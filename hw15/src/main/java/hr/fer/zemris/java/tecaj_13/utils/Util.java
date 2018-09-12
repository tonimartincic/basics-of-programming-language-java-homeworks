package hr.fer.zemris.java.tecaj_13.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class Util contains two public static methods. Those methods are hextobyte(keyText) and 
 * bytetohex(bytearray).
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Util {
	
	/**
	 * Method takes hex-encoded String and returns appropriate byte array.
	 * 
	 * @param keyText hex-encoded String
	 * @return byte array
	 * @throws IllegalArgumentException if the accepted string is null, length of the accepted
	 * string is odd, or accepted string does not contain only letters and digits, 
	 * {@link IllegalArgumentException} is thrown
	 */
	public static byte[] hexToByte(String keyText) {
		if(keyText == null) {
			throw new IllegalArgumentException("Argument keyText must not be null.");
		}
		
		boolean invalid = isInvalid(keyText);
		if(invalid) {
			throw new IllegalArgumentException("Argument must contain only letters and numbers and length of the argument must not be odd.");
		}
		
		if(keyText.length() == 0) {
			return new byte[0];
		}
		
		byte[] byteArray = new byte[keyText.length() / 2];
		for(int i = 0; i < keyText.length(); i += 2) {
			byteArray[i / 2] = getOneByte(keyText.substring(i, i+2));
		}
		
		return byteArray;
	}
	
	/**
	 * Method converts hex-encoded string of the size 2 into the byte and returns that byte.
	 * 
	 * @param substring accepted hex-encoded string
	 * @return byte converted from accepted hex-encode string
	 */
	private static byte getOneByte(String substring) {
		return (byte) ((Character.digit(substring.charAt(0), 16) * 16) + 
                		Character.digit(substring.charAt(1), 16));
	}

	/**
	 * Method checks if the accepted string is valid. Valid string contains only
	 * 
	 * @param keyText
	 * @return true if the accepted string is invalid, false otherwise
	 */
	private static boolean isInvalid(String keyText) {
		if(keyText.length() % 2 != 0) {
			return true;
		}
		
		char[] data = keyText.toCharArray();
		
		boolean invalid = false;
		for(int i = 0, n = data.length; i < n; i++) {
			if(!Character.isDigit(data[i]) && 
			   !(data[i] >= 'A' && data[i] <= 'F') &&
			   !(data[i] >= 'a' && data[i] <= 'f')) {
				invalid = true;
				
				break;
			}
		}
		
		return invalid;
	}

	/**
	 * Method accepts byte array and returns hex-encoded string which represents accepted
	 * byte array.
	 * 
	 * @param bytearray accepted byte array
	 * @return hex-encoded string
	 */
	public static String byteToHex(byte[] bytearray) {
		if(bytearray == null) {
			throw new IllegalArgumentException("Byte array must not be null.");
		}
		
		if(bytearray.length == 0) {
			return new String("");
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0, n = bytearray.length; i < n; i++) {
			sb.append(oneByteAsHex(bytearray[i]));
		}
		
		return sb.toString();
	}

	/**
	 * Method returns hex-encoded value for accepted one byte.
	 * 
	 * @param b accepted byte
	 * @return hex value of accepted byte
	 */
	private static String oneByteAsHex(byte b) {
		int i = b;
		
		String hex = Integer.toHexString(i);
		
		//if int was negative
		if(hex.length() == 8) {
			hex = hex.substring(hex.length() - 2, hex.length());
		}
		
		//if int didn't have two digits
		if(hex.length() == 1) {
			hex = "0" + hex;
		}
		
		return hex;
	}
	
	/**
	 * Method gets password hash.
	 * 
	 * @param password password
	 * @return password hash
	 */
	public static String getPasswordHash(String password) {
		MessageDigest messageDigest = null;
		byte[] digest = null;
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
		
		messageDigest.update(password.getBytes());
		digest = messageDigest.digest();
		
		return byteToHex(digest);
	}
}
