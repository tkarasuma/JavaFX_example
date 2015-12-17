package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.lang.reflect.Field;

public class Main extends Application {
    ListView<String> view = new ListView<>();
    ObservableList<String> data = FXCollections.observableArrayList();
    @Override
    public void start(Stage primaryStage) throws Exception{

        VBox box = new VBox();

        box.getChildren().addAll(view);
        VBox.setVgrow(view, Priority.ALWAYS);

        for (Field f :Color.class.getFields()){
            if (f.getName().equals("TRANSPARENT")){
                continue;
            }
            data.add(f.getName());
        }

        view.setItems(data);

        view.setCellFactory
                (new Callback<ListView<String>, ListCell<String>>() {
                     @Override
                     public ListCell<String> call(ListView<String> list) {
                         return new MyCell();
                     }
                 }
                );
        Scene scene = new Scene(box,400,600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("javafx.scene.paint.Color");
        primaryStage.show();

    }

    static class MyCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            Rectangle rect = new Rectangle(30, 20);
            if (item != null) {
                setText("   "+item.toString());
                rect.setFill(Color.web(item));
                setGraphic(rect);
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
