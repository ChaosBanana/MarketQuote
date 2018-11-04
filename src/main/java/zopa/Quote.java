package zopa;

import java.util.List;

import zopa.model.MarketData;

public class Quote {
	
	private final List<MarketData> dataList;
	private final int loanAmount;

	public Quote(final List<MarketData> dataList, final int loanAmount) {
		this.dataList = dataList;
		this.loanAmount = loanAmount;
		
        for (MarketData data : dataList) {
        	System.out.println(data);
        }
	}

}
