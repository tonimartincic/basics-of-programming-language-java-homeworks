package hr.fer.zemris.java.hw01;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Program preko naredbenog retka ili preko standardnog ulaza prima visinu i širinu
 * pravokutnika i ispisuje površinu i opseg pravokutnika.
 * 
 * @author Toni Martinčić
 *
 */

public class Rectangle {
	

	/**
	 * Metoda od koje kreće izvođenje programa.
	 * 
	 * @param args argumenti zadani preko naredbenog retka
	 */

	public static void main(String[] args) {
		
		double width = 0.0, height = 0.0;
		Scanner sc = new Scanner(System.in);
		
		if(args.length == 0) {
			width = input("širinu", sc);
			height = input("visinu", sc);
			sc.close();
		} else if(args.length == 2){
			try {
				width = Double.parseDouble(args[0]);
				height = Double.parseDouble(args[1]);
			} catch(NumberFormatException | InputMismatchException exc) {
				System.out.println("Jedan od ulaznih argumenata se ne može protumačiti kao broj.");
				System.exit(1);
			}
		} else {
			System.out.printf("Broj predanih argumenata je %d, a mora biti 0 ili 2.%n", args.length);
			System.exit(1);
		}
		
		System.out.printf("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.%n", width, height, width * height, 2 * (width + height));
		
		sc.close();
	}
	
	/**
	 * Metoda sa standardnog ulaza prima vrijednost za određenu dimenziju pravokutnika.
	 * 
	 * @param string dimenzija čija se vrijednost prima
	 * @param sc Scanner
	 * @return vrijednost dimenzije
	 */

	private static double input(String string, Scanner sc) {
		double n = 0.0;
		String line = null;
		while(true){
			System.out.printf("Unesite %s > ", string);
			try {
				line = sc.nextLine();
				n = Double.parseDouble(line);
				if(n >= 0) {
					break;
				} else {
					System.out.println("Unijeli ste negativnu vrijednost.");
				}
			} catch(NumberFormatException exc) {
				System.out.printf("'%s' se ne može protumačiti kao broj.%n", line);
			}
		}
		return n;
	}

}
