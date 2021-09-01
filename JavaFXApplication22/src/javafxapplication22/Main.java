/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication22;

import static java.lang.Integer.parseInt;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 *
 * @author Mats, Christoffer, Jacob
 */
public class Main extends Application {
    
    public final int WIDTH = 600; 
    public final int HEIGHT = 600; 
    
    public BorderPane root;
    public Pane pane;
    public HBox hbox;
    
    @Override
    public void start(Stage primaryStage) {
       
        //Panes
        root = new BorderPane();
        pane = new Pane();
        hbox = new HBox();
        hbox.setPrefHeight(50);
        hbox.setStyle("-fx-background-color: black;");
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10,10,10,10));
        pane.setStyle("-fx-background-color: pink;");
        
        root.setCenter(pane);
        root.setBottom(hbox);
        
        //Interaktivitet
        TextField size = new TextField();
        /*
        Hvis vi vil at inp.feltet bare skal akseptere tall gjør vi det slik: 
        size.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*"))
                    size.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });*/
        
        TextField angle = new TextField();
        TextField trunk = new TextField();
        
        Button draw = new Button("Draw");
        //draw.setOnAction(e -> drawTree(size.getText(), angle.getText(), trunk.getText()));
        draw.setOnAction(e -> {
            int size2 = parseInt(size.getText()); 
            int angle2 = parseInt(angle.getText()); 
            int trunk2 = parseInt(trunk.getText()); 
            drawTree(size2, angle2, trunk2);
        });
        
        size.setPromptText("Tre størrelse");
        angle.setPromptText("Vinkel på gren");
        trunk.setPromptText("Stammens størrelse");
        
        
        hbox.getChildren().addAll(size,angle,trunk,draw);
        
        
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        
        primaryStage.setTitle("Yggdrasil");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    public void drawTree(int size, int angle, int trunk) {  
        if(trunk <= 2)
            return; 
        
        double posX = WIDTH/2, posY = pane.getHeight(); 
        
        Line trunkLine = new Line(posX, posY, posX, posY - trunk); 
        pane.getChildren().add(trunkLine); 
        
    }
    
}
