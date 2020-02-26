package com.andymur.pg.pocket.model.label.base;

import com.andymur.pg.pocket.model.label.LabeledMeasurement;
import com.andymur.pg.pocket.model.label.MeasureUnit;

public enum  Currency implements LabeledMeasurement {
    AED(""), AFN(""), ALL(""), AMD(""), ANG(""), AOA(""), ARS(""), AUD(""), AWG(""), AZN(""),
    BAM(""), BBD(""), BDT(""), BGN(""), BHD(""), BIF(""), BMD(""), BND(""), BOB(""), BRL(""),
    BSD(""), BTC(""), BTN(""), BWP(""), BYN(""), BZD(""), CAD(""), CDF(""), CHF(""), CLF(""),
    CLP(""), CNH(""), CNY(""), COP(""), CRC(""), CUC(""), CUP(""), CVE(""), CZK(""), DJF(""),
    DKK(""), DOP(""), DZD(""), EGP(""), ERN(""), ETB(""), EUR("EUR"), FJD(""), FKP(""), GBP(""),
    GEL(""), GGP(""), GHS(""), GIP(""), GMD(""), GNF(""), GTQ(""), GYD(""), HKD(""), HNL(""),
    HRK(""), HTG(""), HUF(""), IDR(""), ILS(""), IMP(""), INR(""), IQD(""), IRR(""), ISK(""),
    JEP(""), JMD(""), JOD(""), JPY(""), KES(""), KGS(""), KHR(""), KMF(""), KPW(""), KRW(""),
    KWD(""), KYD(""), KZT(""), LAK(""), LBP(""), LKR(""), LRD(""), LSL(""), LYD(""), MAD(""),
    MDL(""), MGA(""), MKD(""), MMK(""), MNT(""), MOP(""), MRO(""), MRU(""), MUR(""), MVR(""),
    MWK(""), MXN(""), MYR(""), MZN(""), NAD(""), NGN(""), NIO(""), NOK(""), NPR(""), NZD(""),
    OMR(""), PAB(""), PEN(""), PGK(""), PHP(""), PKR(""), PLN(""), PYG(""), QAR(""), RON(""),
    RSD(""), RUB("RUB"), RWF(""), SAR(""), SBD(""), SCR(""), SDG(""), SEK(""), SGD(""), SHP(""),
    SLL(""), SOS(""), SRD(""), SSP(""), STD(""), STN(""), SVC(""), SYP(""), SZL(""), THB(""),
    TJS(""), TMT(""), TND(""), TOP(""), TRY(""), TTD(""), TWD(""), TZS(""), UAH(""), UGX(""),
    USD("USD"), UYU(""), UZS(""), VEF(""), VES(""), VND(""), VUV(""), WST(""), XAF(""), XAG(""),
    XAU(""), XCD(""), XDR(""), XOF(""), XPD(""), XPF(""), XPT(""), YER(""), ZAR(""), ZMW(""),
    ZWL("");

    private final String symbol;

    private Currency(final String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public MeasureUnit getUnit() {
        return MeasureUnit.CCY_UNIT;
    }

}
