package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class can check digest of file and encrypt or decrypt file. Class gets arguments from command line.
 * First argument can be "checksha", "encrypt" or "decrypt". If first argument is "checksha" then 
 * method must accept one additional argument, path to the file which digest is checked.
 * If the first argument is "encrypt" or "decrypt" then program must accept two additional 
 * arguments. First of them is source file and second is destination file.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Crypto {
	
	/**
	 * Size of the buffer used in reading from files.
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Method from which program starts. Method accepts arguments from command line.
	 * 
	 * @param args arguments of the command line
	 */
	public static void main(String[] args) {
		if(args.length != 2 && args.length != 3) {
			System.out.println("Expected number of arguments is 2 or 3.");
			
			return;
		}
		
		if(args[0].equals("checksha")) {
			String file = ".\\examples\\" + args[1]; //files are in project in folder examples
			if(!Files.exists(Paths.get(file))) {
				System.out.printf("File %s does not exists.%n", file);
				
				return;
			}
			
			checksha(file);
		} else if(args[0].equals("encrypt") || args[0].equals("decrypt")) {
			String file1 = ".\\examples\\" + args[1];
			String file2 = ".\\examples\\" + args[2];
			if(!Files.exists(Paths.get(file1))) {
				System.out.printf("File %s does not exists.%n", file1);
				
				return;
			}
			
			try {
				encrypt(args[0].equals("encrypt"), file1, file2);
			} catch(IllegalArgumentException exc) {
				System.out.println(exc.getMessage());
			}
		} else {
			System.out.println("First argument must be one of the following: "
							   + "\"checksha\", \"encrypt\" or \"decrypt\".");
			
		}

	}
	
	/**
	 * Method checks if the digest for accepted file is equal to digest which user enters.
	 * Digest is calculated by SHA-256 algorithm.
	 * It prints result to the standard output.
	 * 
	 * @param file file which digest is being checked
	 */
	public static void checksha(String file) {
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("Please provide expected sha-256 digest for %s:%n", file);
		System.out.print("> ");
		
		byte[] digest = Util.hexToByte(sc.nextLine());
		byte[] digestFromFile = getDigestFromFile(file);
		
		if(Util.byteToHex(digest).equals(Util.byteToHex(digestFromFile))) {
			System.out.printf("Digesting completed. Digest of %s matches expected digest.%n", file);
		} else {
			System.out.printf("Digesting completed. Digest of %s does not match the expected digest.%n", file);
			System.out.printf("Digest was %s.%n", Util.byteToHex(digestFromFile));
		}
		
		sc.close();
	}
	
	/**
	 * Method calculates digest of the accepted file by SHA-256 algorithm.
	 * 
	 * @param file file which digest is being calculated
	 * @return digest of file
	 */
	private static byte[] getDigestFromFile(String file) {
		MessageDigest messageDigest = null;
		byte[] digest = null;
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.out.printf("Error while digesting file %s.%n", file);
		}
		
		Path p = Paths.get(file);
		try (InputStream is = Files.newInputStream(p, StandardOpenOption.READ)) {
			byte[] buff = new byte[BUFFER_SIZE];
			
			while(true) {
				int r = is.read(buff);
				
				if(r<1) break;
				
				messageDigest.update(buff, 0, r);
			}
			
			digest = messageDigest.digest();
		 } catch(IOException ex) {
			 System.out.printf("Error while reading from file %s.%n", file);
		 }
		
		return digest;
	}

	/**
	 * Method encrypts or decrypts accepted file into the destination file. First argument says is the 
	 * file is encrypted or decrypted. Encryption and decryption are done using the AES cryptoalgorithm 
	 * and the 128-bit encryption key.
	 * 
	 * @param encrypt if it is true, file is encrypted, otherwise it is decrypted
	 * @param file1 source file
	 * @param file2 destionation file
	 */
	public static void encrypt(Boolean encrypt, String file1, String file2) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");
		String keyText = sc.nextLine();
		
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");
		String ivText = sc.nextLine();
		
		encryptFile(encrypt, keyText, ivText, file1, file2);
		
		System.out.printf("%s completed. Generated file %s based on file %s.%n",
				          encrypt ? "Encrypt" : "Decrypt", file2, file1);
		
		sc.close();
	}
	
	/**
	 * Method accepts encryption key and initialization vector, source and destination file and
	 * does the encrypting or decrypting.
	 * 
	 * @param encrypt if it is true, file is encrypted, otherwise it is decrypted
	 * @param keyText encryption key
	 * @param ivText encryption initialization vector
	 * @param file1 source file
	 * @param file2 destination file
	 */
	private static void encryptFile(Boolean encrypt, String keyText, String ivText, String file1, String file2) {
		SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
		
		Cipher cipher = null;;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			
		} catch (NoSuchAlgorithmException | InvalidKeyException | 
				 InvalidAlgorithmParameterException | NoSuchPaddingException e) {
			System.out.printf("Error while crypting file %s.%n", file1);
		} 
		
		Path p1 = Paths.get(file1);
		try (InputStream is = Files.newInputStream(p1, StandardOpenOption.READ);
			 OutputStream os = new BufferedOutputStream(new FileOutputStream(file2));) {
			byte[] buff = new byte[BUFFER_SIZE];
			
			while(true) {
				int r = is.read(buff);
				
				if(r<1) break;
				
				os.write(cipher.update(buff, 0, r));
			}
			
			os.write(cipher.doFinal());
		 } catch(IOException | BadPaddingException | IllegalBlockSizeException ex) {
			 ex.printStackTrace();
		 }
	
	}

}
