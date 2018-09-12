package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program stvara binarno stablo i dodaje elemente u njega bez duplikata na način da
 * manje elemente dodaje lijevo, a veće desno.
 * 
 * @author Toni Martinčić
 *
 */

public class UniqueNumbers {
	
	/**
	 * Pomoćna klasa koja predstavlja jedan čvor stabla.
	 * 
	 * @author Toni Martinčić
	 *
	 */
	
	public static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}
	
	/**
	 * Metoda od koje kreće izvođenje programa.
	 * 
	 * @param args argumenti zadani preko naredbenog retka
	 */

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String line = null;
		int value;
		TreeNode glava = null;
		
		while(true) {
			try {
				System.out.printf("Unesite broj > ");
				line = sc.nextLine();
				if(line.equals("kraj")){
					break;
				}
				value = Integer.parseInt(line);
				if(!containsValue(glava, value)) {
					glava = addNode(glava, value);
					System.out.println("Dodano.");
				} else {
					System.out.println("Broj već postoji. Preskačem.");
				}
			} catch(NumberFormatException exc) {
				System.out.printf("'%s' nije cijeli broj.%n", line);
			}
			
		}
		
		sc.close();
		
		System.out.print("Ispis od najmanjeg: ");
		printAscending(glava);
		
		System.out.println();
		
		System.out.print("Ispis od najvećeg: ");
		printDescending(glava);
	
	}
	
	/**
	 * Metoda dodaje čvor u binarno stablo samo ako čvor sa vrijednosti koja se dodaje 
	 * već ne postoji na način da manje elemente dodaje lijevo, a veće desno.
	 * 
	 * @param glava glava stabla u koji dodajemo čvor
	 * @param value vrijednost čvora koji dodajemo
	 * @return glava stabla
	 */

	public static TreeNode addNode(TreeNode glava, int value) {
		if(glava == null) {
			glava = new TreeNode();
			glava.left = null;
			glava.right = null;
			glava.value = value;
		} else if(value < glava.value) {
			glava.left = addNode(glava.left, value);
		} else if(value > glava.value) {
			glava.right = addNode(glava.right, value);
		}
		return glava;
	}
	
	/**
	 * Metoda rekurzivno računa veličinu binarnog stabla.
	 * 
	 * @param glava glava stabla čiju veličinu računamo
	 * @return veličina stabla
	 */
	
	public static int treeSize(TreeNode glava) {
		if(glava != null) {
			return 1 + treeSize(glava.left) + treeSize(glava.right);
		} else {
			return 0;
		}
	}
	
	/**
	 * Metoda provjerava sadrži li stablo zadanu vrijednost.
	 * 
	 * @param glava glava stabla u kojemu tražimo zadanu vrijednost.
	 * @param value vrijednost koju tražimo
	 * @return true ako vrijednost postoji u stablu, false inače
	 */
	
	public static boolean containsValue(TreeNode glava, int value) {
		if(glava == null) {
			return false;
		} else if(value < glava.value) {
			return containsValue(glava.left, value);
		} else if(value > glava.value) {
			return containsValue(glava.right, value);
		} else {
			return true;
		}
	}
	
	/**
	 * Metoda na standardni izlaz ispisuje sve elemente stabla u rastućem poretku.
	 * 
	 * @param glava glava stabla čije elemente ispisujemo
	 */
	
	private static void printAscending(TreeNode glava) {
		if(glava != null) {
			printAscending(glava.left);
			System.out.printf("%d ", glava.value);
			printAscending(glava.right);
		}
	}
	
	/**
	 * Metoda na standardni izlaz ispisuje sve elemente stabla u padajućem poretku.
	 * 
	 * @param glava
	 */
	
	private static void printDescending(TreeNode glava) {
		if(glava != null){
			printDescending(glava.right);
			System.out.printf("%d ", glava.value);
			printDescending(glava.left);
		}
	}
	
}
