/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication22;

import static java.lang.Integer.parseInt;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 *
 * @author Mats, Christoffer, Jacob
 */
public class Main extends Application {
    
    Pane pane;
    
    @Override
    public void start(Stage primaryStage) {
       
        //Panes
        BorderPane root = new BorderPane();
        pane = new Pane();
        HBox hbox = new HBox();
        hbox.setPrefHeight(50);
        hbox.setStyle("-fx-background-color: black;");
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10,10,10,10));
        pane.setStyle("-fx-background-color: pink;");
        
        Line linje = new Line(300,500,300,400); //300,500,300,400
        
        pane.getChildren().add(linje);
        
        /*Rotate rotate = new Rotate();
        rotate.setPivotX(linje.getStartX());
        rotate.setPivotY(linje.getStartY());
        rotate.setAngle(-10);
        linje.getTransforms().add(rotate);
        */
        //tegnTre(300,400,40,10,50);
        root.setCenter(pane);
        root.setBottom(hbox);
        
        //Interaktivitet
        TextField size = new TextField();
        TextField angle = new TextField();
        TextField trunk = new TextField();
        Button draw = new Button("Draw");
        
        draw.setOnAction(e -> {
            int size2 = parseInt(size.getText()); 
            int angle2 = parseInt(angle.getText()); 
            int trunk2 = parseInt(trunk.getText()); 
            
            tegnTre(300, 400, angle2, size2, 50); 
        });
        
        size.setPromptText("Tre størrelse");
        angle.setPromptText("Vinkel på gren");
        trunk.setPromptText("Stammens størrelse");
        
        
        hbox.getChildren().addAll(size,angle,trunk,draw);
        
        
        Scene scene = new Scene(root, 600, 600);
        
        primaryStage.setTitle("Yggdrasil");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //tegnTre(300,400,0,10,5,50);
    //Metode som lager treet
    public void tegnTre(double startX, double startY, int vinkling, int str, double lengde) {
        //Base case, i dette tilfellet dersom treet når 2 px, eller størrelse er oppnådd
        //Neste linje skal være halvparten så lang, og ha litt annerledes vinkling
          //Base case: Dersom vi når den størrelsen vi har bedt om
        if (str <= 0 || lengde <= 2 || startX <= 0 && startY <= 0) 
            return; 
        else {
            //Lager venstre utgrening som en rett strek
            Line venstreGren = new Line(startX, startY, startX, startY-lengde);
            //Legger til vinkling på utgreningen
            Rotate rotVenstre = new Rotate();
            rotVenstre.setPivotX(venstreGren.getStartX());
            rotVenstre.setPivotY(venstreGren.getStartY());
            rotVenstre.setAngle(-vinkling); //-20
            venstreGren.getTransforms().add(rotVenstre);
            //Finner nye sluttkoordinater til utgrening, etter vinkling har blitt lagt til
              //Disse blir nye startkoordinater for neste utgrening
            Point2D nyeVenstreKoordinater = venstreGren.localToParent(venstreGren.getEndX(),venstreGren.getEndY());
            
            //if (vinkling == 40)
            //
            
            Line høyreGren = new Line(startX, startY, startX, startY-lengde);
            Rotate rotHøyre = new Rotate();
            rotHøyre.setPivotX(høyreGren.getStartX());
            rotHøyre.setPivotY(høyreGren.getStartY());
            rotHøyre.setAngle(vinkling);
            høyreGren.getTransforms().add(rotHøyre);
            
            System.out.println("angle: " + rotHøyre.getAngle() + "\n" + 
                    "angle left: " + rotVenstre.getAngle());
            Point2D nyeHøyreKoordinater = høyreGren.localToParent(høyreGren.getEndX(),høyreGren.getEndY());
            
            //Legger til de nye utgreningene til FX-vinduet
            pane.getChildren().addAll(venstreGren,høyreGren);
            
            //Her skal metoden kalles igjen to ganger, en gang for hver gren
              //Venstre
            tegnTre( nyeVenstreKoordinater.getX(), nyeVenstreKoordinater.getY(),
                        (vinkling),(str-1),lengde/2);
              //Høyre
            tegnTre( nyeHøyreKoordinater.getX(), nyeHøyreKoordinater.getY(),
                        (vinkling), (str-1),lengde/2);
            
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
