package zopa.util;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class QuoteAlgorithmTest {
	
	public static final long PRECISION = 20L;
	public static final int MONTHS = 36;

	@Test
	void testTotalRepayment() {
		BigDecimal monthlyRepayments = QuoteAlgorithm.monthlyRepayment(1000, new BigDecimal("0.07"), MONTHS, PRECISION);
		assertEquals("1108.03963776537096", QuoteAlgorithm.totalRepayment(monthlyRepayments, MONTHS).toString());
	}

	@Test
	void testMonthlyRepayment() {
		assertEquals("30.77887882681586", QuoteAlgorithm.monthlyRepayment(1000, new BigDecimal("0.07"), MONTHS, PRECISION).toString());
	}
	
	@Test
	void testMonthlyRate() {
		assertEquals("0.005654145387405277", QuoteAlgorithm.monthlyRate(new BigDecimal("0.07"), PRECISION).toString());
	}

}
