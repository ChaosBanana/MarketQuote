package zopa.util;

import java.math.BigDecimal;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

public class QuoteAlgorithm {

	public static BigDecimal totalRepayment(BigDecimal monthlyRepayment, int months) {
		// Total repayment: monthlyRepayment * months
		return monthlyRepayment.multiply(new BigDecimal(months));
	}

	public static BigDecimal monthlyRepayment(final int amount, final BigDecimal rate, final int months,
			final long precision) {
		// Monthly repayment: (monthlyRate * amount)/(1 - (1 + rate)^-months)
		Apfloat monthlyRate = new Apfloat(monthlyRate(rate, precision).toString());

		Apfloat division = new Apfloat("1", precision).subtract(
				ApfloatMath.pow(new Apfloat("1", precision).add(monthlyRate), new Apfloat(-months, precision)));
		return new BigDecimal(monthlyRate.multiply(new Apfloat(amount, precision)).divide(division).toString());
	}

	public static BigDecimal monthlyRate(final BigDecimal rate, final long precision) {
		// Monthly rate: (rate + 1)^(1/12) - 1
		return new BigDecimal(ApfloatMath
				.pow(new Apfloat(rate, precision).add(new Apfloat("1", precision)),
						new Apfloat("1", precision).divide(new Apfloat("12", precision)))
				.subtract(new Apfloat("1", precision)).toString());
	}

}
