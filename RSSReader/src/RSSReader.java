import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML RSS (version 2.0) feed from a given URL into the
 * corresponding HTML output file.
 *
 * @author Chris Davis
 *
 */
public final class RSSReader {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSReader() {
    }

    /**
     * Outputs the "opening" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * <html> <head> <title>the channel tag title as the page title</title>
     * </head> <body>
     * <h1>the page title inside a link to the <channel> link</h1>
     * <p>
     * the channel description
     * </p>
     * <table border="1">
     * <tr>
     * <th>Date</th>
     * <th>Source</th>
     * <th>News</th>
     * </tr>
     *
     * @param channel
     *            the channel element XMLTree
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the root of channel is a <channel> tag] and out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    private static void outputHeader(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
        assert channel.isTag() && channel.label().equals("channel") : ""
                + "Violation of: the label root of channel is a <channel> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        String cDescription = "";
        String cTitle = "";
        String channelLink = channel.child(getChildElement(channel, "link"))
                .child(0).label();

        if (getChildElement(channel, "title") > -1) {
            if (channel.child(getChildElement(channel, "title"))
                    .numberOfChildren() > 0) {
                cTitle = channel.child(getChildElement(channel, "title"))
                        .child(0).label();
            } else {
                cTitle = "Empty Title";
            }
        } else {
            cTitle = "Empty Title";
        }
        if (getChildElement(channel, "description") > -1) {
            if (channel.child(getChildElement(channel, "description"))
                    .numberOfChildren() > 0) {
                cDescription = channel
                        .child(getChildElement(channel, "description")).child(0)
                        .label();
            } else {
                cDescription = "No Description";
            }
        } else {
            cDescription = "No Description";
        }

        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + cTitle + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println(
                "<h1><a href=\"" + channelLink + "\">" + cTitle + "</a></h1>");
        out.println("<p>" + cDescription + "</p>");
        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<th>Date</th>");
        out.println("<th>Source</th>");
        out.println("<th>News</th>");
        out.println("</tr>");

    }

    /**
     * Outputs the "closing" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * </table>
     * </body> </html>
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     *
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of type tag of the {@code XMLTree}
     *         or -1 if not found
     * @requires [the label of the root of xml is a tag]
     * @ensures <pre>
     * getChildElement =
     *  [the index of the first child of type tag of the {@code XMLTree} or
     *   -1 if not found]
     * </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";

        int i = xml.numberOfChildren();
        int index = -1;
        int count = 0;

        while (count < i && index < 0) {
            if (xml.child(count).label().equals(tag)) {
                index = count;
            }
            ++count;
        }

        return index;
    }

    /**
     * Processes one news item and outputs one table row. The row contains three
     * elements: the publication date, the source, and the title (or
     * description) of the item.
     *
     * @param item
     *            the news item
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the label of the root of item is an <item> tag] and
     *           out.is_open
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML table row with publication date, source, and title of news item]
     * </pre>
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item") : ""
                + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        String pubDate = "No Date Available";
        if (getChildElement(item, "pubDate") >= -1) {
            pubDate = item.child(getChildElement(item, "pubDate")).child(0)
                    .label();
        }
        out.println("<tr>");
        out.println("<td>" + pubDate + "</td>");
        String source = "No Source Available";
        String sourceURL = "";
        if (getChildElement(item, "source") >= -1) {
            XMLTree sourceElement = item.child(getChildElement(item, "source"));
            source = sourceElement.child(0).label();
            sourceURL = sourceElement.attributeValue("url");
        }
        if (!sourceURL.isEmpty()) {
            source = "<a href=\"" + sourceURL + "\">" + source + "</a>";
        }
        out.println("<td>" + source + "</td>");

        String titleOrDescription = "No Title Available";
        if (getChildElement(item, "title") >= -1) {
            titleOrDescription = item.child(getChildElement(item, "title"))
                    .child(0).label();
        } else if (getChildElement(item, "description") >= -1) {
            titleOrDescription = item
                    .child(getChildElement(item, "description")).label();
        }

        String itemLink = "";
        if (getChildElement(item, "link") >= -1) {
            itemLink = item.child(getChildElement(item, "link")).child(0)
                    .label();
            titleOrDescription = "<a href=\"" + itemLink + "\">"
                    + titleOrDescription + "</a>";
        }
        out.println("<td>" + titleOrDescription + "</td>");
        out.println("</tr>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        // Create a SimpleReader for user input
        SimpleReader userInput = new SimpleReader1L();
        // Create a SimpleWriter for program output
        SimpleWriter programOutput = new SimpleWriter1L();

        // Prompt the user for the URL of an RSS 2.0 feed
        programOutput.println("What is the URL of an RSS 2.0 feed");
        String rssUrl = userInput.nextLine();
        // Prompt the user for the name of the output file
        programOutput.println("What is the name of the output file");
        String outputFileName = userInput.nextLine();

        // Create an XMLTree object from the provided URL
        XMLTree rssStructure = new XMLTree1(rssUrl);
        // Specify the required version for RSS feed
        String requiredVersion = "2.0";
        // Retrieve the version attribute from the XML structure
        String rssVersion = rssStructure.attributeValue("version");

        // Check if the RSS version is supported
        if (!rssVersion.equals(requiredVersion)) {
            // Print an error message if the version is not supported
            programOutput
                    .println("Error: Unsupported RSS version - " + rssVersion);
        } else {
            // Create a SimpleWriter for the HTML output file
            SimpleWriter htmlOutput = new SimpleWriter1L(outputFileName);

            // Retrieve the <channel> element from the XML structure
            XMLTree channelElement = rssStructure.child(0);
            // Output the HTML header based on the <channel> information
            outputHeader(channelElement, htmlOutput);

            // Process each <item> element within the <channel>
            for (int i = 0; i < channelElement.numberOfChildren(); i++) {
                // Check if the current child is an <item> element
                if (channelElement.child(i).label().equals("item")) {
                    // Process the <item> element and output the corresponding HTML row
                    processItem(channelElement.child(i), htmlOutput);
                }
            }

            // Output the HTML footer
            outputFooter(htmlOutput);
            // Close the SimpleWriter for the HTML output file
            htmlOutput.close();
        }

        // Close the SimpleReader for user input
        userInput.close();
        // Close the SimpleWriter for program output
        programOutput.close();
    }

}