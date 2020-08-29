package solver;

public class LinearEquationResult {
    enum SolvingResult {
        ONE_SOLUTION,
        NO_SOLUTIONS,
        INFINITELY_MANY_SOLUTIONS};
    SolvingResult solvingResult;
    double[] result;

    LinearEquationResult(SolvingResult solvingResult, double[] result) {
        this.solvingResult = solvingResult;
        this.result = result;
    }
}
