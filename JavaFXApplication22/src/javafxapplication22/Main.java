/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication22;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Mats, Christoffer, Jacob
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       
        //Panes
        BorderPane root = new BorderPane();
        Pane pane = new Pane();
        HBox hbox = new HBox();
        hbox.setPrefHeight(50);
        hbox.setStyle("-fx-background-color: black;");
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10,10,10,10));
        pane.setStyle("-fx-background-color: pink;");
        
        root.setCenter(pane);
        root.setBottom(hbox);
        
        //Interaktivitet
        TextField size = new TextField();
        TextField angle = new TextField();
        TextField trunk = new TextField();
        Button draw = new Button("Draw");
        
        size.setPromptText("Tre størrelse");
        angle.setPromptText("Vinkel på gren");
        trunk.setPromptText("Stammens størrelse");
        
        
        hbox.getChildren().addAll(size,angle,trunk,draw);
        
        
        Scene scene = new Scene(root, 600, 600);
        
        primaryStage.setTitle("Ygdrasil");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
