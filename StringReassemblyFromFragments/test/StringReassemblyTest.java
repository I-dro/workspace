import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    @Test
    public void testAddToSetAvoidingSubstrings() {
        Set<String> strSet = new Set1L<String>();
        strSet.add("hello");
        strSet.add("world");

        Set<String> expected = new Set1L<String>();
        expected.add("hello");
        expected.add("world");
        expected.add("greetings");

        StringReassembly.addToSetAvoidingSubstrings(strSet, "greetings");

        assertEquals(expected, strSet);
    }

    @Test
    public void testAddToSetAvoidingSubstrings2() {
        Set<String> strSet = new Set1L<String>();
        strSet.add("hello");
        strSet.add("world");
        strSet.add("greetings");

        Set<String> expected = new Set1L<String>();
        expected.add("hello");
        expected.add("world");
        expected.add("greetings");

        StringReassembly.addToSetAvoidingSubstrings(strSet, "greetings");

        assertEquals(expected, strSet);
    }

    @Test
    public void testCombination() {
        String str1 = "hello";
        String str2 = "loworld";
        int overlap = 2;

        String expected = "helloworld";
        String result = StringReassembly.combination(str1, str2, overlap);

        assertEquals(expected, result);
    }

    @Test
    public void testCombination2() {
        String str1 = "goodby";
        String str2 = "bye";
        int overlap = 2;

        String expected = "goodbye";
        String result = StringReassembly.combination(str1, str2, overlap);

        assertEquals(expected, result);
    }

    @Test
    public void testCombination3() {
        String str1 = "massage cha";
        String str2 = "chair";
        int overlap = 3;

        String expected = "massage chair";
        String result = StringReassembly.combination(str1, str2, overlap);

        assertEquals(expected, result);
    }

    @Test
    public void testPrintWithLineSeparators1() {
        SimpleWriter out = new SimpleWriter1L();
        String str = "i don't have money to spend ~i want money now ~please give me money \n";
        StringReassembly.printWithLineSeparators(str, out);

    }

}
