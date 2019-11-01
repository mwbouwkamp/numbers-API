package nl.limakajo.numbers.RABO.API.dao;

import nl.limakajo.numbers.RABO.API.entity.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class NumbersDAOJPA implements NumbersDAO {

    EntityManager entityManager;

    @Autowired
    public NumbersDAOJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Level> getLevels() {
        Query query = entityManager.createQuery("FROM Level", Level.class);
        return query.getResultList();
    }

    @Override
    public Level getLevelByNumbers(String number) {
        TypedQuery<Level> query = entityManager.createQuery("FROM Level WHERE numbers = :n", Level.class);
        return query.setParameter("n", number).getSingleResult();
    }

    @Override
    public Level getLevelByClosestAverageTime(int averageTime) {
        List<Level> levels = getLevels();
        return levels.stream().reduce((a, b) -> averageTimeACloser(averageTime, a, b) ? a : b).orElse(null);
    }

    /**
     * Checks wheather the averageTime of LevelRep a is closer than that of LevelRep b
     * If two are closest, the methods will return true
     *
     * @param averageTime   the reference averagetime
     * @param a     LevelRep A
     * @param b     LevelRep B
     * @return      true if a closer than b or when the difference is the same
     */
    public boolean averageTimeACloser(int averageTime, Level a, Level b) {
        return Math.abs(a.getAverageTime() - averageTime) <= Math.abs(b.getAverageTime() - averageTime);
    }

    @Override
    @Transactional
    public void saveLevel(Level level) {
        entityManager.persist(level);
    }

    @Override
    @Transactional
    public void updateLevel(Level level) {
        Level current = getLevelByNumbers(level.getNumbers());
        level.setId(current.getId());
        entityManager.merge(level);
    }

    @Override
    @Transactional
    public void deleteLevel(int id) {
        Query query = entityManager.createQuery("delete from Level where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
