package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    final Label source = new Label( "ドラッグしてね。");
    final Label target = new Label("ここ、ここ !!");
    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox box = new VBox();

        source.setStyle("-fx-background-color:lightpink");


        Label label = new Label("大きなラベル");
        label.setPrefHeight(300);
        label.setPrefWidth(Double.MAX_VALUE);

        source.setPrefWidth(Double.MAX_VALUE);
        target.setPrefWidth(Double.MAX_VALUE);

        label.setStyle("-fx-background-color: aquamarine");

        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                Dragboard db = source.startDragAndDrop(TransferMode.ANY);
                System.out.println("DragDetected");

                ClipboardContent content = new ClipboardContent();
                content.putString(source.getText());
                db.setContent(content);

                event.consume();
            }
        });

        source.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                // D&Dでデータ移動が無事終了したら
                if (event.getTransferMode() == TransferMode.MOVE) {
                    System.out.println("DragDone");
                    source.setText("");
                }
                event.consume();
            }
        });

        target.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //データがターゲットからドラッグされたものではなく、なおかつ文字列のとき
                if (event.getGestureSource() != target && event.getDragboard().hasString())
                {
                    //コピーと移動を許可
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    System.out.println("DragOver");
                }
                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != target && event.getDragboard().hasString()) {
                    System.out.println("DragEntered");
                }
                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("DragExited");
                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    target.setText(db.getString());
                    System.out.println("DragDropped");
                    success = true;
                }
                //sourceに文字列が無事、移動されたことを通知
                event.setDropCompleted(success);
                event.consume();
            }
        });

        box.getChildren().addAll(source, label, target);
        primaryStage.setTitle("タイトル");
        primaryStage.setScene(new Scene(box));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
