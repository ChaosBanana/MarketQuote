package zopa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import zopa.model.MarketData;
import zopa.util.QuoteAlgorithm;

public class Quote {

	public static final int PRECISION = 20;
	public static final int MONTHS = 36;

	private final List<MarketData> dataList;
	private final int loanAmount;
	private BigDecimal rate;
	private BigDecimal monthlyRepayment;
	private BigDecimal totalRepayment;

	public Quote(final List<MarketData> dataList, final int loanAmount) {
		this.dataList = new ArrayList<>(dataList);
		this.dataList.sort((o1, o2) -> {
			return o1.getRate().compareTo(o2.getRate());
		});
		this.loanAmount = loanAmount;
		generateQuote();
	}

	private void generateQuote() {
		List<QuotePart> quoteParts = this.getQuoteParts();
		this.joinQuoteParts(quoteParts);
	}
	
	private List<QuotePart> getQuoteParts() {
		List<QuotePart> quoteParts = new LinkedList<>();
		int remainingAmount = this.loanAmount;
		for (MarketData data : dataList) {
			int amount = data.getAvailable();
			if (amount > remainingAmount) {
				amount = remainingAmount;
			}
			remainingAmount -= amount;

			BigDecimal rate = data.getRate();
			BigDecimal monthlyRepayment = QuoteAlgorithm.monthlyRepayment(this.loanAmount, rate, MONTHS, PRECISION);
			BigDecimal totalRepayment = QuoteAlgorithm.totalRepayment(monthlyRepayment, MONTHS);
			
			QuotePart quotePart = new QuotePart();
			quotePart.amount = amount;
			quotePart.rate = rate;
			quotePart.monthlyRepayment = monthlyRepayment;
			quotePart.totalRepayment = totalRepayment;
			quoteParts.add(quotePart);
			
			if (remainingAmount <= 0) {
				break;
			}
		}
		return quoteParts;
	}
	
	private void joinQuoteParts(List<QuotePart> quoteParts) {
		BigDecimal loanAmountDecimal = new BigDecimal(this.loanAmount);
		
		BigDecimal rate = new BigDecimal(0);
		BigDecimal monthlyRepayment = new BigDecimal(0);
		BigDecimal totalRepayment = new BigDecimal(0);
		for (QuotePart quotePart : quoteParts) {
			BigDecimal partShare = new BigDecimal(quotePart.amount).divide(loanAmountDecimal, PRECISION, RoundingMode.HALF_UP);
			
			// Joint rate: part1.rate * (part1.amount/loanAmount) + part2...
			rate = rate.add(quotePart.rate.multiply(partShare));
			monthlyRepayment = monthlyRepayment.add(quotePart.monthlyRepayment.multiply(partShare));
			totalRepayment = totalRepayment.add(quotePart.totalRepayment.multiply(partShare));
		}
		
		this.rate = rate.setScale(3, RoundingMode.HALF_UP);
		this.monthlyRepayment = monthlyRepayment.setScale(2, RoundingMode.HALF_UP);
		this.totalRepayment = totalRepayment.setScale(2, RoundingMode.HALF_UP);
	}
	
	private static class QuotePart {
		public int amount;
		public BigDecimal rate;
		public BigDecimal monthlyRepayment;
		public BigDecimal totalRepayment;
	}
	
	public int getLoanAmount() {
		return loanAmount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public BigDecimal getMonthlyRepayment() {
		return monthlyRepayment;
	}

	public BigDecimal getTotalRepayment() {
		return totalRepayment;
	}

}
