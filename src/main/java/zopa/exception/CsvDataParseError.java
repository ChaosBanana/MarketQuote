package zopa.exception;

/**
 * Error parsing CSV data.
 */
public class CsvDataParseError extends Exception {

	private static final long serialVersionUID = -8354831995891793970L;
	
	public CsvDataParseError() { super(); }
	public CsvDataParseError(String message) { super(message); }
	public CsvDataParseError(Throwable t) { super(t); }
	public CsvDataParseError(String message, Throwable t) { super(message, t); }
	
}
