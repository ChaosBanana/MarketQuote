package zopa.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class MarketDataTest {
	
	private static final MarketData TEST_MARKET_DATA = new MarketData("a", new BigDecimal("0.01"), 100);

	@Test
	void testEqual() {
		MarketData expected = new MarketData("a", new BigDecimal("0.01"), 100);
		assertEquals(expected, TEST_MARKET_DATA);
	}
	
	@Test
	void testNotEqual() {
		MarketData unexpected = new MarketData("b", new BigDecimal("0.01"), 100);
		assertNotEquals(unexpected, TEST_MARKET_DATA);
	}
	
	@Test
	void testHashCode() {
		MarketData expected = new MarketData("a", new BigDecimal("0.01"), 100);
		assertEquals(expected.hashCode(), TEST_MARKET_DATA.hashCode());
	}

}
