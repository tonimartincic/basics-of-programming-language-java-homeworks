package hr.fer.zemris.java.hw02;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComplexNumberTest {
	
	private static final double REAL_PART_POSITIVE = 10.0;
	private static final double REAL_PART_NEGATIVE = -10.0;
	private static final double IMAGINARY_PART_POSITIVE = 5.0;
	private static final double IMAGINARY_PART_NEGATIVE = -5.0;
	
	private static final double DELTA = 1E-8;
	
	private static final double FIRST_REAL_PART = 2.0;
	private static final double FIRST_IMAGINARY_PART = -3.0;
	private static final double SECOND_REAL_PART = -4.0;
	private static final double SECOND_IMAGINARY_PART = 5.5;
	
	private static final int POWER_N = 6;
	private static final double FIRST_MAGNITUDE = 8.5;
	private static final double FIRST_ANGLE = 3.28;
	private static final int ROOT_N = 3;
	
	

	@Test
	public void testComplexNumber() {
		ComplexNumber complexNumber = new ComplexNumber(REAL_PART_POSITIVE , IMAGINARY_PART_POSITIVE);
		
		assertEquals(REAL_PART_POSITIVE, complexNumber.getReal(), DELTA);
		assertEquals(IMAGINARY_PART_POSITIVE, complexNumber.getImaginary(), DELTA);
	}

	@Test
	public void testFromReal() {
		ComplexNumber complexNumber = ComplexNumber.fromReal(REAL_PART_POSITIVE);
		
		assertEquals(REAL_PART_POSITIVE, complexNumber.getReal(), DELTA);
		assertEquals(0.0, complexNumber.getImaginary(), DELTA);
	}

	@Test
	public void testFromImaginary() {
		ComplexNumber complexNumber = ComplexNumber.fromImaginary(IMAGINARY_PART_POSITIVE);
		
		assertEquals(0.0, complexNumber.getReal(), DELTA);
		assertEquals(IMAGINARY_PART_POSITIVE, complexNumber.getImaginary(), DELTA);
	}

	@Test
	public void testFromMagnitudeAndAngle() {
		ComplexNumber complexNumber1 = 
				ComplexNumber.fromMagnitudeAndAngle(FIRST_MAGNITUDE, FIRST_ANGLE);
		
		assertEquals(Math.cos(FIRST_ANGLE) * FIRST_MAGNITUDE, complexNumber1.getReal(), DELTA);
		assertEquals(Math.sin(FIRST_ANGLE) * FIRST_MAGNITUDE, complexNumber1.getImaginary(), DELTA);
	}

	@Test
	public void testParsePositivePositive() {
		ComplexNumber complexNumber1 = new ComplexNumber(REAL_PART_POSITIVE , IMAGINARY_PART_POSITIVE);
		
		String complexNumberAsString = "10.0+5.0i";
		ComplexNumber complexNumber2 = ComplexNumber.parse(complexNumberAsString);
		
		assertEquals(complexNumber1.getReal(), complexNumber2.getReal(), DELTA);
		assertEquals(complexNumber1.getImaginary(), complexNumber2.getImaginary(), DELTA);
	}
	
	@Test
	public void testParsePositiveNegative() {
		ComplexNumber complexNumber1 = new ComplexNumber(REAL_PART_POSITIVE , IMAGINARY_PART_NEGATIVE);
		
		String complexNumberAsString = "10.0-5.0i";
		ComplexNumber complexNumber2 = ComplexNumber.parse(complexNumberAsString);
		
		assertEquals(complexNumber1.getReal(), complexNumber2.getReal(), DELTA);
		assertEquals(complexNumber1.getImaginary(), complexNumber2.getImaginary(), DELTA);
	}
	
	@Test
	public void testParseNegativePositive() {
		ComplexNumber complexNumber1 = new ComplexNumber(REAL_PART_NEGATIVE , IMAGINARY_PART_POSITIVE);
		
		String complexNumberAsString = "-10.0+5.0i";
		ComplexNumber complexNumber2 = ComplexNumber.parse(complexNumberAsString);
		
		assertEquals(complexNumber1.getReal(), complexNumber2.getReal(), DELTA);
		assertEquals(complexNumber1.getImaginary(), complexNumber2.getImaginary(), DELTA);
	}
	
	@Test
	public void testParseNegativeNegative() {
		ComplexNumber complexNumber1 = new ComplexNumber(REAL_PART_NEGATIVE , IMAGINARY_PART_NEGATIVE);
		
		String complexNumberAsString = "-10.0-5.0i";
		ComplexNumber complexNumber2 = ComplexNumber.parse(complexNumberAsString);
		
		assertEquals(complexNumber1.getReal(), complexNumber2.getReal(), DELTA);
		assertEquals(complexNumber1.getImaginary(), complexNumber2.getImaginary(), DELTA);
	}
	
	@Test
	public void testParseOnlyRealPositivePart() {
		ComplexNumber complexNumber1 = ComplexNumber.fromReal(REAL_PART_POSITIVE);
		
		String complexNumberAsString = "10.0";
		ComplexNumber complexNumber2 = ComplexNumber.parse(complexNumberAsString);
		
		assertEquals(complexNumber1.getReal(), complexNumber2.getReal(), DELTA);
		assertEquals(complexNumber1.getImaginary(), complexNumber2.getImaginary(), DELTA);
	}
	
	@Test
	public void testParseOnlyRealNegativePart() {
		ComplexNumber complexNumber1 = ComplexNumber.fromReal(REAL_PART_NEGATIVE);
		
		String complexNumberAsString = "-10.0";
		ComplexNumber complexNumber2 = ComplexNumber.parse(complexNumberAsString);
		
		assertEquals(complexNumber1.getReal(), complexNumber2.getReal(), DELTA);
		assertEquals(complexNumber1.getImaginary(), complexNumber2.getImaginary(), DELTA);
	}
	
	@Test
	public void testParseOnlyImaginaryPositivePart() {
		ComplexNumber complexNumber1 = ComplexNumber.fromImaginary(IMAGINARY_PART_POSITIVE);
		
		String complexNumberAsString = "5.0i";
		ComplexNumber complexNumber2 = ComplexNumber.parse(complexNumberAsString);
		
		assertEquals(complexNumber1.getReal(), complexNumber2.getReal(), DELTA);
		assertEquals(complexNumber1.getImaginary(), complexNumber2.getImaginary(), DELTA);
	}
	
	@Test
	public void testParseOnlyImaginaryNegativePart() {
		ComplexNumber complexNumber1 = ComplexNumber.fromImaginary(IMAGINARY_PART_NEGATIVE);
		
		String complexNumberAsString = "-5.0i";
		ComplexNumber complexNumber2 = ComplexNumber.parse(complexNumberAsString);
		
		assertEquals(complexNumber1.getReal(), complexNumber2.getReal(), DELTA);
		assertEquals(complexNumber1.getImaginary(), complexNumber2.getImaginary(), DELTA);
	}

	@Test
	public void testGetReal() {
		ComplexNumber complexNumber = new ComplexNumber(REAL_PART_POSITIVE , IMAGINARY_PART_POSITIVE);
		
		assertEquals(REAL_PART_POSITIVE, complexNumber.getReal(), DELTA);
	}

	@Test
	public void testGetImaginary() {
		ComplexNumber complexNumber = new ComplexNumber(REAL_PART_POSITIVE , IMAGINARY_PART_POSITIVE);
		
		assertEquals(IMAGINARY_PART_POSITIVE, complexNumber.getImaginary(), DELTA);
	}

	@Test
	public void testGetMagnitude() {
		ComplexNumber complexNumber = new ComplexNumber(REAL_PART_POSITIVE , IMAGINARY_PART_POSITIVE);
	
		assertEquals(5.0 * Math.sqrt(5.0) , complexNumber.getMagnitude(), DELTA);
	}

	@Test
	public void testGetAngle() {
		ComplexNumber complexNumber = new ComplexNumber(REAL_PART_POSITIVE , IMAGINARY_PART_POSITIVE);
		
		double angle = Math.atan2(IMAGINARY_PART_POSITIVE, REAL_PART_POSITIVE);
		angle = angle > 0.0 ? angle : angle + 2 * Math.PI;
		
		assertEquals(angle, complexNumber.getAngle(), DELTA);
	}

	@Test
	public void testAdd() {
		ComplexNumber complexNumber1 = new ComplexNumber(FIRST_REAL_PART, FIRST_IMAGINARY_PART);
		ComplexNumber complexNumber2 = new ComplexNumber(SECOND_REAL_PART, SECOND_IMAGINARY_PART);
		
		ComplexNumber complexNumber3 = complexNumber1.add(complexNumber2);
		assertEquals(complexNumber3.getReal(), complexNumber1.getReal() + complexNumber2.getReal(), DELTA);
		assertEquals(complexNumber3.getImaginary(), complexNumber1.getImaginary() + complexNumber2.getImaginary(), DELTA);
	}

	@Test
	public void testSub() {
		ComplexNumber complexNumber1 = new ComplexNumber(FIRST_REAL_PART, FIRST_IMAGINARY_PART);
		ComplexNumber complexNumber2 = new ComplexNumber(SECOND_REAL_PART, SECOND_IMAGINARY_PART);
		
		ComplexNumber complexNumber3 = complexNumber1.sub(complexNumber2);
		assertEquals(complexNumber3.getReal(), complexNumber1.getReal() - complexNumber2.getReal(), DELTA);
		assertEquals(complexNumber3.getImaginary(), complexNumber1.getImaginary() - complexNumber2.getImaginary(), DELTA);
	}

	@Test
	public void testMul() {
		ComplexNumber complexNumber1 = new ComplexNumber(FIRST_REAL_PART, FIRST_IMAGINARY_PART);
		ComplexNumber complexNumber2 = new ComplexNumber(SECOND_REAL_PART, SECOND_IMAGINARY_PART);
		
		ComplexNumber complexNumber3 = complexNumber1.mul(complexNumber2);
		
		double magnitude = complexNumber1.getMagnitude() * complexNumber2.getMagnitude();
		
		double angle = complexNumber1.getAngle() + complexNumber2.getAngle();
		angle = angle < 2 * Math.PI ? angle : angle - 2 * Math.PI;
		
		assertEquals(magnitude, complexNumber3.getMagnitude(), DELTA);
		assertEquals(angle, complexNumber3.getAngle(), DELTA);
	}

	@Test
	public void testDiv() {
		ComplexNumber complexNumber1 = new ComplexNumber(FIRST_REAL_PART, FIRST_IMAGINARY_PART);
		ComplexNumber complexNumber2 = new ComplexNumber(SECOND_REAL_PART, SECOND_IMAGINARY_PART);
		
		ComplexNumber complexNumber3 = complexNumber1.div(complexNumber2);
		assertEquals(complexNumber1.getMagnitude() / complexNumber2.getMagnitude(), 
					 complexNumber3.getMagnitude(),
					 DELTA);
		assertEquals(complexNumber1.getAngle() - complexNumber2.getAngle(),
					 complexNumber3.getAngle(),
					 DELTA);
		
	}

	@Test
	public void testPowerFirstTest() {
		ComplexNumber complexNumber = new ComplexNumber(FIRST_REAL_PART, FIRST_IMAGINARY_PART);
		
		double magnitude = Math.pow(complexNumber.getMagnitude(), POWER_N);
		
		double angle = complexNumber.getAngle() * POWER_N;
	
		while(angle > (2 * Math.PI)) {
			angle -= 2 * Math.PI;
		}
		
		complexNumber = complexNumber.power(POWER_N);

		assertEquals(magnitude, complexNumber.getMagnitude(), DELTA);
		assertEquals(angle, complexNumber.getAngle(), DELTA);
	}
	
	@Test
	public void testPowerSecondTest() {
		ComplexNumber complexNumber = new ComplexNumber(SECOND_REAL_PART, SECOND_IMAGINARY_PART);
		
		double magnitude = Math.pow(complexNumber.getMagnitude(), POWER_N);
		
		double angle = complexNumber.getAngle() * POWER_N;
		
		while(angle > (2 * Math.PI)) {
			angle -= 2 * Math.PI;
		}
		
		complexNumber = complexNumber.power(POWER_N);

		assertEquals(magnitude, complexNumber.getMagnitude(), DELTA);
		assertEquals(angle, complexNumber.getAngle(), DELTA);
	}

	@Test
	public void testRootFirstTest() {
		ComplexNumber complexNumber = new ComplexNumber(REAL_PART_POSITIVE , IMAGINARY_PART_POSITIVE);
		
		ComplexNumber[] roots = complexNumber.root(ROOT_N);
		
		assertEquals(2.209416337985, roots[0].getReal(), DELTA);
		assertEquals(0.344208433140, roots[0].getImaginary(), DELTA);
		
		assertEquals(-1.40280141628, roots[1].getReal(), DELTA);
		assertEquals(1.741306459661, roots[1].getImaginary(), DELTA);
		
		assertEquals(-0.806614921696, roots[2].getReal(), DELTA);
		assertEquals(-2.085514892801, roots[2].getImaginary(), DELTA);
		
	}
	
	@Test
	public void testRootSecondTest() {
		ComplexNumber complexNumber = new ComplexNumber(0.0 , 1.0);
		
		ComplexNumber[] roots = complexNumber.root(ROOT_N);
		
		assertEquals(0.866025403784, roots[0].getReal(), DELTA);
		assertEquals(0.500000000000, roots[0].getImaginary(), DELTA);
		
		assertEquals(-0.866025403784, roots[1].getReal(), DELTA);
		assertEquals(0.5000000000000, roots[1].getImaginary(), DELTA);
		
		assertEquals(0.0000000000000, roots[2].getReal(), DELTA);
		assertEquals(-1.000000000000, roots[2].getImaginary(), DELTA);
	}

}
