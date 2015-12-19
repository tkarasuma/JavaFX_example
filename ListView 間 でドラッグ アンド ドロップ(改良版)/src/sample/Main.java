package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

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
      //  view_1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        view_1.setCellFactory(
                new Callback<ListView<Member>, ListCell<Member>>() {
                    @Override
                    public ListCell<Member> call(ListView<Member> param) {
                        ListCell<Member> cell = new ListCell<Member>(){
                            @Override
                            protected void updateItem(Member item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null){
                                    setText(item.getName());
                                }else{
                                    setText("");
                                }
                            }
                        };

                        cell.setOnDragDetected(
                                new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        myDragDetected(event, cell);
                                    }
                                }
                        );
                        cell.setOnDragOver(
                                new EventHandler<DragEvent>() {
                                    @Override
                                    public void handle(DragEvent event) {
                                        myDragOver(event, cell);
                                    }
                                }
                        );
                        cell.setOnDragDropped(
                                new EventHandler<DragEvent>() {
                                    @Override
                                    public void handle(DragEvent event) {
                                        //event.setDropCompletedをmyDragDrop内で使うとエラーが出る。
                                        boolean success = myDragDrop(event, cell);
                                        event.setDropCompleted(success);
                                        event.consume();
                                    }
                                }
                        );
                        cell.setOnDragDone(
                                new EventHandler<DragEvent>() {
                                    @Override
                                    public void handle(DragEvent event) {
                                        myDragDone(event, cell);
                                    }
                                }
                        );
                        return  cell;
                    }
                }
        );

        view_2.getItems().addAll(list_right);
        //view_2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        view_2.setCellFactory(
                new Callback<ListView<Member>, ListCell<Member>>() {
                    @Override
                    public ListCell<Member> call(ListView<Member> param) {
                        ListCell<Member> cell = new ListCell<Member>(){
                            @Override
                            protected void updateItem(Member item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty || item == null) {
                                    //空になったので表示する内容はなし
                                    setGraphic(null);
                                    setText(null);
                                    return;
                                }
                                setText(item.getName());
                            }
                        };

                        cell.setOnDragDetected(
                                new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        myDragDetected(event, cell);
                                    }
                                }
                        );
                        cell.setOnDragOver(
                                new EventHandler<DragEvent>() {
                                    @Override
                                    public void handle(DragEvent event) {
                                        myDragOver(event, cell);
                                    }
                                }
                        );
                        cell.setOnDragDropped(
                                new EventHandler<DragEvent>() {
                                    @Override
                                    public void handle(DragEvent event) {
                                        //event.setDropCompletedをmyDragDrop内で使うとエラーが出る。
                                        boolean success = myDragDrop(event, cell);
                                        event.setDropCompleted(success);
                                        event.consume();
                                    }
                                }
                        );
                        cell.setOnDragDone(
                                new EventHandler<DragEvent>() {
                                    @Override
                                    public void handle(DragEvent event) {
                                        myDragDone(event, cell);
                                    }
                                }
                        );
                        return  cell;
                    }
                }
        );

        HBox box = new HBox(10);
        box.getChildren().addAll(view_1, view_2);

        primaryStage.setTitle("タイトル");
        primaryStage.setScene(new Scene(box));
        primaryStage.show();
    }


    private void myDragDetected(MouseEvent event, ListCell<Member> self) {
        // ドラッグボード、初期化
        Dragboard db = self.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        content.put(MyFormat, self.getItem());
        db.setContent(content);

        event.consume();
    }

    private void myDragOver(DragEvent event, ListCell<Member> self) {
        Dragboard db = event.getDragboard();
        //ドラッグ元が自身ではなく、かつ、ドラッグボードがMyFormatのデータである場合
        if (event.getGestureSource() != self &&  db.hasContent(MyFormat)) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    private  boolean myDragDrop(DragEvent event, ListCell<Member> self){
        boolean success = false;
        Dragboard dragboard = event.getDragboard();
        System.out.println("ターゲットのセル" + self.getItem());
        ObservableList<Member> list = self.getListView().getItems();
        int target_index = list.indexOf(self.getItem());
        boolean flag = ((ListCell<Member>)event.getGestureSource()).getListView()== self.getListView();
        if (!flag && dragboard.hasContent(MyFormat)) {
            Member item = (Member) dragboard.getContent(MyFormat);
            self.getListView().getItems().add(target_index, item);
            //データ移動が成功したときのフラグ
            success = true;
        }
        return  success;
    }



    private void myDragDone(DragEvent event, ListCell<Member> self) {
        if (event.getTransferMode() == TransferMode.MOVE) {
            ObservableList<Member> list = self.getListView().getItems();
            int source_index = list.indexOf(self.getItem());
            System.out.println(list);
            // セレクト状態をクリア
            self.getListView().getSelectionModel().clearSelection();
            //ドラッグ元のセレクトした要素を削除
            System.out.println("dragDoneのソース"+source_index);
           self.getListView().getItems().remove(source_index);

            System.out.println( self.getListView().getItems());
        }

        event.consume();
        }

    public static void main(String[] args) {
        Application.launch(args);
    }



}
