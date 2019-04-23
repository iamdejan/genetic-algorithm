import java.util.*;

class Main {

    Random random = new Random();
    HashMap<Double, Double> globalResults = new HashMap<>();

    double function(double x) {
        return 3.0d + 14.0d * x - 5.0d * x * x;
    }

    HashMap<Double, Double> calculateSolutions(final Double[] solutions) {
        HashMap<Double, Double> results = new HashMap<>();
        for(int i = 0; i < solutions.length; i++) {
            results.put(solutions[i], function(solutions[i]));
        }

        return results;
    }

    Double mate(final Double[] solutions, int numberOfParents) {
        Double number = 0.0;

        for(int i = 0; i < numberOfParents; i++) {
            number += solutions[i];
        }

        number /= numberOfParents;

        return number;
    }

    void printOngoingSimulation(final Double[] solutions, final HashMap<Double, Double> results) {
        for(int i = 0; i < solutions.length; i++) System.out.printf("%.6f ", solutions[i]);
        System.out.println();

        for(int i = 0; i < solutions.length; i++) System.out.printf("%.6f ", results.get(solutions[i]));
        System.out.println();

        System.out.println();
    }

    Double mutate(Double newSolution) {
        final double mutationMinimumRange = -1.0d;
        final double mutationMaximumRange = +1.0d;

        newSolution += (mutationMinimumRange+ (mutationMaximumRange - mutationMinimumRange) * random.nextDouble());

        return newSolution;
    }

    HashMap<Double, Double> calculateResults(Double[] solutions) {
        HashMap<Double, Double> results = calculateSolutions(solutions);
        Arrays.sort(solutions, (a, b) -> {
            return Double.compare(results.get(b), results.get(a));
        });

        globalResults = results;

        return results;
    }

    Double[] generateRandomSolutions(final int numberOfSolutions) {
        Double[] solutions = new Double[numberOfSolutions];
        for(int i = 0; i < numberOfSolutions; i++) solutions[i] = random.nextDouble() * 10.3d;

        return solutions;
    }

    public Main() {
        /**
         * Genetic algorithm:
         * 1) Generate random solutions
         * 2) Measure, then sort based on minimum value
         * 3) Mate (using average function)
         * 4) Mutate the best solutions
         * 5) Repeat from number 2
         */

        int numberOfSolutions = 5;
        int generation = 1;
        int limitOfGenerations = 5500;

        Double[] solutions = generateRandomSolutions(numberOfSolutions);

        do {
            HashMap<Double, Double> results = calculateResults(solutions);

            printOngoingSimulation(solutions, results);

            Double newSolution = mate(solutions, 2);
            newSolution = mutate(newSolution);
            solutions[solutions.length - 1] = newSolution; // set the solution

            generation++;
        } while(!(generation >= limitOfGenerations || (globalResults.get(solutions[0]) - globalResults.get(solutions[3]) <= 1e-4)));

        System.out.println("Best x is: " + solutions[0]);
        System.out.println("f(x) = " + function(solutions[0]));
        System.out.println("Generation: " +generation);
    }

    public static void main(String[] args) {
        new Main();
    }
}