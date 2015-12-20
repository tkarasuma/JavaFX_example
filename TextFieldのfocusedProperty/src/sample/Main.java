package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField textField = new TextField();
        textField.setPrefColumnCount(24);
        TextField textField2 = new TextField();
        textField.setStyle("-fx-border-color:red;");
        textField.focusedProperty().addListener(
                new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
               // System.out.println(String.format("arg0.getValue(): %6s ,oldPropertyValue: %6s, newPropertyValue: %6s", arg0.getValue(), oldPropertyValue ,newPropertyValue));
                System.out.println(textField.focusedProperty().getValue());
                if (newPropertyValue){
                    textField2.setText("赤枠のテキストフィールドにフォーカスしました。");
                    textField.setText(textField.getText().replace(" km", ""));
                }else{
                    textField2.setText("赤枠のテキストフィールドからフォーカスが外れました。");
                    textField.setText(textField.getText().concat(" km"));
                }
            }
        });
        VBox root = new VBox();
        root.getChildren().addAll(textField,textField2);
        Scene scene = new Scene(root);

        primaryStage.setTitle("タイトル");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}