package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    //カスタム データ フォーマット 引数はIDとして適当な文字列
    static final DataFormat MyFormat = new DataFormat("a4b65e12b5a78e6a");
    ListView<Member> view_1 = new ListView<>();
    ListView<Member> view_2 = new ListView<>();

    @Override
    public void start(Stage primaryStage) {

        ObservableList<Member> list_left = FXCollections.observableArrayList();
        ObservableList<Member> list_right = FXCollections.observableArrayList();
        for (int i = 0; i < 10; i++) {
            list_left.add(new Member("日本語 第"+i+"番目"));
            list_right.add(new Member("English No."+i));
        }

        view_1.getItems().addAll(list_left);
        view_1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        view_1.setOnDragDetected(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        dragDetected(event,view_1);
                    }
                }
        );
        view_1.setOnDragOver(
                new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        dragOver(event, view_1);
                    }
                }
        );
        view_1.setOnDragDropped(
                new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        dragDropped(event, view_1);
                    }
                }
        );
        view_1.setOnDragDone(
                new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        dragDone(event,view_1);
                    }
                }
        );

        view_2.getItems().addAll(list_right);
        view_2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        view_2.setOnDragDetected(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        dragDetected(event,view_2);
                    }
                }
        );
        view_2.setOnDragOver(
                new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        dragOver(event, view_2);
                    }
                }
        );
        view_2.setOnDragDropped(
                new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        dragDropped(event, view_2);
                    }
                }
        );
        view_2.setOnDragDone(
                new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        dragDone(event,view_2);
                    }
                }
        );

        HBox box = new HBox(10);
        box.getChildren().addAll(view_1, view_2);

        primaryStage.setTitle("タイトル");
        primaryStage.setScene(new Scene(box));
        primaryStage.show();
    }


    private void dragDetected(MouseEvent event, ListView<Member> self) {
        int selectedCount = self.getSelectionModel().getSelectedIndices().size();
        if (selectedCount == 0) {
            event.consume();
            return;
        }
        // ドラッグボード、初期化
        Dragboard db = self.startDragAndDrop(TransferMode.MOVE);

        ArrayList<Member> selectedMembers =   new ArrayList<>(self.getSelectionModel().getSelectedItems());
        ClipboardContent content = new ClipboardContent();
        content.put(MyFormat, selectedMembers);
        db.setContent(content);

        event.consume();
    }

    private void dragOver(DragEvent event, ListView<Member> self) {
        Dragboard db = event.getDragboard();
        //ドラッグ元が自身ではなく、かつ、ドラッグボードがMyFormatのデータである場合
        if (event.getGestureSource() != self &&  db.hasContent(MyFormat)) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    private void dragDropped(DragEvent event, ListView<Member> self) {
        boolean success = false;
        Dragboard dragboard = event.getDragboard();

        if(dragboard.hasContent(MyFormat)) {
            ArrayList<Member> list = (ArrayList<Member>)dragboard.getContent(MyFormat);
            self.getItems().addAll(list);
            //データ移動が成功したときのフラグ
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private void dragDone(DragEvent event, ListView<Member> self) {
        if (event.getTransferMode() == TransferMode.MOVE) {
            List<Member> selectedList = new ArrayList<>();
            for(Member member : self.getSelectionModel().getSelectedItems()) {
                selectedList.add(member);
            }
            // セレクト状態をクリア
            self.getSelectionModel().clearSelection();
            //ドラッグ元のセレクトした要素を削除
            self.getItems().removeAll(selectedList);
        }

        event.consume();
        }

    public static void main(String[] args) {
        Application.launch(args);
    }



}
