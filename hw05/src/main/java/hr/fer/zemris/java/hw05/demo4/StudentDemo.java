package hr.fer.zemris.java.hw05.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class filters students from student database on few conditions and for each case it prints
 * filtered data to the standard output.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class StudentDemo {

	/**
	 * Method from which program starts.
	 * 
	 * @param args arguments of main method, unused
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		List<String> lines = readLines();
		List<StudentRecord> records = convert(lines);
		
		long brojBodovaViseOd25 = vratiBodovaViseOd25(records);
		long brojOdlikasa = vratiBrojOdlikasa(records);
		
		List<StudentRecord> listaOdlikasa = vratiListuOdlikasa(records);
		List<StudentRecord> sortiranaListaOdlikasa = vratiSortiranuListuOdlikasa(records);
		List<String> popisNepolozenih = vratiPopisNepolozenih(records);
		
		Map<Integer, List<StudentRecord>> studentiPoOcjenama = razvrstajStudentePoOcjenama(records);
		Map<Integer, Integer> brojStudenataPoOcjenama = vratiBrojStudenataPoOcjenama(records);
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);

		//***Ako se ispiše sve odjednom onda se ne vidi sve***
		
//		ispisiPrvi(brojBodovaViseOd25);
		
//		ispisiDrugi(brojOdlikasa);
		
//		ispisiTreci(listaOdlikasa);
		
//		ispisiCetvrti(sortiranaListaOdlikasa);
		
//		ispisiPeti(popisNepolozenih);
		
//		ispisiSesti(studentiPoOcjenama);
		
//		ispisiSedmi(brojStudenataPoOcjenama);
		
//		ispisiOsmi(prolazNeprolaz);
	
	}

	/**
	 * Method prints to the standard output number of students which have more than 25 points on
	 * MI, ZI and LAB together.
	 * 
	 * @param brojBodovaViseOd25 number of students which has more than 25 points on
	 * MI, ZI and LAB together
	 */
	@SuppressWarnings("unused")
	private static void ispisiPrvi(long brojBodovaViseOd25) {
		System.out.printf("Broj studenata sa više od 25 bodova je %d.%n%n", brojBodovaViseOd25);
		
	}

	/**
	 * Method prints to the standard output number of students which grade is 5.
	 * 
	 * @param brojOdlikasa number of students which grade is 5
	 */
	@SuppressWarnings("unused")
	private static void ispisiDrugi(long brojOdlikasa) {
		System.out.printf("Odlikaša ima %d.%n%n", brojOdlikasa);
		
	}

	/**
	 * Method prints to the standard output students which grade is 5.
	 * 
	 * @param listaOdlikasa list of students which grade is 5
	 */
	@SuppressWarnings("unused")
	private static void ispisiTreci(List<StudentRecord> listaOdlikasa) {
		System.out.println("Odlikaši:");
		for(StudentRecord studentRecord : listaOdlikasa) {
			System.out.println("  " + studentRecord);
		}
		
		System.out.println();
	}

	/**
	 * Method prints to the standard output students which grade is 5. Students are sorted by
	 * number of points.
	 * 
	 * @param sortiranaListaOdlikasa sorted list of students which grade is 5
	 */
	@SuppressWarnings("unused")
	private static void ispisiCetvrti(List<StudentRecord> sortiranaListaOdlikasa) {
		System.out.println("Odlikaši sortirani po ukupnom broju bodova:");
		for(StudentRecord studentRecord : sortiranaListaOdlikasa) {
			System.out.print("  " + studentRecord);
			System.out.printf(", ukupni bodovi = %f%n", studentRecord.getBrojBodovaNaMI() + 
					          studentRecord.getBrojBodovaNaZI() + studentRecord.getBrojBodovaNaLAB());
		}
		
		System.out.println();
		
	}

	/**
	 * Method prints to the standard output jmbags of students which grade is 1.
	 * 
	 * @param popisNepolozenih list of jmbags of students which grade is 1
	 */
	@SuppressWarnings("unused")
	private static void ispisiPeti(List<String> popisNepolozenih) {
		System.out.println("Studenti koji nisu položili:");
		for(String jmbag: popisNepolozenih) {
			System.out.printf("  " + jmbag +"%n");
		}
		
		System.out.println();
	}

	/**
	 * Method prints to the standard output students grouped by grades.
	 * 
	 * @param studentiPoOcjenama students grouped by grades
	 */
	@SuppressWarnings("unused")
	private static void ispisiSesti(Map<Integer, List<StudentRecord>> studentiPoOcjenama) {
		System.out.println("Studenti po ocjenama:");
		for(int i = 1; i <= 5; i++){
			System.out.printf("  Studenti s ocjenom %d:%n", i);
			for(StudentRecord studentRecord : studentiPoOcjenama.get(i)) {
				System.out.println("    " + studentRecord);
			}
		}
		
		System.out.println();
	}

	/**
	 * Method prints to the standard output number of students for each grade.
	 * 
	 * @param brojStudenataPoOcjenama number of students for each grade.
	 */
	@SuppressWarnings("unused")
	private static void ispisiSedmi(Map<Integer, Integer> brojStudenataPoOcjenama) {
		System.out.println("Brojevi studenata po ocjenama:");
		for(int i = 1; i <= 5; i++){
			System.out.printf("  Broj studenata s ocjenom %d je %d.%n", 
					          i, brojStudenataPoOcjenama.get(i));
		}
		
		System.out.println();
	}

	/**
	 * Method prints to the standard output students grouped by pass or fail.
	 * 
	 * @param prolazNeprolaz students grouped by pass or fail
	 */
	@SuppressWarnings("unused")
	private static void ispisiOsmi(Map<Boolean, List<StudentRecord>> prolazNeprolaz) {
		System.out.println("Ispis studenata koji su prosli i koji nisu prosli:");
		
		System.out.println("  Studenti koji su prosli:");
		for(StudentRecord studentRecord : prolazNeprolaz.get(true)) {
			System.out.println("    " + studentRecord);
		}
		
		System.out.println("  Studenti koji nisusu prosli:");
		for(StudentRecord studentRecord : prolazNeprolaz.get(false)) {
			System.out.println("    " + studentRecord);
		}
		
		System.out.println();
	}

	/**
	 * Method returns number of students which have more than 25 points on MI, ZI and LAB together.
	 * 
	 * @param records list of student records
	 * @return number of students which have more than 25 points on MI, ZI and LAB together
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
		              .filter(sR ->  sR.getBrojBodovaNaMI() + sR.getBrojBodovaNaZI()
 		                      + sR.getBrojBodovaNaLAB() > 25.0)
		              .count();
	}

	/**
	 * Method returns number of students which grade is 5.
	 * 
	 * @param records list of student records
	 * @return number of students which grade is 5
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records
				.stream()
				.filter(sR -> sR.getOcjena() == 5)
				.count();
	}

	/**
	 * Method returns list of students which grade is 5.
	 * 
	 * @param records list of student records
	 * @return list of students which grade is 5
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records
				.stream()
				.filter(sR -> sR.getOcjena() == 5)
			    .collect(Collectors.toList());
	}

	/**
	 * Method returns sorted list of students which grade is 5.
	 * 
	 * @param records list of student records
	 * @return sorted list of students which grade is 5
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records
			    .stream()
				.filter(sR -> sR.getOcjena() == 5)
			    .sorted(new Comparator<StudentRecord>() {
					@Override
					public int compare(StudentRecord o1, StudentRecord o2) {
						double value = 
						(o1.getBrojBodovaNaMI() + o1.getBrojBodovaNaZI()  + o1.getBrojBodovaNaLAB())-
						(o2.getBrojBodovaNaMI() + o2.getBrojBodovaNaZI()  + o2.getBrojBodovaNaLAB());
						
						if(value > 0) {
							return -1;
						} 
						
						if(value < 0) {
							return 1;
						}
						
						return 0;
					}
				 })
			    .collect(Collectors.toList());
	}

	/**
	 * Method returns list of jmbag of students which grade is 1.
	 * 
	 * @param records list of student records
	 * @return list of jmbag of students which grade is 1
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records
				.stream()
				.filter( sR -> sR.getOcjena() == 1)
				.map( sR -> sR.getJmbag())
				.sorted( (jmbag1, jmbag2) -> {
					return jmbag1.compareTo(jmbag2);
				 })
				.collect(Collectors.toList());
	}

	/**
	 * Method returns {@link Map} which keys are {@link Integer} and values are {@link List} of
	 * {@link StudentRecord}. Keys are grades, and values are list of students which have that
	 * grade.
	 * 
	 * @param records list of student records
	 * @return map which keys are grades, and values are list of students which have that grade
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records
				.stream()
				.collect(Collectors.groupingBy(StudentRecord::getOcjena));
	}

	/**
	 * Method returns map which key is {@link Integer} and values are {@link Integer}.
	 * Keys are grades, and values are number of students which have that grade.
	 * 
	 * @param records list of student records
	 * @return map which keys are grades, and values are number of students which have that grade
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records
				.stream()
				.collect(Collectors.toMap(
						studentRecord -> studentRecord.getOcjena(),
					    studentRecord -> 1,
						(value, increase) -> value + 1));
	}

	/**
	 * Method returns map which key is {@link Boolean} and values are {@link StudentRecord}. 
	 * Key is true if student's grade is not 1, false otherwise. 
	 * 
	 * @param records list of student records
	 * @return map which key is {@link Boolean} and values are {@link StudentRecord}
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records
				.stream()
				.collect(Collectors.partitioningBy( studentRecord -> studentRecord.getOcjena() > 1));
	}

	/**
	 * Method converts list of lines into the list of {@link StudentRecord}-s.
	 * 
	 * @param lines list of lines
	 * @return list of student records
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> listOfStudentRecords = new ArrayList<>();
		
		for(String line : lines) {
			String[] parts = line.split("\\s+");
			
			if(parts.length != 7) {
				break;
			}
			
			listOfStudentRecords.add(new StudentRecord(parts[0], parts[1], parts[2], 
				    Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), 
				    Double.parseDouble(parts[5]), Integer.parseInt(parts[6])));
		}
		
		return listOfStudentRecords;
	}

	/**
	 * Method reads all lines from text file. It returns list of strings. Each string 
	 * represents one line.
	 * 
	 * @return list of lines
	 */
	private static List<String> readLines() {
		List<String> lines = new ArrayList<>();
		
		try {
			lines = Files.readAllLines(
					 Paths.get("src/main/resources/studenti.txt"),
					 StandardCharsets.UTF_8
			);
		} catch (IOException e) {
			System.err.println(e);
		}	
		
		return lines;
	}

}
