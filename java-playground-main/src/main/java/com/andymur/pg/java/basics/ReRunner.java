package com.andymur.pg.java.basics;

public class ReRunner {
	public static final String TAG_NO_STRATEGY_PARAMETERS = "957";

	public static void main(String[] args) {
		String startPattern = "\\d+=\\w+\\|" + TAG_NO_STRATEGY_PARAMETERS + "=\\d+\\|(.*)";

		String fixMessage = "22213=231|957=6|958=p_Price|959=8|960=1000.00|958=Enabled Price Sensitivity|959=14|960=No|958=Min Qty Pct|959=1|960=100|958=Liquidity Source|959=1|960=1|958=ExpiryTypeGFD|959=13|960=N|958=StartTimeImmediately|959=13|960=N";


		System.out.println(fixMessage.matches(startPattern));
	}
}
