package zopa;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import zopa.exception.CannotOfferQuote;
import zopa.exception.CsvDataParseError;
import zopa.model.MarketData;

class QuoteTest {
	
	private static final String TEST_DATA_PATH = "./src/test/resources/data.csv";
	
	@Test
	void testQuote() {
		final int loanAmount = 1000;
		Quote quote = null;
		try {
			quote = new Quote(readToList(), loanAmount);
		} catch (CannotOfferQuote e) {
			fail("Market should be able to offer " + loanAmount, e);
		}
		assertEquals(loanAmount, quote.getLoanAmount(), "Loan amount is correct");
		assertEquals("0.070", quote.getRate().toString(), "Loan rate is correct");
		assertEquals("30.78", quote.getMonthlyRepayment().toString(), "Loan monthly repayment is correct");
		assertEquals("1108.10", quote.getTotalRepayment().toString(), "Loan total repayment is correct");
	}
	
	@Test
	void testCantOfferQuote() {
		final int loanAmount = 1000;
		CannotOfferQuote error = null;
		try {
			new Quote(readToList(), 10000000);
		} catch (CannotOfferQuote e) {
			error = e;
		}
		
		assertNotNull(error, "Market should not be able to offer " + loanAmount);
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
