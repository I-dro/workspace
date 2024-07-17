import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Simple program for estimating the square root of a positive number within a
 * specified relative error using the Newton-Raphson method.
 *
 * The program prompts the user to enter an epsilon value and then repeatedly
 * prompts for a positive number until a negative number is entered. For each
 * positive number entered, it calculates and prints the estimate of its square
 * root.
 *
 * This program is clear of Checkstyle and SpotBugs warnings.
 *
 * @author Chris N Davis
 */
public final class Newton4 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton4() {
        // no code needed here
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @param margin
     *            epsilon number
     * @return estimate of square root
     */
    private static double sqrt(double x, double margin) {

        double root = x;
        if (x > 0) {
            double intermediate = 0;
            double error = Math.abs(root - (x / root));

            // Iterate until the relative error is within the specified margin
            while (error > (margin * root)) {
                intermediate = x / root;
                root = (intermediate + root) / 2;

                error = Math.abs(root - (x / root));
            }
        }

        return root;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        // Create input and output objects for reading and writing
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        double fullNum = 0.0;

        // Prompt user to enter epsilon value
        out.println("Enter an epsilon value: ");
        double epNum = in.nextDouble();

        // Prompt user to enter a number
        out.println("Enter a number: ");
        fullNum = in.nextDouble();

        // Continue processing until a negative number is entered
        while (fullNum >= 0) {
            if (fullNum >= 0) {
                // Print the square root estimate using the specified epsilon
                out.println("Square root: " + sqrt(fullNum, epNum));
            }
            // Prompt user to enter another number
            out.print("Enter a number: ");
            fullNum = in.nextDouble();
        }

        // Say goodbye when the loop ends
        out.print("Goodbye");

        // Close input and output resources
        in.close();
        out.close();
    }

}
