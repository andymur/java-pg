package com.andymur.pg.pocket.model.portfolio;

import com.andymur.pg.pocket.model.asset.BankDeposit;
import com.andymur.pg.pocket.model.asset.base.AssetValue;
import com.andymur.pg.pocket.model.label.Currency;
import com.andymur.pg.pocket.scrapping.RateHolder;
import com.andymur.pg.pocket.scrapping.RateHolderImpl;
import com.andymur.pg.pocket.scrapping.ScrapperMock;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

public class PortfolioTest {

    private Portfolio portfolio;
    private RateHolder rateHolder = new RateHolderImpl(new ScrapperMock(Collections.emptySet()));

    @Test
    public void testPortfolioInUsdCalculatedInUsdIsCorrect() {
        portfolio = new PortfolioImpl(rateHolder);
        portfolio.addAsset(new BankDeposit(Currency.USD, new BigDecimal("100000")));
        AssetValue calculatedAssetValue = portfolio.calculatePortfolio(Currency.USD);
        Assert.assertThat("Portfolio calculated in the USD", calculatedAssetValue.getMeasurement(),
                CoreMatchers.equalTo(Currency.USD));
        Assert.assertThat("Value should be equal 100k USD", formatAmount(calculatedAssetValue.getValue()),
                CoreMatchers.equalTo(formatAmount(new BigDecimal("100000"))));
    }

    private static String formatAmount(BigDecimal amount) {
        return amount.toPlainString();
    }
}