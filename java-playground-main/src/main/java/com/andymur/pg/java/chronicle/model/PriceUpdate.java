package com.andymur.pg.java.chronicle.model;

import java.io.*;
import java.util.Arrays;

public class PriceUpdate implements Externalizable {
    private int[] amount;
    private double[] price;

    public PriceUpdate(int[] amount,
                       double[] price) {
        this.amount = amount;
        this.price = price;
    }

    public int getAmount(int index) {
        return amount[index];
    }

    public double getPrice(int index) {
        return price[index];
    }

    public void setAmount(int index, int amountValue) {
        amount[index] = amountValue;
    }
    public void setPrice(int index, double priceValue) {
        price[index] = priceValue;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(amount.length);
        for (int i = 0; i < amount.length; i++) {
            out.writeInt(amount[i]);
        }
        out.writeInt(price.length);
        for (int i = 0; i < price.length; i++) {
            out.writeDouble(price[i]);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int amountNum = in.readInt();
        amount = new int[amountNum];
        for (int i = 0; i < amountNum; i++) {
            amount[i] = in.readInt();
        }
        int priceNum = in.readInt();
        price = new double[priceNum];
        for (int i = 0; i < priceNum; i++) {
            price[i] = in.readDouble();
        }
    }

    @Override
    public String toString() {
        return "PriceUpdate{" +
                "amount=" + Arrays.toString(amount) +
                ", price=" + Arrays.toString(price) +
                '}';
    }
}
