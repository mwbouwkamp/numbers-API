package nl.limakajo.numbersAPI.rest;

import nl.limakajo.numbersAPI.entity.Level;
import nl.limakajo.numbersAPI.service.NumbersServiceJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LevelRestController {

    private NumbersServiceJPA numbersService;

    @Autowired
    public LevelRestController(NumbersServiceJPA numbersServiceJPA) {
        this.numbersService = numbersServiceJPA;
    }

    @GetMapping("/levels")
    public List<Level> getLevels() {
        return numbersService.getLevels();
    }

    @GetMapping("levels/{levelNumbers}")
    public Level getLevelById(@PathVariable("levelNumbers") String levelNumbers) {
        Level level = numbersService.getLevelByNumbers(levelNumbers);
        if (null == level) {
            throw new RuntimeException("Level with numbers " + levelNumbers + " not found");
        }
        return level;
    }

    @PostMapping("/levels")
    public void saveLevel(@RequestBody Level level) {
        level.setId(0);
        numbersService.saveLevel(level);
    }

    @PutMapping("/levels")
    public void updateLevel(@RequestBody Level level) {
        numbersService.updateLevel(level);
    }

    @DeleteMapping("/levels/{levelNumbers}")
    public void deleteByNumbers(@PathVariable("levelNumbers") String numbers) {
        Level level = numbersService.getLevelByNumbers(numbers);
        if (null == level) {
            throw new RuntimeException("Level with numbers " + numbers + " not found");
        }
        numbersService.deleteLevel(level.getId());
    }

    @GetMapping("/levels/closest/{averageTime}")
    public Level getClosestLevelByAverageTime(@PathVariable("averageTime") int averageTime) {
        return numbersService.getLevelByClosestAverageTime(averageTime);
    }
}
