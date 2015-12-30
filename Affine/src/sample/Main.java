package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Affine;
import javafx.scene.transform.MatrixType;
import javafx.stage.Stage;


public class Main extends Application {

    Group root;
    final double length = 30;
    final double thickness = 0.5;
    double range = 20;
    double dx =0, dy=0, dz=0, tx=0, ty=0, tz=0;
    final static  double pi_x2 = Math.PI*2;
    final static double deg_per_rad = 360/pi_x2;

    private final double[] idt={1,0,0,0, 0,1,0,0, 0,0,1,0, 0,0,0,1};

    protected Affine matrix = new Affine(idt, MatrixType.MT_3D_4x4,0);

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane pane = new BorderPane();

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));

        HBox box_rotateX = new HBox(10);
        box_rotateX.setPadding(new Insets(10));
        box_rotateX.setStyle("-fx-background-color: gainsboro");
        Slider slider_rotateX = new Slider(0,pi_x2,0);
        slider_rotateX.setStyle("-fx-background-color: gainsboro");
        Label label_rotateX = new Label(String.format("%3s",0));
        label_rotateX.setPrefWidth(40);
        slider_rotateX.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        dx = newValue.doubleValue();
                        label_rotateX.setText(String.format("%3s",(int)(dx*deg_per_rad)));
                        update();
                    }
                }
        );

        HBox box_rotateY = new HBox(10);
        box_rotateY.setPadding(new Insets(10));
        box_rotateY.setStyle("-fx-background-color: darkgray");
        Slider slider_rotateY = new Slider(0,pi_x2,0);
        slider_rotateY.setStyle("-fx-background-color: darkgray");
        Label label_rotateY =  new Label(String.format("%3s",0));
        label_rotateY.setPrefWidth(40);
        slider_rotateY.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        dy = newValue.doubleValue();
                        label_rotateY.setText(String.format("%3s",(int)(dy*deg_per_rad)));
                        update();
                    }
                }
        );

        HBox box_rotateZ = new HBox(10);
        box_rotateZ.setPadding(new Insets(10));
        box_rotateZ.setStyle("-fx-background-color: gainsboro");
        Slider slider_rotateZ = new Slider(0,pi_x2,0);
        slider_rotateZ.setStyle("-fx-background-color: gainsboro");
        Label label_rotateZ = new Label(String.format("%3s",0));
        label_rotateZ.setPrefWidth(40);
        slider_rotateZ.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        dz = newValue.doubleValue();
                        label_rotateZ.setText(String.format("%3s",(int)(dz*deg_per_rad)));
                        update();
                    }
                }
        );

        HBox box_translateX = new HBox(10);
        box_translateX.setPadding(new Insets(10));
        box_translateX.setStyle("-fx-background-color: darkgray");
        Slider slider_translateX = new Slider(-range,range,0);
        slider_translateX.setStyle("-fx-background-color: darkgray");
        Label label_translateX= new Label(String.format("%.3f",0d));
        slider_translateX.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        tx = newValue.doubleValue();
                        label_translateX.setText(String.format("%.3f",tx));
                        update();
                    }
                }
        );

        HBox box_translateY = new HBox(10);
        box_translateY.setPadding(new Insets(10));
        box_translateY.setStyle("-fx-background-color: gainsboro");
        Slider slider_translateY =  new Slider(-range,range,0);
        slider_translateY.setStyle("-fx-background-color: gainsboro");
        Label label_translateY= new Label(String.format("%.3f",0d));
        slider_translateY.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        ty = newValue.doubleValue();
                        label_translateY.setText(String.format("%.3f",ty));
                        update();
                    }
                }
        );

        HBox box_translateZ = new HBox(10);
        box_translateZ.setPadding(new Insets(10));
        box_translateZ.setStyle("-fx-background-color: darkgray");
        Slider slider_translateZ =  new Slider(-range*2,range*30,0);
        slider_translateZ.setStyle("-fx-background-color: darkgray");
        Label label_translateZ= new Label(String.format("%.3f",0d));
        slider_translateZ.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        tz = newValue.doubleValue();
                        label_translateZ.setText(String.format("%.3f",tz));
                        update();
                    }
                }
        );

        box_rotateX.getChildren().addAll(new Label("X回転:"), label_rotateX);
        box_rotateY.getChildren().addAll(new Label("Y回転:"), label_rotateY);
        box_rotateZ.getChildren().addAll(new Label("Z回転:"), label_rotateZ);
        box_translateX.getChildren().addAll(new Label("X移動:"), label_translateX);
        box_translateY.getChildren().addAll(new Label("Y移動:"),label_translateY );
        box_translateZ.getChildren().addAll(new Label("Z移動:"), label_translateZ);
        Button button = new Button("リセット");
        button.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        dx=0;
                        dy=0;
                        dz=0;
                        tx=0;
                        ty=0;
                        tz=0;
                        update();
                    }
                }
        );
        vBox.getChildren().addAll(slider_rotateX,box_rotateX
                ,slider_rotateY,box_rotateY
                , slider_rotateZ, box_rotateZ
                ,slider_translateX,box_translateX
                ,slider_translateY,box_translateY
                ,slider_translateZ,box_translateZ,button);

        pane.setRight(vBox);

        Group Root_root = new Group();
        root = new Group();
        root.setStyle("-fx-background-color: black");

        Box box_x = new Box(length,thickness,thickness);
        box_x.setMaterial(new PhongMaterial(Color.RED));

        Box box_y = new Box(thickness,length,thickness);
        box_y.setMaterial(new PhongMaterial(Color.GREEN));

        Box box_z = new Box(thickness,thickness,length);
        box_z.setMaterial(new PhongMaterial(Color.BLUE));

        PointLight white_light = new PointLight(Color.WHITE);
        white_light.setTranslateX(50);
        white_light.setTranslateY(-50);
        white_light.setTranslateZ(-50);

        AmbientLight ambient_light = new AmbientLight(Color.rgb(255, 255, 255, 0.6));

        root.getChildren().addAll(box_x,box_y,box_z,ambient_light);
        root.getTransforms().setAll(matrix);

        Root_root.getChildren().addAll(root, white_light);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-100);
        camera.setTranslateX(5);
        camera.setTranslateY(-5);
        camera.setFarClip(1000);

        SubScene subScene = new SubScene(Root_root, 1200,800,true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.DARKGRAY);
        subScene.setCamera(camera);
        pane.setCenter(subScene);

        Scene scene = new Scene(pane, 1400,800,true, SceneAntialiasing.BALANCED);


        primaryStage.setTitle("アフィン変換");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void update() {
        setEularRotation(dx, dy,dz);
        matrix.setTx(tx);
        matrix.setTy(ty);
        matrix.setTz(tz);
    }

    public void setEularRotation(double rx, double ry, double rz) {
        double cx = Math.cos(rx);
        double cy = Math.cos(ry);
        double cz = Math.cos(rz);
        double sx = Math.sin(rx);
        double sy = Math.sin(ry);
        double sz = Math.sin(rz);
        matrix.setMxx(cy*cz);
        matrix.setMxy((sx * sy * cz) + (cx * sz));
        matrix.setMxz(-(cx * sy * cz) + (sx * sz));
        matrix.setMyx(-cy*sz);
        matrix.setMyy(-(sx * sy * sz) + (cx * cz));
        matrix.setMyz((cx * sy * sz) + (sx * cz));
        matrix.setMzx(sy);
        matrix.setMzy(-sx*cy);
        matrix.setMzz(cx*cy);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
