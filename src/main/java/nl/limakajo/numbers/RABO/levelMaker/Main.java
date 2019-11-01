package nl.limakajo.numbers.RABO.levelMaker;

import nl.limakajo.numbers.RABO.API.entity.Level;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        LevelCollection levelCollection = new LevelCollection();
        for (int i = 0; i < 10; i++) {
            levelCollection.addValidLevel();
        }
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("query.txt"));
            StringBuilder queryBuilder = new StringBuilder("INSERT INTO leveldata (numbers, averagetime, timesplayed) VALUES ");
            for (int i = 0; i < levelCollection.size(); i++) {
                Level level = levelCollection.getLevel(i);
                String solution = levelCollection.getSolution(i);
                writer.write("\n---" + i + "---");
                writer.write("\nLEVEL    : " + level);
                writer.write("\nSOLUTION : " + solution + " (targetTime: " + calculateTargetTime(solution) + ")");

                addLevelToQueryBuilder(queryBuilder, level, calculateTargetTime(solution));
            }
            String query = queryBuilder.toString().substring(0, queryBuilder.length() - 2) + ";";
            writer.write("\n");
            writer.write("\n---QUERY---\n" + query);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addLevelToQueryBuilder(StringBuilder queryBuilder, Level level, int targetTime) {
        queryBuilder
                .append("(")
                .append(level)
                .append(", ")
                .append(targetTime)
                .append(", ")
                .append(1)
                .append("), ");
    }

    private static int calculateTargetTime(String solution) {
        return (int) solution.chars().filter(c -> c == '[' || c == ',').count() * 1_000 + 40_000;
    }
}
