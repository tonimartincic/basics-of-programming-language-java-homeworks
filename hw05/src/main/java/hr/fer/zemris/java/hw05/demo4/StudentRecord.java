package hr.fer.zemris.java.hw05.demo4;

/**
 * Class has data for one student. It has jmbag, last name, first name, number of points on MI,
 * number of points on ZI, number of points on LAB ang grade of student. 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class StudentRecord {
	
	/**
	 * Student's jmbag.
	 */
	private String jmbag;
	
	/**
	 * Student's last name.
	 */
	private String prezime;
	
	/**
	 * Student's first name.
	 */
	private String ime;
	
	/**
	 * Number of points on MI.
	 */
	private double brojBodovaNaMI;
	
	/**
	 * Number of points on ZI.
	 */
	private double brojBodovaNaZI;
	
	/**
	 * Number of points on LAB.
	 */
	private double brojBodovaNaLAB;
	
	/**
	 * Student's grade.
	 */
	private int ocjena;
	
	/**
	 * Constructor which accepts 7 arguments. It accepts jmbag, last name, first name, 
	 * number of points on MI, number of points on ZI, number of points on LAB ang grade of student.
	 * 
	 * @param jmbag student's jmbag
	 * @param prezime student's last name
	 * @param ime student's first name
	 * @param brojBodovaNaMI number of points on MI
	 * @param brojBodovaNaZI number of points on ZI
	 * @param brojBodovaNaLAB number of points on LAB
	 * @param ocjena student's grade
	 */
	public StudentRecord(String jmbag, String prezime, String ime, 
			double brojBodovaNaMI, double brojBodovaNaZI, double brojBodovaNaLAB, int ocjena) {
		
		super();
		
		this.jmbag = jmbag;
		this.prezime = prezime;
		this.ime = ime;
		this.brojBodovaNaMI = brojBodovaNaMI;
		this.brojBodovaNaZI = brojBodovaNaZI;
		this.brojBodovaNaLAB = brojBodovaNaLAB;
		this.ocjena = ocjena;
	}

	/**
	 * Getter for the jmbag.
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for the last name.
	 * 
	 * @return last name
	 */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * Getter for the first name.
	 * 
	 * @return first name
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * Getter for the number of points on MI.
	 * 
	 * @return number of points on MI
	 */
	public double getBrojBodovaNaMI() {
		return brojBodovaNaMI;
	}

	/**
	 * Getter for the number of points on ZI.
	 * 
	 * @return number of points on ZI
	 */
	public double getBrojBodovaNaZI() {
		return brojBodovaNaZI;
	}

	/**
	 * Getter for the number of points on LAB.
	 * 
	 * @return number of points on LAB
	 */
	public double getBrojBodovaNaLAB() {
		return brojBodovaNaLAB;
	}

	/**
	 * Getter for the student's grade.
	 * 
	 * @return student's grade
	 */
	public int getOcjena() {
		return ocjena;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("jmbag = ").append(jmbag).append(",");
		sb.append(" prezime = ").append(prezime).append(",");
		sb.append(" ime = ").append(ime).append(",");
		sb.append(" brojBodovaNaMI = ").append(brojBodovaNaMI).append(",");
		sb.append(" brojBodovaNaZI = ").append(brojBodovaNaZI).append(",");
		sb.append(" brojBodovaNaLAB = ").append(brojBodovaNaLAB).append(",");
		sb.append(" ocjena = ").append(ocjena);
		
		return sb.toString();
	}
	
}
