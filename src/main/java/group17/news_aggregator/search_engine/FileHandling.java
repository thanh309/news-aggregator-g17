package group17.news_aggregator.search_engine;

public class FileHandling {
    public static String removePunctuations(String input) {
        return input.replaceAll("\\p{Punct}", "").toLowerCase();
    }

    /**
     * Removes all non-ASCII characters from a string.
     *
     * @param text The input string.
     * @return A new string with all non-ASCII characters removed.
     */
    @Deprecated
    public static String removeNonAscii(String text) {
        return text.replaceAll("[^\\x00-\\x7F]+", "");
    }
}