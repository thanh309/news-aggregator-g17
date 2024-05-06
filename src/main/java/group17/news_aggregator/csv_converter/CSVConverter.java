package group17.news_aggregator.csv_converter;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import group17.news_aggregator.news.News;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CSVConverter {
    public void toCSV(List<? extends News> articles, String filePath, boolean overwrite) {

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath, !overwrite), StandardCharsets.UTF_8)) {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder<>(writer).build();
            beanToCsv.write(articles);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void toCSV(News article, String filePath, boolean overwrite) {

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath, !overwrite), StandardCharsets.UTF_8)) {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder<>(writer).build();
            beanToCsv.write(article);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


    public List<? extends News> fromCSV(String filePath) {

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8)) {
            CsvToBean<News> csvToBean = new CsvToBeanBuilder<News>(reader).withType(News.class).withSeparator(',').build();
            return csvToBean.parse();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;

    }

    public List<? extends News> fromCSV(String filePath, int i) {

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8)) {
            CsvToBean<News> csvToBean = new CsvToBeanBuilder<News>(reader).withType(News.class).withSeparator(',').build();
            return csvToBean.parse();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;

    }

}
