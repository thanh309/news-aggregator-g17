package group17.news_aggregator.search_engine;

public class FileHandling {
    public static String removePuncts(String input) {
        return input.replaceAll("\\p{Punct}", "").toLowerCase();
    }

    @Deprecated
    public static String removeNonAscii(String text) {
        /*
         * Remove non - ASCII characters
         */
        return text.replaceAll("[^\\x00-\\x7F]+", "");
    }
}

