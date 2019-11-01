package nl.limakajo.numbers.RABO.levelMaker;

import nl.limakajo.numbers.RABO.API.entity.Level;

import java.util.Arrays;

class LevelGenerator {

    /**
     * Generates a random level with NUMTILES numbers (e.g., "001002003004005006007"),
     * a default averageTime of 45000 and default timesPlayed of 1
     *
     * @return      random level
     */
    static Level generateLevel() {
        int[] hand = new int[Level.NUMTILES];
        for (int i = 0; i < Level.NUMTILES; i++) {
            hand[i] = Level.NUMBERS[(int) (14 * Math.random())];
        }
        int goal = 100 + (int) (900 * Math.random());
        StringBuilder stringBuilder = new StringBuilder();
        for (int number: hand) {
            stringBuilder = addNumberToStringBuilder(stringBuilder, number);
        }
        String levelNumbers = addNumberToStringBuilder(stringBuilder, goal).toString();
        return new Level(levelNumbers, 45000, 1);
    }

    /**
     * Adds number is "###" format to StringBuilder
     *
     * @param stringBuilder     StringBuilder to add to
     * @param number            number to add
     * @return                  adapted StringBuilder
     */
    public static StringBuilder addNumberToStringBuilder(StringBuilder stringBuilder, int number) {
        for (int i = 0; i < getNumberLeadingZeros(number); i++) {
            stringBuilder.append("0");
        }
        stringBuilder.append(String.valueOf(number));
        return stringBuilder;
    }

    /**
     * Calculate the number of zero's that need to be added (leading zero's) to convert a number to "###" format
     *
     * @param number    number to consider
     * @return          number of zero's to add
     */
    static int getNumberLeadingZeros(int number) {
        int numberLeadingZeros = 0;
        while (number / 10 > 0) {
            numberLeadingZeros++;
            number /= 10;
        }
        return 2 - numberLeadingZeros;
    }
}
