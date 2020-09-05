package solver;

public class LinearEquationResult<T> {
    enum SolvingResult {
        ONE_SOLUTION,
        NO_SOLUTIONS,
        INFINITELY_MANY_SOLUTIONS};
    SolvingResult solvingResult;
    T[] result;

    LinearEquationResult(SolvingResult solvingResult, T[] result) {
        this.solvingResult = solvingResult;
        this.result = result;
    }
}
