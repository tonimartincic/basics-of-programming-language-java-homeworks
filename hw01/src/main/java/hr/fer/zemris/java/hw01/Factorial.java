package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji preko standardnog ulaza prima cijele brojeve u rasponu od 1 do 20
 * i ispisuje faktorijelu zadanog broja.
 * 
 * @author Toni Martinčić
 *
 */

public class Factorial {
	
	/**
	 * Metoda od koje kreće izvođenje programa.
	 * 
	 * @param args argumenti zadani preko naredbenog retka
	 */

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("Unesite broj > ");
			if(sc.hasNextInt()) {
				int n = sc.nextInt();
				if((n >= 1) && (n <= 20)){
					System.out.printf("%d! = %d%n", n, countFactorial(n));
				} else {
					System.out.printf("'%d' nije broj u dozvoljenom rasponu.%n", n);
				}
			} else {
				String string = sc.next();
				if(string.equals("kraj")){
					System.out.println("Doviđenja.");
					break;
				} else {
					System.out.printf("'%s' nije cijeli broj.%n", string);
				}
			}
		}
		
		sc.close();
	}
	
	/**
	 * Metoda koja računa faktorijelu zadanog broja.
	 * 
	 * @param n broj čiju faktorijelu metoda računa
	 * @return faktorijela zadanog broja
	 */
	
	public static long countFactorial(int n) {
		long factorial = 1;
		for(int i = 1; i <= n; i++){
			factorial *= i;
		}
		return factorial;
	}

}
