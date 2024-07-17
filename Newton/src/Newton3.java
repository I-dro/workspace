import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Simple HelloWorld program (clear of Checkstyle and SpotBugs warnings).
 *
 * @author Chris N Davis
 */
public final class Newton3 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton3() {
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
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        //initialize the number that will be overwritten by user
        double fullNum = 0.0;
        //creates the String
        String key = "y";
        out.print("Do you wish to calculate a square root? ");
        key = in.nextLine();
        out.println("Enter an epsilin value: ");
        double epNum = in.nextDouble();
        while (key.charAt(0) == 'y') {
            out.println("Enter a number: ");
            fullNum = in.nextDouble();
            if (fullNum >= 0) {
                out.println("Square root: " + sqrt(fullNum, epNum));
            } else {
                out.println("Please Provide a positive number other than 0");
            }
            out.print("Do you wish to calculate a square root? ");
            key = in.nextLine();
        }
        out.print("Goodbye");

        in.close();
        out.close();
    }

}
