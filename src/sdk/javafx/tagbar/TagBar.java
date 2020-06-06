package sdk.javafx.tagbar;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TagBar extends HBox {
    private final ObservableList<String> tags;
    private final TextField inputTextField;

    public TagBar() {
        getStyleClass().setAll("tag-bar");
        getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        tags = FXCollections.observableArrayList();
        inputTextField = new TextField();

        inputTextField.setOnAction(evt -> {
            // Chuyển đổi các kí tự thành chữ thường.
            String text = inputTextField.getText().toLowerCase();
            if (!text.isEmpty() && !tags.contains(text)) {
                tags.add(text);
                inputTextField.clear();
            }
        });

        inputTextField.prefHeightProperty().bind(this.heightProperty());
        HBox.setHgrow(inputTextField, Priority.ALWAYS);
        inputTextField.setBackground(null);

        inputTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                // Xóa hết kí tư
                if (inputTextField.getLength() == 0) {
                    // Xóa đến thẻ
                    if (!tags.isEmpty()) {
                        int lastIndex = tags.size() - 1;
                        tags.remove(tags.get(lastIndex));
                    }
                }
            }
        });

        tags.addListener((ListChangeListener.Change<? extends String> change) -> {
            while (change.next()) {
                if (change.wasPermutated()) {
                    ArrayList<Node> newSublist = new ArrayList<>(change.getTo() - change.getFrom());
                    for (int i = change.getFrom(), end = change.getTo(); i < end; i++) {
                        newSublist.add(null);
                    }
                    for (int i = change.getFrom(), end = change.getTo(); i < end; i++) {
                        newSublist.set(change.getPermutation(i), getChildren().get(i));
                    }
                    getChildren().subList(change.getFrom(), change.getTo()).clear();
                    getChildren().addAll(change.getFrom(), newSublist);
                } else {
                    if (change.wasRemoved()) {
                        getChildren().subList(change.getFrom(), change.getFrom() + change.getRemovedSize()).clear();
                    }
                    if (change.wasAdded()) {
                        getChildren().addAll(change.getFrom(), change.getAddedSubList().stream().map(Tag::new).collect(Collectors.toList()));
                    }
                }
            }
        });
        getChildren().add(inputTextField);
    }

    private class Tag extends HBox {

        public Tag(String tag) {
            getStyleClass().setAll("tag");
            Button removeButton = new Button("✖");
            removeButton.setOnAction((evt) -> tags.remove(tag));

            Label text = new Label(tag);
//            HBox.setMargin(text, new Insets(0, 0, 0, 10));
            getChildren().addAll(text, removeButton);
        }

    }
    public ObservableList<String> getListKeyword() {
        // Một vòng
        return tags;
    }
}
