
import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Chris Davis
 *
 */
public final class Glossary {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Glossary() {
    }

    /**
     * Put a short phrase describing the static method myMethod here.
     */

    /**
     * This method would take a file and insert the term and definitions into a.
     *
     *
     * @param inFile
     * @return a map of the term and definitions
     */
    public static Map<String, String> inputGlossary(SimpleReader inFile) {
        Map<String, String> terms = new Map1L<String, String>();

        while (!inFile.atEOS()) {
            String key = inFile.nextLine().trim();
            if (!key.isEmpty()) {
                String value = inFile.nextLine().trim();
                if (!inFile.atEOS()) {
                    String next = inFile.nextLine().trim();
                    if (!next.isEmpty()) {
                        value += next;
                    }
                    terms.add(key, value);
                }

            }
        }

        return terms;
    }

    /**
     * Sort terms alphabetically.
     *
     * @param inFile
     * @return an array of the terms sorted in alphabetical order
     */
    public static String[] alphabetical(SimpleReader inFile) {
        Set<String> terms = new Set1L<String>();

        while (!inFile.atEOS()) {
            String key = inFile.nextLine().trim();
            if (!key.isEmpty()) {
                String trash = inFile.nextLine().trim();
                if (!inFile.atEOS()) {
                    String next = inFile.nextLine().trim();
                    if (!next.isEmpty()) {
                        trash += next;
                    }
                    terms.add(key);
                }

            }
        }

        String[] termsIn = new String[terms.size()];
        int i = 0;
        for (String word : terms) {
            termsIn[i] = word;
            ++i;
        }
        int size = termsIn.length;
        for (i = 0; i < size; ++i) {
            for (int j = i + 1; j < size; ++j) {
                if (termsIn[i].compareTo(termsIn[j]) > 0) {
                    String temp = termsIn[i];
                    termsIn[i] = termsIn[j];
                    termsIn[j] = temp;
                }
            }
        }

        return termsIn;
    }

    /**
     * complicated. this makes the term page while also making sure that all
     * terms that are part of the definition can be linked to it's respective
     * page might fail
     *
     * @param fName
     * @param terms
     * @param sortedTerms
     */
    public static void termHtmlFile(String fName, Map<String, String> terms,
            String[] sortedTerms) {
        for (String term : sortedTerms) {
            String definition = "";
            String[] sentence = terms.value(term).split(" ");
            for (String checking : sentence) {
                boolean presence = false;
                for (String checked : sortedTerms) {
                    if (checking.contains(checked)) {
                        definition += "<a href=\"" + checked + ".html\">"
                                + checked + " </a>";
                        presence = true;
                    }

                }
                if (!presence) {
                    definition += checking + " ";
                }
            }
            String fileName = term + ".html";
            String pageTitle = "Definition of " + term;
            String content = "<h2>" + term + "</h2>\n\n<p>" + definition
                    + "</p>";
            fileHtml(fileName, pageTitle, content);
        }

    }

    /**
     * creates the page that links to other pages in the glossary.
     *
     * @param folderName
     * @param sortedTerms
     */
    public static void indexPage(String folderName, String[] sortedTerms) {
        String file = folderName;
        String listed = "";
        String title = "Glossary";
        for (String term : sortedTerms) {
            String terms = "\n<a href=\"" + term + ".html\">" + term + "</a>";
            listed += "<li>" + terms + "</li>\n";
        }
        String content = "<h1>Glossary</h1>\n\n<ul>\n" + listed + "</ul>";
        fileHtml(file, title, content);
    }

    /**
     * Makes the file and names it.
     *
     * @param fileName
     * @param pageTitle
     * @param content
     */
    private static void fileHtml(String fileName, String pageTitle,
            String content) {

        SimpleWriter pen = new SimpleWriter1L(fileName);
        pen.print(
                "<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\">\n");
        pen.print("<title>" + pageTitle + "</title>\n</head>\n<body>\n");
        pen.print(content + "\n</body>\n</html>");

        pen.close();

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
         * get input file name
         */
        out.print("what is the name of the file: ");
        String inputFileName = in.nextLine();
        out.print("what is the name of the output folder: ");
        String outputFileName = in.nextLine();
        SimpleReader inFile = new SimpleReader1L(inputFileName);
        SimpleReader inFile1 = new SimpleReader1L(inputFileName);
        Map<String, String> terms = inputGlossary(inFile);
        String[] sortedTerms = alphabetical(inFile1);
        termHtmlFile(outputFileName, terms, sortedTerms);
        indexPage(outputFileName, sortedTerms);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
