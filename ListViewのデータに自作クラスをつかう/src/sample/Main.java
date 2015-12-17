package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();

        List<Language> data = new ArrayList<>();
        data.add(new Language("Java", 19.565));
        data.add(new Language("C", 15.621));
        data.add(new Language("C++", 6.782));
        data.add(new Language("C#", 4.909));
        data.add(new Language("Python", 3.664));
        data.add(new Language("PHP", 2.530));
        data.add(new Language("JavaScript", 2.342));
        data.add(new Language("VB.NET", 2.062));
        data.add(new Language("Perl", 1.899));
        data.add(new Language("Objective-C", 1.821));
        data.add(new Language("Assembly", 1.806));
        data.add(new Language("Ruby", 1.783));
        data.add(new Language("Other", 10.065));

        ListView<Language> view = new ListView<>();
        ObservableList<Language> observableList = FXCollections.observableList(data);
        view.setItems(observableList);

        view.setCellFactory(
                //型パラメータは <callメソッドの引数 , callメソッドの戻り値>
                new Callback<ListView<Language>, ListCell<Language>>() {
                    @Override
                    public ListCell<Language> call(ListView<Language> view) {
                        ListCell<Language> cell = new ListCell<Language>() {
                            @Override
                            protected void updateItem(Language lang, boolean empty) {
                                super.updateItem(lang, empty);
                                if (lang != null) {
                                    setText(String.format("%-12s", lang.getName()) + " = " + lang.getPopularity());
                                }
                            }
                        };
                        return cell;
                    }
                });


        root.getChildren().add(view);
        primaryStage.setTitle("タイトル");
        primaryStage.setScene(new Scene(root, 300, 500));
        primaryStage.show();
    }

    class Language {
        String name;
        double popularity;

        public Language(String name, double popularity) {
            this.name = name;
            this.popularity = popularity;
        }

        public String getName() {
            return name;
        }

        public double getPopularity() {
            return popularity;
        }
    }
}