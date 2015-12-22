package sample;

import javafx.event.ActionEvent;

public class Controller3 {
    public void handlePrev(ActionEvent event) {
        Main.getInstance().prevPage();
    }
    public void handleNext(ActionEvent event) {
        Main.getInstance().nextPage();
    }
}
