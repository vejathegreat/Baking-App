package com.nanodegree.bakingapp.utils;

public class Converters {

    public static String units(Double amount, String symbol) {

        switch (symbol) {

            case "K":
                if (amount > 1) {
                    return "Kilograms";
                } else {
                    return "Kilogram";
                }

            case "G":
                if (amount > 1) {
                    return "Grams";
                } else {
                    return "Gram";
                }

            case "CUP":
                if (amount > 1) {
                    return "Cups";
                } else {
                    return "Cup";
                }

            case "TBLSP":
                if (amount > 1) {
                    return "Tablespoons";
                } else {
                    return "Tablespoon";
                }
            case "TSP":
                if (amount > 1) {
                    return "Teaspoons";
                } else {
                    return "Teaspoon";
                }

            case "OZ":
                if (amount > 1) {
                    return "Ounces";
                } else {
                    return "Ounce";
                }
        }

        return symbol;
    }
}
