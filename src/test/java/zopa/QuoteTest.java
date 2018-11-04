package zopa;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class QuoteTest {
	
	@Test
	void testTotalRepayment() {
		BigDecimal monthlyRepayments = Quote.monthlyRepayment(1000, "0.07");
		assertEquals("1108.03963776537096", Quote.totalRepayment(monthlyRepayments).toString());
	}

	@Test
	void testMonthlyRepayment() {
		assertEquals("30.77887882681586", Quote.monthlyRepayment(1000, "0.07").toString());
	}
	
	@Test
	void testMonthlyRate() {
		assertEquals("0.005654145387405277", Quote.monthlyRate("0.07").toString());
	}

}
