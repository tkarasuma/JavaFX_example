package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private final TableView<Prefecture> table = new TableView<>();
    private final ObservableList<Prefecture> data =  FXCollections.observableArrayList(
            new Prefecture("北海道", "札幌市"),
            new Prefecture("宮城県", "仙台市"),
            new Prefecture("大阪府", "大阪市"),
            new Prefecture("兵庫県","神戸市"),
            new Prefecture("島根県","松江市")
    );

    @Override
    public void start(Stage stage) {

        StackPane pane = new StackPane();

        TableColumn<Prefecture, String> prefectureNameCol =   new TableColumn<>("都道府県");
        prefectureNameCol.setCellValueFactory(new PropertyValueFactory<>("prefectureName"));

        prefectureNameCol.setCellFactory(TextFieldTableCell.<Prefecture>forTableColumn());
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
        capitalCityNameCol.setCellFactory(TextFieldTableCell.<Prefecture>forTableColumn());
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

    public static void main(String[] args) {
        launch(args);
    }
}