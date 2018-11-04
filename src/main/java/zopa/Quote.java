package zopa;

import java.math.BigDecimal;
import java.util.List;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

import zopa.model.MarketData;

public class Quote {
	
	public static final long PRECISION = 20L;
	public static final int MONTHS = 36;

	private final List<MarketData> dataList;
	private final int loanAmount;

	public Quote(final List<MarketData> dataList, final int loanAmount) {
		this.dataList = dataList;
		this.loanAmount = loanAmount;

		for (MarketData data : dataList) {
			System.out.println(data);
		}
	}
	
	public static BigDecimal totalRepayment(BigDecimal monthlyRepayment) {
		// Total repayment: monthlyRepayment * months
		return monthlyRepayment.multiply(new BigDecimal(MONTHS));
	}
	
	public static BigDecimal monthlyRepayment(int amount, String interest) {
		// Monthly repayment: (rate * amount)/(1 - (1 + rate)^-months)
		Apfloat rate = new Apfloat(monthlyRate(interest).toString());
		int months = MONTHS;
		
		Apfloat division = new Apfloat("1", PRECISION).subtract(
				ApfloatMath.pow(
					new Apfloat("1", PRECISION).add(rate),
					new Apfloat(-months, PRECISION)
				));
		return new BigDecimal(rate.multiply(new Apfloat(amount, PRECISION))
				.divide(division).toString());
	}

	public static BigDecimal monthlyRate(String interest) {
		// Rate: (interest + 1)^(1/12) - 1
		return new BigDecimal(ApfloatMath.pow(
				new Apfloat("0.07", PRECISION).add(new Apfloat("1", PRECISION)),
				new Apfloat("1", PRECISION).divide(new Apfloat("12", PRECISION))
			).subtract(new Apfloat("1", PRECISION)).toString());
	}

}
