package zopa;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.jupiter.api.Test;

import zopa.exception.CsvDataParseError;
import zopa.model.MarketData;

class CsvImportTest {
	
	private static final String TEST_DATA_PATH = "./src/test/resources/data.csv";
	private static final MarketData TEST_ITEM = new MarketData("Fred", new BigDecimal("0.071"), 520);

	/**
	 * Test {@code CsvImport.beanList} reads all items
	 */
	@Test
	void testBeanListSize() {
		assertEquals(7, readToList().size(), "List has correct amount of items");
	}
	
	/**
	 * Test {@code CsvImport.beanIterator} reads all items
	 */
	@Test
	void testBeanIteratorSize() {
		assertEquals(7, readWithIteratorToList().size(), "Iterator has correct amount of items");
	}
	
	/**
	 * Test {@code CsvImport.beanList} reads items with correct details
	 */
	@Test
	void testBeanListItem() {
		assertThat(readToList(), IsCollectionContaining.hasItem(TEST_ITEM));
	}
	
	/**
	 * Test {@code CsvImport.beanIterator} reads items with correct details
	 */
	@Test
	void testBeanIteratorItem() {
		assertThat(readWithIteratorToList(), IsCollectionContaining.hasItem(TEST_ITEM));
	}
	
	private List<MarketData> readToList() {
		try {
			return CsvImport.beanList(TEST_DATA_PATH, MarketData.class);
		} catch (IOException | CsvDataParseError e) {
			fail("Error reading file", e);
			return null;
		}
	}
	
	private List<MarketData> readWithIteratorToList() {
		try (Reader reader = Files.newBufferedReader(Paths.get(TEST_DATA_PATH))) {
			Iterator<MarketData> iterator = CsvImport.beanIterator(reader, MarketData.class);
			List<MarketData> list = new ArrayList<>();
			iterator.forEachRemaining(list::add);
			return list;
		} catch (IOException e) {
			fail("Error reading file", e);
			return null;
		}
	}

}
