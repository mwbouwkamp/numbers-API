package nl.limakajo.numbers.RABO.levelMaker;

import nl.limakajo.numbers.RABO.API.entity.Level;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solver {

    private static List<State> queue;

    /**
     * Private class to hold a state to consider
     */
    private static class State {
        List<Integer> numbers;
        List<String> stringRep;

        State(List<Integer> numbers, List<String> stringRep) {
            this.numbers = numbers;
            this.stringRep = stringRep;
        }

        List<Integer> getNumbers() {
            return numbers;
        }

        int getNumber(int i) {
            return numbers.get(i);
        }

        String getString(int i) {
            return stringRep.get(i);
        }

        @Override
        public String toString() {
            return stringRep.toString();
        }
    }

    private static final int MAXVALUE = 9999;

    /**
     * Returns solution to a level or null if no solution is available
     *
     * @param level     the Level to solve
     * @return          a String representation of the solution or null if no solution is available
     */
    static String solvableLevel(Level level) {
        queue = new ArrayList<>();
        State current = new State(
                Arrays.stream(level.convertNumbersToHand())
                        .boxed()
                        .collect(Collectors.toList()),
                Arrays.stream(level.convertNumbersToHand())
                        .boxed()
                        .map(String::valueOf)
                        .collect(Collectors.toList()));
        queue.add(current);
        while (queue.size() > 0) {
            State check = queue.remove(queue.size() - 1);
            if (goalReached(level, check)) {
                return check.toString();
            }
            else {
                for (int i = 0; i < check.getNumbers().size(); i++) {
                    for (int j = 0; j < check.getNumbers().size(); j++) {
                        if (i != j) {
                            if (i < j) { // By definition a + b = b + a, so we only need to perform this addition once
                                doOperation(check, i, j, '+').ifPresent(queue::add);
                            }
                            if (i < j || check.getNumber(i) == 1 && check.getNumber(j) != 1) { // By definition a + b = b + a, so we only need to perform this multiplication once; also, when 1 is involved, multiplication is redundant
                                doOperation(check, i, j, '*').ifPresent(queue::add);
                            }
                            if (check.getNumber(i) > check.getNumber(j)) { // Substraction only needs to be performed if a > b
                                doOperation(check, i, j, '-').ifPresent(queue::add);
                            }
                            if (check.getNumber(i) % check.getNumber(j) == 0 && check.getNumber(i) != 1 && check.getNumber(j) != 1) { // Division only needs to be perormed if a % b = 0; also, when 1 is involved, division is redundant
                                doOperation(check, i, j, '/').ifPresent(queue::add);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Performs an operation on two numbers in a state
     *
     * @param check         State to check
     * @param index1        first index (representing first tile for the operation)
     * @param index2        second index (representing second tile for the operation)
     * @param operator      the type of operation to perform
     * @return              optional with the state to add or empty
     */
    private static Optional<State> doOperation(State check, int index1, int index2, char operator) {
        int valOne = check.getNumber(index1);
        String stringOne = check.getString(index1);
        int valTwo = check.getNumber(index2);
        String stringTwo = check.getString(index2);
        int result;
        switch (operator) {
            case '+':
                result = valOne + valTwo;
                break;
            case '-':
                result = valOne - valTwo;
                break;
            case '*':
                result = valOne * valTwo;
                break;
            case '/':
                result = valOne / valTwo;
                break;
            default:
                throw new IllegalArgumentException();
        }
        if (result <= MAXVALUE && result >= 0) {
            List<Integer> newNumbers = new LinkedList<>();
            List<String> newString = new LinkedList<>();
            List<Integer> remainingNumbers = getRemainingNumbers(check, index1, index2);

            newNumbers.add(result);
            newString.add("[" + stringOne + operator + stringTwo + "]");
            for (Integer number: remainingNumbers) {
                newNumbers.add(number);
                newString.add(String.valueOf(number));
            }
            return Optional.ofNullable(new State(newNumbers, newString));
        }
        return Optional.empty();
    }


    /**
     * Checks if the goal, defined in Level, can be by adding the remaining numbers
     *
     * @param level     Level to complete
     * @param check     State to consider
     * @return          true if level can be solved by adding remaining numbers
     */
    private static boolean goalReached(Level level, State check) {
        return check.getNumbers().stream().mapToInt(Integer::intValue).sum() == level.getGoal();
    }

    /**
     * Get the remaining numbers when taking into account that two numbers (represented by index1 and index2) are removed
     *
     * @param check     current State
     * @param index1    index of first number that no longer needs to be taken into account
     * @param index2    index of second number that no longer needs to be taken into account
     * @return          remaining numbers
     */
    private static List<Integer> getRemainingNumbers(State check, int index1, int index2) {
        return IntStream.range(0, check.getNumbers().size())
                .filter(n -> n != index1 && n != index2)
                .map(check::getNumber)
                .boxed()
                .collect(Collectors.toList());
    }

}
