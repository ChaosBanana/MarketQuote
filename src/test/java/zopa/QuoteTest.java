package zopa;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import zopa.exception.CsvDataParseError;
import zopa.model.MarketData;

class QuoteTest {
	
	private static final String TEST_DATA_PATH = "./src/test/resources/data.csv";
	
	@Test
	void testQuote() {
		final int loanAmount = 1000;
		Quote quote = new Quote(readToList(), loanAmount);
		assertEquals(loanAmount, quote.getLoanAmount(), "Loan amount is correct");
		assertEquals("0.070", quote.getRate().toString(), "Loan rate is correct");
		assertEquals("30.78", quote.getMonthlyRepayment().toString(), "Loan monthly repayment is correct");
		assertEquals("1108.10", quote.getTotalRepayment().toString(), "Loan total repayment is correct");
	}
	
	private List<MarketData> readToList() {
		try {
			return CsvImport.beanList(TEST_DATA_PATH, MarketData.class);
		} catch (IOException | CsvDataParseError e) {
			fail("Error reading file", e);
			return null;
		}
	}

}
