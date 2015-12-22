package sample;

import javafx.event.ActionEvent;

public class Controller {
    public void handlePrev(ActionEvent event) {
        Main.getInstance().prevPage();
    }
    public void handleNext(ActionEvent event) {
        Main.getInstance().nextPage();
    }
}
