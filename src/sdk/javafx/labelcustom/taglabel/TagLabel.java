package sdk.javafx.labelcustom.taglabel;

import javafx.scene.control.Label;

public class TagLabel extends Label {
    private boolean isClicked = false;
    public TagLabel(String value){
        super(value);
        this.setWrapText(true);
        this.getStylesheets().add(getClass().getResource("taglabel.css").toExternalForm());
        this.setOnMousePressed(event -> {
            if(!isClicked) this.setStyle("-fx-background-color: #2969c0");
            else this.setStyle("-fx-background-color: #2b2b2b");
            isClicked = !isClicked;
        });
    }

    // Kiểm tra xem đã được click
    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
}