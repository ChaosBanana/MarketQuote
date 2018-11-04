package zopa;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CliTest {

	private static final String DATA_PATH = "./src/test/resources/data.csv";
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	/**
	 * Capture system output for analyzing CLI output
	 */
	@BeforeEach
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}

	@AfterEach
	public void restoreStreams() {
		System.setOut(originalOut);
	}

	@Test
	void testQuote() {
		String[] args = { "quote.exe", DATA_PATH, "1000" };
		Cli.main(args);
	}

	@Test
	void testMissingArguments() {
		String[] args = {};
		Cli.main(args);
		assertEquals(String.format("Syntax: [application] [market_file] [loan_amount]%n"), outContent.toString());
	}

	@Test
	void testIllegalLoan() {
		String[] args = { "quote.exe", DATA_PATH, "1500a" };
		Cli.main(args);
		assertEquals(String.format("loan_amount must be a number%n"), outContent.toString());
	}

	@Test
	void testNonExistingFile() {
		String[] args = { "quote.exe", "nonexistingfile", "1500" };
		Cli.main(args);
		assertEquals(String.format("Unable to read file%n"), outContent.toString());
	}

}
