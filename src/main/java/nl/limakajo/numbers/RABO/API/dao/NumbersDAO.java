package nl.limakajo.numbers.RABO.API.dao;

import nl.limakajo.numbers.RABO.API.entity.Level;

import java.util.List;

public interface NumbersDAO {

    /**
     * Retrieves a List of Levels from the database
     *
     * @return      List of Levels
     */
    public List<Level> getLevels();

    /**
     * Retrieves a single Level based on numbers
     *
     * @param numbers   representation of the numbers
     * @return          Level
     */
    public Level getLevelByNumbers(String numbers);

    /**
     * Retrieves a single Level that has an averageTime that is closest to the provided averageTime
     *
     * @param averageTime   the averageTime
     * @return              Level
     */
    public Level getLevelByClosestAverageTime(int averageTime);

    /**
     * Saves Level to the database
     *
     * @param level     The Level to write to the database
     */
    public void saveLevel(Level level);

    /**
     * Updates level in the database
     *
     * @param level     The Level to update in the database
     */
    public void updateLevel(Level level);

    /**
     * Deletes level in the database
     *
     * @param id     The Level to delete from the database
     */
    public void deleteLevel(int id);

}
