package com.andymur.pg.pocket.model.label;

import com.andymur.pg.pocket.model.label.base.LabeledMeasurement;
import com.andymur.pg.pocket.model.label.base.MeasureUnit;

public enum  Currency implements LabeledMeasurement {

    AED("AED"), AFN("AFN"), ALL("ALL"), AMD("AMD"), ANG("ANG"), AOA("AOA"),
    ARS("ARS"), AUD("AUD"), AWG("AWG"), AZN("AZN"), BAM("BAM"), BBD("BBD"),
    BDT("BDT"), BGN("BGN"), BHD("BHD"), BIF("BIF"), BMD("BMD"), BND("BND"),
    BOB("BOB"), BRL("BRL"), BSD("BSD"), BTC("BTC"), BTN("BTN"), BWP("BWP"),
    BYN("BYN"), BZD("BZD"), CAD("CAD"), CDF("CDF"), CHF("CHF"), CLF("CLF"),
    CLP("CLP"), CNH("CNH"), CNY("CNY"), COP("COP"), CRC("CRC"), CUC("CUC"),
    CUP("CUP"), CVE("CVE"), CZK("CZK"), DJF("DJF"), DKK("DKK"), DOP("DOP"),
    DZD("DZD"), EGP("EGP"), ERN("ERN"), ETB("ETB"), EUR("EUR"), FJD("FJD"),
    FKP("FKP"), GBP("GBP"), GEL("GEL"), GGP("GGP"), GHS("GHS"), GIP("GIP"),
    GMD("GMD"), GNF("GNF"), GTQ("GTQ"), GYD("GYD"), HKD("HKD"), HNL("HNL"),
    HRK("HRK"), HTG("HTG"), HUF("HUF"), IDR("IDR"), ILS("ILS"), IMP("IMP"),
    INR("INR"), IQD("IQD"), IRR("IRR"), ISK("ISK"), JEP("JEP"), JMD("JMD"),
    JOD("JOD"), JPY("JPY"), KES("KES"), KGS("KGS"), KHR("KHR"), KMF("KMF"),
    KPW("KPW"), KRW("KRW"), KWD("KWD"), KYD("KYD"), KZT("KZT"), LAK("LAK"),
    LBP("LBP"), LKR("LKR"), LRD("LRD"), LSL("LSL"), LYD("LYD"), MAD("MAD"),
    MDL("MDL"), MGA("MGA"), MKD("MKD"), MMK("MMK"), MNT("MNT"), MOP("MOP"),
    MRO("MRO"), MRU("MRU"), MUR("MUR"), MVR("MVR"), MWK("MWK"), MXN("MXN"),
    MYR("MYR"), MZN("MZN"), NAD("NAD"), NGN("NGN"), NIO("NIO"), NOK("NOK"),
    NPR("NPR"), NZD("NZD"), OMR("OMR"), PAB("PAB"), PEN("PEN"), PGK("PGK"),
    PHP("PHP"), PKR("PKR"), PLN("PLN"), PYG("PYG"), QAR("QAR"), RON("RON"),
    RSD("RSD"), RUB("RUB"), RWF("RWF"), SAR("SAR"), SBD("SBD"), SCR("SCR"),
    SDG("SDG"), SEK("SEK"), SGD("SGD"), SHP("SHP"), SLL("SLL"), SOS("SOS"),
    SRD("SRD"), SSP("SSP"), STD("STD"), STN("STN"), SVC("SVC"), SYP("SYP"),
    SZL("SZL"), THB("THB"), TJS("TJS"), TMT("TMT"), TND("TND"), TOP("TOP"),
    TRY("TRY"), TTD("TTD"), TWD("TWD"), TZS("TZS"), UAH("UAH"), UGX("UGX"),
    USD("USD"), UYU("UYU"), UZS("UZS"), VEF("VEF"), VES("VES"), VND("VND"),
    VUV("VUV"), WST("WST"), XAF("XAF"), XAG("XAG"), XAU("XAU"), XCD("XCD"),
    XDR("XDR"), XOF("XOF"), XPD("XPD"), XPF("XPF"), XPT("XPT"), YER("YER"),
    ZAR("ZAR"), ZMW("ZMW"), ZWL("ZWL");

    private final String symbol;

    Currency(final String symbol) {
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
