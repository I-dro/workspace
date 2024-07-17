import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class Hailstone1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Hailstone1() {
    }

    /**
     * Generates and outputs the Hailstone series starting with the given
     * integer.
     *
     * @param n
     *            the starting integer
     * @param out
     *            the output stream
     */
    private static void generateSeries(int n, SimpleWriter out) {
        int quantity = 1;
        out.print(n + " ");
        int max = n;
        while (n > 1) {
            quantity++;
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = (n * 3) + 1;
            }
            max = (n > max) ? n : max;
            out.print(n + " ");
        }
        out.println();
        out.println("This series is " + quantity + " numbers Long");
        out.println("The max number is " + max);

    }

    /**
     * Repeatedly asks the user for a positive integer until the user enters
     * one. Returns the positive integer.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive integer entered by the user
     */
    private static int getPositiveInteger(SimpleReader in, SimpleWriter out) {
        int num = 0;
        while (num < 1) {
            out.print("Please enter a Number greater than 0: ");
            num = in.nextInteger();
        }
        return num;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Put your main program code here; it may call myMethod as shown
         */

        String agree = "y";
        while (agree.equals("y") || agree.equals("Y")) {
            int num = getPositiveInteger(in, out);
            generateSeries(num, out);
            out.println("Do you want to computer another series");
            agree = in.nextLine();
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
