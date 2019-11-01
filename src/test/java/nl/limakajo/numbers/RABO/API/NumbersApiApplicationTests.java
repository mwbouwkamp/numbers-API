package nl.limakajo.numbers.RABO.API;

import nl.limakajo.numbers.RABO.API.entity.Level;
import nl.limakajo.numbers.RABO.API.service.NumbersServiceJPA;
import nl.limakajo.numbers.RABO.API.rest.LevelRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
/**
 * Sample data (see resources/data.sql)
 *     '001002003004005006007', 20000, 1
 *     '002004006008010012014', 40000, 2
 *     '003006009012015018021', 60000, 3
 */
class NumbersApiApplicationTests {

	@Autowired
	NumbersServiceJPA numbersServiceJPA;

	@Test
	public void testGetAllLevels() {
		LevelRestController levelRestController = new LevelRestController(numbersServiceJPA);
		List<Level> levels = levelRestController.getLevels();
		assertEquals(3, levels.size());
	}

	@Test
	public void testGetSingleLevel() {
		LevelRestController levelRestController = new LevelRestController(numbersServiceJPA);
		String numbers = "002004006008010012014";
		Level retrievedLevel = levelRestController.getLevelByNumbers(numbers);
		assertEquals(numbers, retrievedLevel.getNumbers());
		assertEquals(40000, retrievedLevel.getAverageTime());
		assertEquals(2, retrievedLevel.getTimesPlayed());
	}

	@Test
	public void testDeleteLevel() {
		LevelRestController levelRestController = new LevelRestController(numbersServiceJPA);
		Level levelToDelete = new Level("002004006008010012014", 40000, 2);
		levelRestController.deleteByNumbers(levelToDelete.getNumbers());
		List<Level> levels = levelRestController.getLevels();
		assertEquals(2, levels.size());
	}

	@Test
	public void testSaveLevel() {
		LevelRestController levelRestController = new LevelRestController(numbersServiceJPA);
		String numbers = "010020030040050060070";
		Level levelToSave = new Level(numbers, 100000, 10);
		levelRestController.saveLevel(levelToSave);
		List<Level> levels = levelRestController.getLevels();
		assertEquals(4, levels.size());
		Level savedLevel = levelRestController.getLevelByNumbers(numbers);
		assertEquals(levelToSave, savedLevel);
	}

	@Test
	public void testUpdateLevel() {
		LevelRestController levelRestController = new LevelRestController(numbersServiceJPA);
		String numbers = "002004006008010012014";
		Level levelToUpdate = new Level(numbers, 111, 11);
		levelRestController.updateLevel(levelToUpdate);
		Level updatedLevel = levelRestController.getLevelByNumbers(numbers);
		assertEquals(numbers, updatedLevel.getNumbers());
		assertEquals(111, updatedLevel.getAverageTime());
		assertEquals(11, updatedLevel.getTimesPlayed());
	}
}
