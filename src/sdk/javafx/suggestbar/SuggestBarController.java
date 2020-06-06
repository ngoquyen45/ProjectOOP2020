package sdk.javafx.suggestbar;

import core.suggest.NormalSuggester;
import core.utils.StringUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import sdk.javafx.labelcustom.taglabel.TagLabel;

import java.util.ArrayList;

public class SuggestBarController {
    @FXML
    public FlowPane listTagEnter;

    @FXML
    public FlowPane listTagSuggest;

    @FXML
    public TextField text;


    public void addListTagSuggest(String statement) {
        System.out.println("Statement = " + statement);
        // Load các đề nghị cho câu văn
        // Đề nghị theo
        NormalSuggester normalSuggester = new NormalSuggester();
        ArrayList<String> list1 = normalSuggester.suggest(statement);

        // Tạm thời chưa tính đế phần trong cây
        //TreeSuggest treeSuggest = new TreeSuggest();
        TagLabel[] tagLabels = new TagLabel[list1.size()];
        // Khởi tạo tag Label
        for(int i = 0; i < list1.size(); i++) {
            TagLabel tagLabel = new TagLabel(list1.get(i));
            listTagSuggest.getChildren().add(tagLabel);
            FlowPane.setMargin(tagLabel, new Insets( 6, 0, 0, 6));
        }
    }

    public void typeEnterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if(StringUtils.checkInputString(text.getText())) {
                // Add tới enterTag
                TagLabel tagLabel = new TagLabel(StringUtils.getString(text.getText()));
                tagLabel.setStyle("-fx-background-color: #2969c0");
                tagLabel.setClicked(true);
                listTagEnter.getChildren().add(tagLabel);

                FlowPane.setMargin(tagLabel, new Insets( 6, 0, 0, 6));
                // Clear text
                text.setText("");
            }
        }
    }
}
