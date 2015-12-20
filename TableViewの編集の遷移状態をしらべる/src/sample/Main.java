package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {

    private final TableView<Prefecture> table = new TableView<>();
    private final ObservableList<Prefecture> data =  FXCollections.observableArrayList(
            new Prefecture("北海道", "札幌市"),
            new Prefecture("宮城県", "仙台市"),
            new Prefecture("大阪府", "大阪市"),
            new Prefecture("兵庫県","神戸市"),
            new Prefecture("島根県","松江市")
    );
    private  Callback<TableColumn<Prefecture, String>, TableCell<Prefecture, String>> cellFactory =
            new Callback<TableColumn<Prefecture, String>, TableCell<Prefecture, String>>() {
                @Override
                public TableCell<Prefecture, String> call(TableColumn<Prefecture, String> p) {
                    return new MyCell();
                }
            };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {



        StackPane pane = new StackPane();

        TableColumn<Prefecture, String> prefectureNameCol =   new TableColumn<>("都道府県");
        prefectureNameCol.setCellValueFactory(new PropertyValueFactory<>("prefectureName"));

        prefectureNameCol.setCellFactory(cellFactory);
        prefectureNameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Prefecture, String>>() {
                    @Override
                    public void handle(CellEditEvent<Prefecture, String> event) {
                        Prefecture item = event.getTableView().getItems().get(event.getTablePosition().getRow());
                        item.setPrefectureName(event.getNewValue());
                    }
                }
        );

        TableColumn<Prefecture, String> capitalCityNameCol = new TableColumn<>("県庁所在地");
        capitalCityNameCol.setCellValueFactory(new PropertyValueFactory<>("capitalCityName"));
        capitalCityNameCol.setCellFactory(cellFactory);
        capitalCityNameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Prefecture, String>>() {
                    @Override
                    public void handle(CellEditEvent<Prefecture, String> event) {
                        Prefecture item = event.getTableView().getItems().get(event.getTablePosition().getRow());
                        item.setCapitalCityName(event.getNewValue());
                    }
                }
        );
        table.setItems(data);
        table.getColumns().addAll(prefectureNameCol, capitalCityNameCol);
        table.setEditable(true);

        pane.getChildren().addAll( table);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public static class MyCell extends TableCell<Prefecture, String> {

        private TextField textField;

        public MyCell() {
        }

        @Override
        public void startEdit() {
            System.out.println(p32("startEdit"));
            if (!isEmpty()) {
                super.startEdit();
                //TextFieldインスタンスを使い捨て
                textField = new TextField(getString());
                System.out.println(p32("TextField - HashCode") + textField.hashCode());
                textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
                //これも使い捨て
                ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        System.out.println(p32("ChangeListener newValue")+newValue);
                        if (!newValue){
                            commitEdit(textField.getText());
                        }
                    }
                };
                System.out.println(p32("ChangeListener - HashCode")+listener.hashCode());
                textField.focusedProperty().addListener(listener);
                setText(null);
                setGraphic(textField);
                textField.selectAll();
                System.out.println(p32("startEdit exit"));
            }
        }

        @Override
        public void cancelEdit() {
            System.out.println(p32("cancelEdit"));
            super.cancelEdit();
            setText(getItem());
            setGraphic(null);
            System.out.println(p32("cancelEdit exit"));
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    public static class Prefecture {
        private final SimpleStringProperty prefectureName;
        private final SimpleStringProperty capitalCityName;

        private Prefecture(String pName, String cName) {
            this.prefectureName = new SimpleStringProperty(pName);
            this.capitalCityName = new SimpleStringProperty(cName);
        }

        public String getPrefectureName() {
            return prefectureName.get();
        }

        public void setPrefectureName(String pName) {
            prefectureName.set(pName);
        }

        public String getCapitalCityName() {
            return capitalCityName.get();
        }

        public void setCapitalCityName(String cName) {
            capitalCityName.set(cName);
        }
    }
    public static String p32(String string){
        return  String.format("%32s", string)+" : ";
    }

}
