package group17.news_aggregator.csv_converter;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import group17.news_aggregator.news.Article;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class CSVConverter {
    public void toCSV(List<? extends Article> articles, String filePath, boolean overwrite) {

        try (FileWriter writer = new FileWriter(filePath, !overwrite)) {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder<>(writer).build();
            beanToCsv.write(articles);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void toCSV(Article article, String filePath, boolean overwrite) {

        try (FileWriter writer = new FileWriter(filePath, !overwrite)) {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder<>(writer).build();
            beanToCsv.write(article);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }



    public List<Article> fromCSV(String filePath) {

        try (FileReader reader = new FileReader(filePath)) {
            CsvToBean<Article> csvToBean = new CsvToBeanBuilder<Article>(reader).withSeparator(',').build();
            return csvToBean.parse();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;

    }
}
