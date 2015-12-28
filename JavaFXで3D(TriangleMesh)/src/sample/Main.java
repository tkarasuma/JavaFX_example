package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
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

        MeshView meshView = new MeshView();
        TriangleMesh mesh = new TriangleMesh();
        //空間での頂点座標
        //インデックスをつくっている。
        float[] points = {
                -150,-100,0, //頂点 p０
                -150,100,0,//頂点 p１
                150,100,0,//頂点 p２
                150,-100,0//頂点 p３
        };
        //貼り付ける画像の座標
        //インデックスを作っている
        float[] textCoords = {
                0,0,//画像の左上の点 t0
                0,1,//画像の左下の点 t1
                1,1,//画像の右下の点 t2
                1,0,//画像の右上の点 t3
        };
        //上記の2つを対応づけて三角形の面をつくる。
        //インデックスで指定
        //反時計回りで三角形を定義していることに注意
        int[] faces = {
                0,0,1,1,2,2, //三角形１ p0,t0,p1,t1,p2,t2,
                2,2,3,3,0,0//三角形２ p2,t2,p3,t3,p0,t0
                //0,0,3,3,2,2
        };
        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(textCoords);
        mesh.getFaces().addAll(faces);
        meshView.setMesh(mesh);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("img/cat.jpg")));
        meshView.setMaterial(material);
        meshView.setCullFace(CullFace.NONE);

        LightBase   light       = new PointLight();
        light.setTranslateZ( -40 );

        root.getChildren().addAll(meshView,light);

        Rotate rotateY = new Rotate(0.0, Rotate.Y_AXIS);
        Rotate rotateX = new Rotate(0.0, Rotate.X_AXIS);
        root.getTransforms().addAll(rotateX,rotateY);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-400);
         camera.setFarClip(1000);
        Scene scene = new Scene(root, 750, 500,true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.DARKGRAY.darker());
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
