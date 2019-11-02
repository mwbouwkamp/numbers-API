package nl.limakajo.numbers.RABO.API.entity;

import javax.persistence.*;

@Entity
@Table(name = "numbers_levels")
public class Level {

    public static final int[] NUMBERS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 25, 50, 75, 100};
    public static final int NUMTILES = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "numbers")
    private String numbers;

    @Column(name = "averagetime")
    private int averageTime;

    @Column(name = "timesplayed")
    private int timesPlayed;

    public Level() {}

    public Level(String numbers, int averageTime, int timesPlayed) {
        this.numbers = numbers;
        this.averageTime = averageTime;
        this.timesPlayed = timesPlayed;
    }

    //Default getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    @Override
    public String toString() {
        return "Level{" +
                "numbers='" + numbers + '\'' +
                ", averageTime=" + averageTime +
                ", timesPlayed=" + timesPlayed +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Level) {
            Level level = (Level) obj;
            if (this.numbers == level.getNumbers()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the hand as an int[] based on the numbers
     *
     * @return      hand
     */
    public int[] convertNumbersToHand() {
        int[] hand = new int[NUMTILES];
        for (int i = 0; i < NUMTILES; i++) {
            hand[i] = Integer.parseInt(numbers.substring(i * 3, (i + 1) * 3));
        }
        return hand;
    }

    /**
     * Returns the goal based on the numbers
     * @return
     */
    public int extractGoal() {
        return Integer.parseInt(numbers.substring(3 * NUMTILES, 3 * NUMTILES + 3));
    }
}
