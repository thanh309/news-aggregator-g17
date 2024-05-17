package group17.news_aggregator.gui.utils.auto_complete_field;

import java.net.URL;
import java.util.ResourceBundle;

public class TagTextField extends AutoCompleteTextField {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fieldType = "tag";
        super.initialize(url, resourceBundle);
    }
}