import group17.news_aggregator.auto_complete.AutoComplete;

import java.util.*;


public class TestAutoComplete {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> words = List.of("hello", "dog", "hell", "cat", "a", "hel", "help", "helps", "helping", "heo", "heo");

        System.out.println("Enter query here: ");
        String query = sc.nextLine();

        AutoComplete t = new AutoComplete(words);

        t.createTrie();

        List<String> res = t.getSuggestion(query);

        if (res.isEmpty()) {
            System.out.println("No string with this prefix");
        } else {
            for (String w : res) {
                System.out.println(w);
            }
        }

        sc.close();
    }
}

