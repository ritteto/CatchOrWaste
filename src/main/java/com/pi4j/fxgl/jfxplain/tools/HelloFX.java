package com.pi4j.fxgl.jfxplain.tools;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import com.almasb.fxgl.audio.AudioPlayer;

/**
 * A tiny tool to check the basic setup of JavaFX.
 *
 * It's a long way from here to have a real application.
 *
 * But as long as 'HelloFX' isn't properly working, it's useless to go any further.
 *
 * You should make it run in X11-mode and in DRM.
 *
 * It's not meant to be any kind of template to start your development.
 *
 * Initially copied from https://github.com/openjfx/samples/blob/master/CommandLine/Modular/CLI/hellofx/src/hellofx/HelloFX.java
 *
 * @author Dieter Holz
 */
public class HelloFX extends Application {


    @Override
    public void start(Stage stage) {
        String javaVersion   = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

        Label lbl = new Label("JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");


        AudioClip buzzer = new AudioClip(getClass().getResource("/assets/sounds/drop.wav").toExternalForm());
        Button btn = new Button("Say Hello");
        btn.setOnAction(event -> {
            lbl.setText("Hello");
            buzzer.play();
        });

        MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/assets/music/bgm.mp3").toExternalForm()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        ImageView imgView = new ImageView(new Image(HelloFX.class.getResourceAsStream("/jfx/openduke.png")));
        imgView.setFitHeight(200);
        imgView.setPreserveRatio(true);

        VBox rootPane = new VBox(50, imgView, lbl, btn);
        rootPane.setAlignment(Pos.CENTER);

        rootPane.getStylesheets().add(HelloFX.class.getResource("/jfx/styles.css").toExternalForm());

        Scene scene = new Scene(rootPane, 640, 480);
        stage.setTitle("Plain JavaFX App");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("shutdown hook ++++++++++++");
        }));
        launch();
    }

}