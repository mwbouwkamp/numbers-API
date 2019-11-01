package nl.limakajo.numbers.RABO.levelMaker;

import nl.limakajo.numbers.RABO.API.entity.Level;
import org.junit.jupiter.api.Test;

import static nl.limakajo.numbers.RABO.API.entity.Level.NUMTILES;
import static org.junit.jupiter.api.Assertions.*;

class LevelGeneratorTest {

    @Test
    void testGenerateLevel() {
        Level generatedLevel  = LevelGenerator.generateLevel();
        assertEquals(3 * (NUMTILES + 1), generatedLevel.getNumbers().length());
        assertEquals(45000, generatedLevel.getAverageTime());
        assertEquals(1, generatedLevel.getTimesPlayed());
    }

    @Test
    void testAddNuberToStringBuilderSingleDigit() {
        StringBuilder resultingStringBuilder = LevelGenerator.addNumberToStringBuilder(new StringBuilder("abc"), 1);
        assertEquals("abc001", resultingStringBuilder.toString());
    }

    @Test
    void testAddNuberToStringBuilderDoubleDigits() {
        StringBuilder resultingStringBuilder = LevelGenerator.addNumberToStringBuilder(new StringBuilder("abc"), 11);
        assertEquals("abc011", resultingStringBuilder.toString());
    }

    @Test
    void testAddNuberToStringBuilderTripleDigits() {
        StringBuilder resultingStringBuilder = LevelGenerator.addNumberToStringBuilder(new StringBuilder("abc"), 111);
        assertEquals("abc111", resultingStringBuilder.toString());
    }

    @Test
    void testGetNumberLeadingZerosSingleDigit() {
        int numberZeros = LevelGenerator.getNumberLeadingZeros(1);
        assertEquals(2, numberZeros);
    }

    @Test
    void testGetNumberLeadingZerosDoubleDigits() {
        int numberZeros = LevelGenerator.getNumberLeadingZeros(11);
        assertEquals(1, numberZeros);
    }

    @Test
    void testGetNumberLeadingZerosTripleDigits() {
        int numberZeros = LevelGenerator.getNumberLeadingZeros(111);
        assertEquals(0, numberZeros);
    }

}