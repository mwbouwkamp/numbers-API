package nl.limakajo.numbers.RABO.levelMaker;

import nl.limakajo.numbers.RABO.API.entity.Level;

import java.util.LinkedList;
import java.util.List;

public class LevelCollection {
    private List<Level> levels;
    private List<String> solutions;

    /**
     * Constructs a LevelCollectoin
     */
    LevelCollection() {
        this.levels = new LinkedList<>();
        this.solutions = new LinkedList<>();
    }

    /**
     * Adds a level to the collection
     *
     * @param level         the Level to add
     * @param solution      the solution to the level
     */
    public void add(Level level, String solution) {
        levels.add(level);
        solutions.add(solution);
    }

    /**
     * Adds a valid Level to the collection
     */
    void addValidLevel() {
        boolean validLevelFound = false;
        while (!validLevelFound) {
            Level level = LevelGenerator.generateLevel();
            String solution = Solver.solvableLevel(level);
            if (null != solution) {
                levels.add(level);
                solutions.add(solution);
                validLevelFound = true;
            }
        }
    }

    /**
     * Returns the number of elements in the collection
     *
     * @return      the number of elements in the collection
     */
    int size() {
        return levels.size();
    }

    //Getters

    Level getLevel(int i) {
        return levels.get(i);
    }

    List<Level> getLevels() {
        return levels;
    }

    String getSolution(int i) {
        return solutions.get(i);
    }
}
