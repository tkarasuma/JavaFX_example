package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;


public class Main extends Application {

    private static final ObservableList<String> pics_name_list = FXCollections.observableArrayList(
            "Add.png",
            "Apple.png",
            "Color.png",
            "Flags.png",
            "Health.png",
            "Help.png",
            "Hint.png"
    );
    private static final ObservableList<Image> image_list = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{

        for (int i = 0; i < pics_name_list.size(); i++) {
            image_list.add(new Image(getClass().getResourceAsStream(pics_name_list.get(i))));
        }

        VBox box = new VBox();
        box.setPadding(new Insets(10));
        ListView<String> pics_list_view = new ListView(pics_name_list);
        box.getChildren().add(pics_list_view);
        pics_list_view.setCellFactory(
                new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public MyCell call(ListView<String> param)
                    {
                        return new MyCell();
                    }
                }
        );


        primaryStage.setTitle("タイトル");
        primaryStage.setScene(new Scene(box));
        primaryStage.show();
    }

    private class MyCell extends ListCell<String> {
        private final ImageView imageView = new ImageView();

        public MyCell() {

            ListCell self = this;

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setAlignment(Pos.CENTER);

            setOnDragDetected(
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (getItem() == null) {
                                return;
                            }

                            ObservableList<String> list = getListView().getItems();

                            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
                            ClipboardContent content = new ClipboardContent();
                            content.putString(getItem());
                            dragboard.setDragView(  image_list.get(list.indexOf(getItem())));
                            dragboard.setContent(content);

                            event.consume();
                        }
                    }
            );

            setOnDragOver(
                    new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            if (event.getGestureSource() != self &&  event.getDragboard().hasString()) {
                                event.acceptTransferModes(TransferMode.MOVE);
                            }

                            event.consume();
                        }
                    }
            );

            setOnDragEntered(
                    new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            if (event.getGestureSource() != self &&   event.getDragboard().hasString()) {
                                setOpacity(0.3);
                            }
                        }
                    }
            );

            setOnDragExited(
                    new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            if (event.getGestureSource() != self && event.getDragboard().hasString()) {
                                setOpacity(1);
                            }
                        }
                    }
            );

            setOnDragDropped(
                    new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            if (getItem() == null) {
                                return;
                            }

                            Dragboard db = event.getDragboard();
                            boolean success = false;

                            if (db.hasString()) {
                                ObservableList<String> list = getListView().getItems();
                                int source_index = list.indexOf(db.getString());
                                int self_index = list.indexOf(getItem());

                                Image tmp_image = image_list.get(source_index);
                                image_list.set(source_index, image_list.get(self_index));
                                image_list.set(self_index, tmp_image);

                                list.set(source_index, getItem());
                                list.set(self_index, db.getString());
                                getListView().getItems().setAll(new ArrayList<>(list));

                                success = true;
                            }
                            event.setDropCompleted(success);

                            event.consume();
                        }
                    }
            );

            setOnDragDone(DragEvent::consume);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
            } else {
                imageView.setImage(
                        image_list.get(
                                getListView().getItems().indexOf(item)
                        )
                );
                setGraphic(imageView);
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
