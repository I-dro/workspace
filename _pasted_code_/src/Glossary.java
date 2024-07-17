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

    private Glossary() {
    }

    /**
     * Reads the input glossary file and creates a map of terms and definitions.
     *
     * @param inFile
     *            The input file reader
     * @return A map containing terms and their definitions
     */
    public static Map<String, String> inputGlossary(SimpleReader inFile) {
        Map<String, String> terms = new Map1L<>();

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
     * Generates HTML pages for each term based on its definition.
     *
     * @param terms
     *            The map containing terms and definitions
     * @param sortedTerms
     *            An array of terms sorted alphabetically
     * @param folderName
     *            The output folder to store generated HTML files
     */
    public static String[] alphabetical(SimpleReader inFile) {
        Set<String> terms = new Set1L<>();

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
     * Generates HTML pages for each term based on its definition.
     *
     * @param terms
     *            The map containing terms and definitions
     * @param sortedTerms
     *            An array of terms sorted alphabetically
     * @param folderName
     *            The output folder to store generated HTML files
     */
    public static void generateTermPages(Map<String, String> terms,
            String[] sortedTerms, String folderName) {
        for (String term : sortedTerms) {
            String definition = createDefinition(terms, sortedTerms, term);
            String fileName = term + ".html";
            String pageTitle = "Definition of " + term;
            String endContent = "</blockquote><hr><p>Return to ";
            endContent += "<a href=\"index.html\"> index </a>. </p>";
            String content = "<h2> <b> <i> <font color=\"red\">" + term
                    + "</font></i></b></h2>\n\n<blockquote>" + definition
                    + endContent;
            fileHtml(folderName, fileName, pageTitle, content);
        }
    }

    /**
     * Generates the definition for a given term by linking related terms.
     *
     * @param terms
     *            The map containing terms and definitions
     * @param sortedTerms
     *            An array of terms sorted alphabetically
     * @param term
     *            The term for which the definition is to be generated
     * @return The generated definition for the given term
     */
    private static String createDefinition(Map<String, String> terms,
            String[] sortedTerms, String term) {
        String definition = "";
        String[] sentence = terms.value(term).split(" ");
        for (String checking : sentence) {
            boolean presence = false;
            for (String checked : sortedTerms) {
                if (checking.contains(checked)) {
                    definition += "<a href=\"" + checked + ".html\">" + checked
                            + " </a>";
                    presence = true;
                }
            }
            if (!presence) {
                definition += checking + " ";
            }
        }
        return definition;
    }

    /**
     * Creates the HTML index page with links to all terms.
     *
     * @param folderName
     *            The output folder to store the index HTML file
     * @param sortedTerms
     *            An array of terms sorted alphabetically
     */
    public static void generateIndexPage(String folderName,
            String[] sortedTerms) {
        String listed = "";
        for (String term : sortedTerms) {
            String terms = "\n<a href=\"" + term + ".html\">" + term + "</a>";
            listed += "<li>" + terms + "</li>\n";
        }
        String content = "<h2>Glossary</h2><hr><h3>Index</h3>\n\n<ul>\n"
                + listed + "</ul>";
        fileHtml(folderName, "index.html", "Glossary", content);
    }

    /**
     * Writes the HTML content to a file.
     *
     * @param folderName
     *            The output folder for the HTML file
     * @param fileName
     *            The name of the HTML file
     * @param pageTitle
     *            The title of the HTML page
     * @param content
     *            The content to be written to the HTML file
     */
    private static void fileHtml(String folderName, String fileName,
            String pageTitle, String content) {
        String fullPath = folderName + "/" + fileName;
        SimpleWriter pen = new SimpleWriter1L(fullPath);
        pen.print(
                "<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\">\n");
        pen.print("<title>" + pageTitle + "</title>\n</head>\n<body>\n");
        pen.print(content + "\n</body>\n</html>");

        pen.close();
    }

    /**
     * Main method for executing glossary generation.
     *
     * @param args
     *            Command line arguments (not used here)
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of the input file: ");
        String inputFileName = in.nextLine();
        out.print("Enter the name of the output folder: ");
        String outputFolderName = in.nextLine();

        SimpleReader inFile = new SimpleReader1L(inputFileName);
        SimpleReader inFile1 = new SimpleReader1L(inputFileName);

        Map<String, String> terms = inputGlossary(inFile);
        String[] sortedTerms = alphabetical(inFile1);

        generateTermPages(terms, sortedTerms, outputFolderName);
        generateIndexPage(outputFolderName, sortedTerms);

        in.close();
        out.close();
    }
}
