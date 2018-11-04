package zopa;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import zopa.exception.CannotOfferQuote;
import zopa.exception.CsvDataParseError;
import zopa.model.MarketData;

/**
 * Handle CLI commands.
 */
public class Cli {

	private static final String CMD_QUOTE = "quote.exe";

	public static void main(String[] args) {
		List<MarketData> dataList;
		if (args.length < 3) {
			System.out.println("Syntax: [application] [market_file] [loan_amount]");
			return;
		} else {
			try {
				dataList = CsvImport.beanList(args[1], MarketData.class);
			} catch (IOException e) {
				System.out.println("Unable to read file");
				return;
			} catch (CsvDataParseError e) {
				System.out.println("Error parsing CSV data");
				return;
			}
		}

		if (args[0].equals(CMD_QUOTE)) {
			handleQuote(dataList, args[2]);
		} else {
			System.out.println("Unknown application");
		}
	}

	private static void handleQuote(List<MarketData> dataList, String loanAmountStr) {
		Integer loanAmount = null;
		Quote quote = null;
		try {
			loanAmount = Integer.parseInt(loanAmountStr);
		} catch (NumberFormatException e) {
			System.out.println("loan_amount must be a number");
			return;
		}
		try {
			quote = new Quote(dataList, loanAmount);
		} catch (CannotOfferQuote e) {
			System.out.println("Can't offer this quote");
			return;
		}
	}

}
