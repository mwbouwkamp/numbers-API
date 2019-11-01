package nl.limakajo.numbersAPI.dao;

import nl.limakajo.numbersAPI.entity.Level;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NumbersDAOJPATest {

    @Mock
    private EntityManager entityManager;

    Level levelA,  levelB;

    @BeforeAll
    public void before() {
        levelA = new Level("001002003004005006007", 20000, 1);
        levelB = new Level("002004006008010012014", 40000, 1);
    }

    @Test
    public void averageTimeSmallerThanA() {
        int averageTime = 10000;
        assertTrue(new NumbersDAOJPA(entityManager).averageTimeACloser(averageTime, levelA, levelB));
        assertFalse(new NumbersDAOJPA(entityManager).averageTimeACloser(averageTime, levelB, levelA));
    }

    @Test
    public void averageTimeLargerThanAndClosestToA() {
        int averageTime = 25000;
        assertTrue(new NumbersDAOJPA(entityManager).averageTimeACloser(averageTime, levelA, levelB));
        assertFalse(new NumbersDAOJPA(entityManager).averageTimeACloser(averageTime, levelB, levelA));
    }

    @Test
    public void averageTimeRightInBetweenAAndB() {
        int averageTime = 30000;
        assertTrue(new NumbersDAOJPA(entityManager).averageTimeACloser(averageTime, levelA, levelB));
        assertTrue(new NumbersDAOJPA(entityManager).averageTimeACloser(averageTime, levelB, levelA));
    }

}