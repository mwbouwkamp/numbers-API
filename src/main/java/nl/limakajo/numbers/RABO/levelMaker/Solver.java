package nl.limakajo.numbers.RABO.levelMaker;

import nl.limakajo.numbers.RABO.API.entity.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solver {

    private static List<State> queue;

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
//        System.out.println("LEVEL: " + level);
        queue = new ArrayList<>();
        State current = new State(
                Arrays.stream(level.convertNumbersToHand()).boxed().collect(Collectors.toList()),
                Arrays.stream(level.convertNumbersToHand()).boxed().map(String::valueOf).collect(Collectors.toList()));
        queue.add(current);
        while (queue.size() > 0) {
            State check = queue.remove(queue.size() - 1);
//            System.out.println("CHECK: " + check);
            if (goalReached(level, check)) {
                return check.toString();
            }
            else {
                List<Integer> mult = new LinkedList<>();
                List<Integer> min = new LinkedList<>();
                List<Integer> div = new LinkedList<>();
                for (int i = 0; i < check.getNumbers().size(); i++) {
                    for (int j = 0; j < check.getNumbers().size(); j++) {
                        if (i != j) {
                            if (i < j) { // By definition a + b = b + a, so we only need to perform this addition once
                                doOperation(check, i, j, '+');
                            }
                            if (i < j || check.getNumber(i) == 1 && check.getNumber(j) != 1) { // By definition a + b = b + a, so we only need to perform this multiplication once; also, when 1 is involved, multiplication is redundant
                                doOperation(check, i, j, '*');
                            }
                            if (check.getNumber(i) > check.getNumber(j)) { // Substraction only needs to be performed if a > b
                                doOperation(check, i, j, '-');
                            }
                            if (check.getNumber(i) % check.getNumber(j) == 0 && check.getNumber(i) != 1 && check.getNumber(j) != 1) { // Division only needs to be perormed if a % b = 0; also, when 1 is involved, division is redundant
                                doOperation(check, i, j, '/');
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private static void doOperation(State check, int i, int j, char operator) {
        int valOne = check.getNumber(i);
        String stringOne = check.getString(i);
        int valTwo = check.getNumber(j);
        String stringTwo = check.getString(j);
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
            List<Integer> remainingNumbers = getRemainingNumbers(check, i, j);

            newNumbers.add(result);
            newString.add("[" + stringOne + operator + stringTwo + "]");
            for (Integer number: remainingNumbers) {
                newNumbers.add(number);
                newString.add(String.valueOf(number));
            }
            queue.add(new State(newNumbers, newString));
//            System.out.println("ADDED: " + newString +  "....." + newNumbers.toString());
        }
    }


    private static boolean goalReached(Level level, State check) {
        return check.getNumbers().stream().mapToInt(Integer::intValue).sum() == level.getGoal();
    }

    private static List<Integer> getRemainingNumbers(State check, int i, int j) {
        return IntStream.range(0, check.getNumbers().size())
                .filter(n -> n != i && n != j)
                .map(check::getNumber)
                .boxed()
                .collect(Collectors.toList());
    }

}
