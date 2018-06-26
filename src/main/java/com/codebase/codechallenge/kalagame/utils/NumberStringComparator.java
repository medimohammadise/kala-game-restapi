package com.codebase.codechallenge.kalagame.utils;

import java.util.Comparator;

public class NumberStringComparator implements Comparator<String> {
    @Override
    public int compare(String index1, String index2) {
        System.out.println(index1 + "   "+ index2 + " Compare");
        return Integer.compare(Integer.valueOf(index1),Integer.valueOf(index1));
    }
}
