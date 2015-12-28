package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;


public class Main extends Application {
    static double init_x;
    static double init_y;
    final double length = 30;
    final double thickness = 0.5;
    final  double origin_size =2;
    final  double strength = 0.3;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        Box box_origin = new Box(origin_size,origin_size,origin_size);
        box_origin.setMaterial(new PhongMaterial(Color.MAGENTA));

        Box box_x = new Box(length,thickness,thickness);
        box_x.setMaterial(new PhongMaterial(Color.RED));
        box_x.setTranslateX(length/2);

        Box box_y = new Box(thickness,length,thickness);
        box_y.setMaterial(new PhongMaterial(Color.GREEN));
        box_y.setTranslateY(-length/2);

        Box box_z = new Box(thickness,thickness,length);
        box_z.setMaterial(new PhongMaterial(Color.BLUE));
        box_z.setTranslateZ(length/2);

        root.getChildren().addAll(box_origin, box_x,box_y,box_z);

        Rotate rotateY = new Rotate(0.0, Rotate.Y_AXIS);
        Rotate rotateX = new Rotate(0.0, Rotate.X_AXIS);
        root.getTransforms().addAll(rotateX,rotateY);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-100);
        camera.setTranslateX(10);
        camera.setTranslateY(-10);
        camera.setFarClip(1000);
        Scene scene = new Scene(root, 500, 300,true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.DARKGRAY);
        scene.setCamera(camera);

        scene.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        init_x = event.getSceneX();
                        init_y = event.getSceneY();
                    }
                }
        );

        scene.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        double x = event.getSceneX();
                        double y = event.getSceneY();
                        rotateX.setAngle(rotateX.getAngle() + (y-init_y)*strength);
                        rotateY.setAngle(rotateY.getAngle()+(init_x-x)*strength);
                        System.out.println(rotateX.getAngle() +" : "+ rotateY.getAngle() );

                        init_x = x;
                        init_y = y;
                    }
                }

        );

        primaryStage.setTitle("タイトル");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
