package nl.limakajo.numbers.RABO.levelMaker;

import nl.limakajo.numbers.RABO.API.entity.Level;

class LevelGenerator {

    /**
     * Generates a random level
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

    private static StringBuilder addNumberToStringBuilder(StringBuilder stringBuilder, int number) {
        for (int i = 0; i < getNumberLeadingZeros(number); i++) {
            stringBuilder.append("0");
        }
        stringBuilder.append(String.valueOf(number));
        return stringBuilder;
    }

    static int getNumberLeadingZeros(int number) {
        int numberLeadingZeros = 0;
        while (number / 10 > 1) {
            numberLeadingZeros++;
            number /= 10;
        }
        return numberLeadingZeros;
    }
}
