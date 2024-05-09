package javasearchengine.core.searchengine;

public class FileHandling {
    public static String removePuncts(String input) {
        return input.replaceAll("\\p{Punct}", "").toLowerCase();
    }

    public static void makeJson(String csvFile, String jsonFile) {

    }

    public static String removeNonAscii(String text) {
        /*
         * Remove non - ASCII characters
         */

        return text.replaceAll("[^\\x00-\\x7F]+", "");

    }

}

