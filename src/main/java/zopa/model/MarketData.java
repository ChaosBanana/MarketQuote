package zopa.model;

import java.math.BigDecimal;
import java.util.Objects;

import com.opencsv.bean.CsvBindByName;

public class MarketData {
	
	@CsvBindByName(required = true)
    private String lender;

    @CsvBindByName(required = true)
    private BigDecimal rate;

    @CsvBindByName(required = true)
    private int available;
    
    public MarketData() {}
    
    public MarketData(String lender, BigDecimal rate, int available) {
    	this.lender = lender;
    	this.rate = rate;
    	this.available = available;
    }

	public String getLender() {
		return lender;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public int getAvailable() {
		return available;
	}
	
	@Override
	public String toString() {
		return "[" + this.getLender() + ", r" + this.getRate() + ", a:" + this.getAvailable() + "]";
	}
	
	@Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof MarketData)) {
            return false;
        }
        MarketData other = (MarketData) o;
        return Objects.equals(lender, other.lender) &&
        		available == other.available &&
                Objects.equals(rate, other.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lender, available, rate);
    }

}
