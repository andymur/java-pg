package com.andymur.pg.java.hackerrank;

/**
 * Created by andymur on 6/24/17.
 *///Complete this code or write your own from scratch

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class DictAndMapsRunner {
    public static void main(String []argh) {
        //TODO: try with pre allocation
        long start =  System.currentTimeMillis();
        Map<String, Integer> dictionary = new HashMap<>();

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        for(int i = 0; i < n; i++){
            String name = in.next();
            int phone = in.nextInt();
            // Write code here
            dictionary.put(name, phone);
        }

        while(in.hasNext()){
            String s = in.next();
            // Write code here
            Integer number = dictionary.get(s);

            if (number == null) {
                System.out.println("Not found");
            } else {
                System.out.println(String.format("%s=%d", s, number));
            }
        }

        in.close();

        System.out.println(System.currentTimeMillis() - start);
    }
}

