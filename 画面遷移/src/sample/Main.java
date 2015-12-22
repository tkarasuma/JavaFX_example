package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    public static Main singleton;
    private Parent root;
    private  String[] PAGE = new String[] {
        "sample.fxml",
        "sample2.fxml",
        "sample3.fxml"
    };
    private  int page_index =0;
    private  int page_num = 3;
    private Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        singleton = this;
        stage = primaryStage;
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setTitle("タイトル");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }

    public  void nextPage(){
        page_index++;
        page_index %= page_num;
        setPage();
    }

    public void prevPage(){
        page_index--;
        if (page_index<0){
            page_index += 3;
        }
        page_index %= page_num;
        setPage();
    }

    private void setPage(){
        try {
            root = FXMLLoader.load(getClass().getResource(PAGE[page_index]));
            stage.setTitle(PAGE[page_index]);
            stage.setScene(new Scene(root, 300, 275));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static Main getInstance(){
        return singleton;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
