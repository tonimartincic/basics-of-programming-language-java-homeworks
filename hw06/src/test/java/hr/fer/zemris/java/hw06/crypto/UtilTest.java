package hr.fer.zemris.java.hw06.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class UtilTest {

	@Test
	public void testHexToByteFromHomework() {
		String hex = "01aE22";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {1, -82, 34}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteZeroZero() {
		String hex = "00";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {0}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteZeroF() {
		String hex = "0F";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {15}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteZeroNine() {
		String hex = "09";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {9}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteNineNine() {
		String hex = "99";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-103}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteFF() {
		String hex = "FF";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-1}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteFZero() {
		String hex = "F0";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-16}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteEightZero() {
		String hex = "80";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-128}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteEightOne() {
		String hex = "81";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-127}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteEightF() {
		String hex = "8F";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-113}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteEightSeven() {
		String hex = "87";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-121}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteNineA() {
		String hex = "9A";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-102}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteNineB() {
		String hex = "9B";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-101}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteNineC() {
		String hex = "9C";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-100}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteNineD() {
		String hex = "9D";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-99}, byteArray);
	}
	
	@Test
	public void testHexToByteOneByteSevenF() {
		String hex = "7F";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {127}, byteArray);
	}
	
	@Test
	public void testHexToByte00FF() {
		String hex = "00FF";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {0, -1}, byteArray);
	}
	
	@Test
	public void testHexToByteFF00() {
		String hex = "FF00";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-1, 0}, byteArray);
	}
	
	@Test
	public void testHexToByteABCDEF() {
		String hex = "ABCDEF";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-85, -51, -17}, byteArray);
	}
	
	@Test
	public void testHexToByteABCDEFabcdefAbcDEfFF0000FF() {
		String hex = "ABCDEFabcdefAbcDEfFF0000FF";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-85, -51, -17, -85, -51, -17, -85, -51, -17, -1, 0, 0, -1}, byteArray);
	}
	
	@Test
	public void testHexToByteABCDEFabcdefAbcDEf() {
		String hex = "ABCDEFabcdefAbcDEf";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {-85, -51, -17, -85, -51, -17, -85, -51, -17}, byteArray);
	}
	
	@Test
	public void testHexToByteEmpty() {
		String hex = "";
		byte[] byteArray = Util.hexToByte(hex);
		
		assertArrayEquals(new byte[] {}, byteArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHexToByteNull() {
		String hex = null;
		@SuppressWarnings("unused")
		byte[] byteArray = Util.hexToByte(hex);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHexToByteOddSize() {
		String hex = "01aE223";
		@SuppressWarnings("unused")
		byte[] byteArray = Util.hexToByte(hex);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHexToByteOddSize2() {
		String hex = "0";
		@SuppressWarnings("unused")
		byte[] byteArray = Util.hexToByte(hex);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHexToByteOddSize3() {
		String hex = "12345";
		@SuppressWarnings("unused")
		byte[] byteArray = Util.hexToByte(hex);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHexToByteInvalidCharacters() {
		String hex = "a?";
		@SuppressWarnings("unused")
		byte[] byteArray = Util.hexToByte(hex);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHexToByteInvalidCharacters2() {
		String hex = "FFG1";
		@SuppressWarnings("unused")
		byte[] byteArray = Util.hexToByte(hex);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHexToByteInvalidCharacters3() {
		String hex = "1h";
		@SuppressWarnings("unused")
		byte[] byteArray = Util.hexToByte(hex);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHexToByteInvalidCharacters4() {
		String hex = "00-1";
		@SuppressWarnings("unused")
		byte[] byteArray = Util.hexToByte(hex);
	}
	
	@Test
	public void testByteToHexFromHomework() {
		byte[] byteArray = new byte[] {1, -82, 34};
		String hex = Util.byteToHex(byteArray);
		
		assertEquals("01ae22", hex);
	}
	
	@Test
	public void testByteToHex127() {
		byte[] byteArray = new byte[] {127};
		String hex = Util.byteToHex(byteArray);
		
		assertEquals("7f", hex);
	}
	
	@Test
	public void testByteToHex126() {
		byte[] byteArray = new byte[] {126};
		String hex = Util.byteToHex(byteArray);
		
		assertEquals("7e", hex);
	}
	
	@Test
	public void testByteToHexMinus128() {
		byte[] byteArray = new byte[] {-128};
		String hex = Util.byteToHex(byteArray);
		
		assertEquals("80", hex);
	}
	
	@Test
	public void testByteToHexMinus127() {
		byte[] byteArray = new byte[] {-127};
		String hex = Util.byteToHex(byteArray);
		
		assertEquals("81", hex);
	}
	
	@Test
	public void testByteToHex0() {
		byte[] byteArray = new byte[] {0};
		String hex = Util.byteToHex(byteArray);
		
		assertEquals("00", hex);
	}
	
	@Test
	public void testByteToHex1() {
		byte[] byteArray = new byte[] {1};
		String hex = Util.byteToHex(byteArray);
		
		assertEquals("01", hex);
	}
	
	@Test
	public void testByteToHexMinus1() {
		byte[] byteArray = new byte[] {-1};
		String hex = Util.byteToHex(byteArray);
		
		assertEquals("ff", hex);
	}
	
	@Test
	public void testByteToHexMinus1_1_127_minus128() {
		byte[] byteArray = new byte[] {-1, 1, 127, -128};
		String hex = Util.byteToHex(byteArray);
		
		assertEquals("ff017f80", hex);
	}
	
	@Test
	public void testByteToHexEmpty() {
		byte[] byteArray = new byte[] {};
		String hex = Util.byteToHex(byteArray);
		
		assertEquals("", hex);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testByteToHexNull() {
		byte[] byteArray = null;
		@SuppressWarnings("unused")
		String hex = Util.byteToHex(byteArray);
	}
}
