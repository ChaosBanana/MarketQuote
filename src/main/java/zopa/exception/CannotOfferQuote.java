package zopa.exception;

/**
 * Error parsing CSV data.
 */
public class CannotOfferQuote extends Exception {
	
	private static final long serialVersionUID = -7762499361314701559L;
	
	public CannotOfferQuote() { super(); }
	public CannotOfferQuote(String message) { super(message); }
	public CannotOfferQuote(Throwable t) { super(t); }
	public CannotOfferQuote(String message, Throwable t) { super(message, t); }
	
}
