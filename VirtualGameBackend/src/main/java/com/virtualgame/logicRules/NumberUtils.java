package com.virtualgame.logicRules;

import java.util.Random;

/**
 * PROGRAM: NumberUtils
 * AUTHOR:  Diego Balaguer
 * DATE:    19/02/2025
 */

public class NumberUtils {

    public static boolean isNumber(String input) {
        return input != null && input.matches("-?\\d+(\\.\\d+)?");
    }

    public static int random(int num_min, int num_max) {
        // Generate a random number
        Random random = new Random();
        return random.nextInt(num_min, num_max + 1);
    }
}
