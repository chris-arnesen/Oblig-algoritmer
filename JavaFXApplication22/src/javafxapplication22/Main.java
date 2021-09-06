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
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
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
        TextField random = new TextField();
        
        
        Button draw = new Button("Draw");
        draw.setOnAction(e -> {
            int size2 = parseInt(size.getText()); 
            int angle2 = parseInt(angle.getText()); 
            int trunk2 = parseInt(trunk.getText()); 
            drawTree(size2, angle2, trunk2);
        });
        
        
        size.setPromptText("Tre størrelse");
        angle.setPromptText("Vinkel på gren");
        trunk.setPromptText("Stammens størrelse");
        random.setPromptText("Grad av random");
        
        
        hbox.getChildren().addAll(size,angle,trunk,random,draw);
      
        
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        
      
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("X: " + event.getSceneX());
                System.out.println("Y: " + event.getSceneY());
            }
        });
        
        
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
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error!");
        alert.setHeaderText("Incorrect input from user");
        alert.setContentText("Size cannot be smaller than the trunk of the tree");
        
        if(size < trunk) {
            alert.showAndWait().ifPresent(rs -> {
                if(rs == ButtonType.OK) {
                }
            });
            return;
        }
        
        int startX = WIDTH/2, startY = (int) pane.getHeight();
        Line line = new Line(startX, startY, startX, startY - trunk);
        pane.getChildren().add(line);
    
        drawRightBranch(trunk/2, startX, startY-trunk, angle);
        drawLeftBranch(trunk/2, startX, startY-trunk, angle*-1);
        drawMiddleBranch(trunk/2, startX, startY-trunk, angle);
    }
    
    
    public void drawRightBranch(int size, int startX, int startY, int angle) {
        if(size <= 2) 
            return;
        
        Line branch = new Line(startX, startY, startX, startY - size);
        pane.getChildren().add(branch);
        
        Rotate r = new Rotate();
        r.setPivotX(startX);
        r.setPivotY(startY);
        r.setAngle(angle);
        branch.getTransforms().add(r);
        
        angle = angle + angle;
        Point2D nyekord = branch.localToParent(branch.getEndX(),branch.getEndY());
        startX = (int) nyekord.getX();
        startY = (int) nyekord.getY();
        size = size/2;
        
        drawRightBranch(size, startX, startY, angle);
        drawRightBranch(size, startX, startY, angle-(angle/2));
    }
    
    
    public void drawMiddleBranch(int size, int startX, int startY, int angle) {
        if(size <= 2) 
            return; 
        
        Line branch = new Line(startX, startY, startX, startY - size); 
        pane.getChildren().add(branch);
        
        startX = (int) branch.getEndX(); 
        startY = (int) branch.getEndY(); 
        size = size/2; 
        
        drawLeftBranch(size, startX, startY, angle*-1); 
        drawRightBranch(size, startX, startY, angle);
        drawMiddleBranch(size, startX, startY, angle);
    }
    
    
    public void drawLeftBranch(int size, int startX, int startY, int angle) {
        if(size <= 2) 
            return; 
        
        Line branch = new Line(startX, startY, startX, startY - size);
        pane.getChildren().add(branch); 
        
        Rotate r = new Rotate();
        r.setPivotX(startX);
        r.setPivotY(startY);
        r.setAngle(angle);
        branch.getTransforms().add(r);
        
        angle = angle*2;
        System.out.println(angle);
        //startX = (int) branch.getEndX(); 
        Point2D nyekord = branch.localToParent(branch.getEndX(),branch.getEndY());
        startX = (int) nyekord.getX();
        startY = (int) nyekord.getY();
        size = size/2;
        
        drawLeftBranch(size, startX, startY, angle);
        drawLeftBranch(size, startX, startY, (int) (angle*0.5));
    }

}
