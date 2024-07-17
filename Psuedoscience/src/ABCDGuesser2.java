import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 *
 * @author Chris Davis
 * @date 2/2/23
 *
 */

public final class ABCDGuesser2 {
    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser2() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        double num = 0.0;
        boolean req = false;
        String answer = "";
        while (!req) {
            if (num > 0.0) {
                req = true;
            } else {
                out.print("Please input a positive number that is 0: ");
                answer = in.nextLine();
                if (FormatChecker.canParseDouble(answer)) {
                    num = Double.parseDouble(answer);

                }

            }

        }

        return num;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        double num = 0.0;
        boolean req = false;
        String answer = "";
        while (!req) {
            if (num > 0.0) {
                if (num != 1) {
                    req = true;
                }
            } else {
                out.print(
                        "Please input a positive number that is not 0 or 1: ");
                answer = in.nextLine();
                if (FormatChecker.canParseDouble(answer)) {
                    num = Double.parseDouble(answer);

                }

            }

        }

        return num;
    }

    /**
     * @param constant
     *            used to find the number that is the goal
     * @param w
     * @param x
     * @param y
     * @param z
     *
     *
     */
    private static void checker(double constant, double w, double x, double y,
            double z) {
        int a = 0, b = 0, c = 0, d = 0;
        double aCount = 0, bCount = 0, cCount = 0, dCount = 0;
        final double[] array = { -5.0, -4, -3, -2, -1, -1.0 / 2, -1.0 / 3,
                -1.0 / 4, 0, 1.0 / 4, 1.0 / 3, 1.0 / 2, 1, 2, 3, 4, 5 };
        double margin = ((Math.pow(w, array[0])) * (Math.pow(x, array[0]))
                * (Math.pow(y, array[0])) * (Math.pow(z, array[0]))) - constant;
        final int limit = 16;
        for (d = 0; d <= limit; ++d) {
            for (c = 0; c <= limit; ++c) {
                for (b = 0; b <= limit; ++b) {
                    for (a = 0; a <= limit; ++a) {
                        double estimate = ((Math.pow(w, array[a]))
                                * (Math.pow(x, array[b]))
                                * (Math.pow(y, array[c]))
                                * (Math.pow(z, array[d]))) - constant;
                        if (Math.abs(estimate) < Math.abs(margin)) {
                            margin = estimate;
                            aCount = array[a];
                            bCount = array[b];
                            cCount = array[c];
                            dCount = array[d];
                        }

                    }

                }

            }

        }
        final double error = (margin / constant) * 100;
        System.out.println(constant + margin);
        System.out.println("The error is: " + error);
        System.out.println(
                "The exponent of the first number you chose was " + aCount);
        System.out.println(
                "The exponent of the second number you chose was " + bCount);
        System.out.println(
                "The exponent of the third number you chose was " + cCount);
        System.out.println(
                "The exponent of the fourth number you chose was " + dCount);

    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        double constant = getPositiveDouble(in, out);
        double num1 = getPositiveDoubleNotOne(in, out);
        double num2 = getPositiveDoubleNotOne(in, out);
        double num3 = getPositiveDoubleNotOne(in, out);
        double num4 = getPositiveDoubleNotOne(in, out);

        checker(constant, num1, num2, num3, num4);

        in.close();
        out.close();

    }

}
