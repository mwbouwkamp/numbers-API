package nl.limakajo.numbersAPI.service;

import nl.limakajo.numbersAPI.dao.NumbersDAOJPA;
import nl.limakajo.numbersAPI.entity.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class NumbersServiceJPA implements NumbersService {
    private NumbersDAOJPA numbersDAOJPA;

    @Autowired
    public NumbersServiceJPA(NumbersDAOJPA numbersDAOJPA) {
        this.numbersDAOJPA = numbersDAOJPA;
    }

    @Override
    public List<Level> getLevels() {
        return numbersDAOJPA.getLevels();
    }

    @Override
    public Level getLevelByNumbers(String numbers) {
        return numbersDAOJPA.getLevelByNumbers(numbers);
    }

    @Override
    public Level getLevelByClosestAverageTime(int averageTime) {
        return numbersDAOJPA.getLevelByClosestAverageTime(averageTime);
    }
    @Override
    public void saveLevel(Level level) {
        numbersDAOJPA.saveLevel(level);
    }

    @Override
    public void updateLevel(Level level) {
        numbersDAOJPA.updateLevel(level);
    }

    @Override
    public void deleteLevel(int id) {
        numbersDAOJPA.deleteLevel(id);
    }
}
