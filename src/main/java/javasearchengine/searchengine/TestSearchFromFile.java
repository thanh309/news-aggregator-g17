package javasearchengine.searchengine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class TestSearchFromFile {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(System.in);
        String csvFile = sc.nextLine();

        System.out.println("Enter the query here: ");
        String query = sc.nextLine();

        List<String> res = SearchFromFile.searchFromFile(csvFile, query);
        for (String title : res) {
            System.out.println(title);
        }
        sc.close();
    }
}
