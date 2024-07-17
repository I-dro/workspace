import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class ProgramSkeleton {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ProgramSkeleton() {
    }

    public static int digitCount(NaturalNumber n, int d) {
        if (!n.isZero()) {
            d++;
            int remainder = n.divideBy10();
            d = digitCount(n, d);
            n.multiplyBy10(remainder);
        }
        return d;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Put your main program code here
         */

        NaturalNumber n = new NaturalNumber2(246016);
        int d = 0;
        int count = digitCount(n, d);
    }

}
