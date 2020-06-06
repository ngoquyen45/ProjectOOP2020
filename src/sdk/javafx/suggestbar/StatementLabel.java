package sdk.javafx.suggestbar;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import ui.MainController;

import java.io.IOException;
import java.util.Objects;

public class StatementLabel extends VBox {
    @FXML
    FlowPane listTagEnter;

    @FXML
    FlowPane listTagSuggest;

    private Label label;

    private VBox vBox;


    public StatementLabel(String statement) {
        super();
        label = new Label(statement);
        label.setWrapText(true);
//        label.setStyle("-fx-background-radius: 5px");
//        label.setStyle("-fx-padding: 3px 6px 3px 3px");
//        label.setStyle("-fx-fit-to-width: inherit");
        this.getStylesheets().add(getClass().getResource("statementlabel.css").toExternalForm());


//        SuggestBarController suggestBarController = fxmlLoader.getController();
//        suggestBarController.addListTagSuggest(statement);

        label.setOnMousePressed(event -> {
            // Mở rộng bên dưới
            StatementLabel statementLabel = null;
            if (!MainController.stack.empty()) {
                statementLabel = MainController.stack.pop();
                statementLabel.getLabel().setStyle("-fx-background-color: null");
                statementLabel.getChildren().remove(statementLabel.getvBox());
            }

            // Nếu click vào văn bản mới
            if (!this.equals(statementLabel)) {
                this.getLabel().setStyle("-fx-background-color: #2969c0");
                // new SuggestBar(((Label) event.getSource()).getText()).start();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("suggest_bar_layout.fxml"));
                try {
                    vBox = fxmlLoader.load();
                    SuggestBarController suggestBarController = fxmlLoader.getController();
                    suggestBarController.addListTagSuggest(label.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                this.getChildren().add(vBox);
                // lưu lại statement trước đó
                MainController.stack.push(this);
            }
        });

        this.getChildren().addAll(label);
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public VBox getvBox() {
        return vBox;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatementLabel that = (StatementLabel) o;
        return Objects.equals(label, that.label) &&
                Objects.equals(vBox, that.vBox);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, vBox);
    }
}
