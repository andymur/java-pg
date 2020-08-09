package com.andymur.pg.java.chronicle;

import com.andymur.pg.java.chronicle.model.PriceUpdate;

public class ChronicleRunner {
    public static void main(String[] args) {
        /*ChronicleWriter chronicleWriter = new ChronicleWriter("/home/antti/workspace");


        PriceUpdate priceUpdate = new PriceUpdate(new int[] {100, 200, 500}, new double[] {1.057, 1.067, 1.115});
        for (int i = 0; i < 1000000; i++) {
            chronicleWriter.write(priceUpdate);
            System.out.println(i);
        }*/
        ChronicleReader chronicleReader = new ChronicleReader("/home/antti/workspace");
        final PriceUpdate priceUpdate = chronicleReader.read();
        System.out.println(priceUpdate);
    }
}