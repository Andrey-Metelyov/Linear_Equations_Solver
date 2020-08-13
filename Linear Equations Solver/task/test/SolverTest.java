import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.TestCase;

import solver.Main;

import java.util.List;

import static java.lang.Math.abs;


public class SolverTest extends StageTest<String> {

    public SolverTest() {
        super(Main.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        List<TestCase<String>> tests = List.of(
            new TestCase<String>().setInput("5 3"),
            new TestCase<String>().setInput("12 67"),
            new TestCase<String>().setInput("12 12"),
            new TestCase<String>().setInput("-1 1"),
            new TestCase<String>().setInput("2 -3"),
            new TestCase<String>().setInput("2.34 12.23")
        );

        for (TestCase<String> test : tests) {
            test.setAttach(solve(test.getInput()));
        }

        return tests;
    }

    private String solve(String input) {
        String[] nums = input.split(" ");
        double a = Double.parseDouble(nums[0]);
        double b = Double.parseDouble(nums[1]);
        return String.valueOf(b / a);
    }

    @Override
    public CheckResult check(String reply, String clue) {
        try {
            double actual = Double.parseDouble(reply);
            double expected = Double.parseDouble(clue);
            return new CheckResult(abs(actual - expected) < 0.001);
        }
        catch (NumberFormatException ex) {
            return new CheckResult(false, "Can't parse numbers - check if you don't print any additional symbols except a space between the numbers");
        }
    }
}
