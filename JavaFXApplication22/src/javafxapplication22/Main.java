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
 * @author Jacob Kristensen, Christoffer Arnesen, Mats Engesund
 */

public class Main extends Application {
    
    Pane pane;
    BorderPane root;
    
    /**
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
       
        //Panes
        root = new BorderPane();
        pane = new Pane();
        HBox hbox = new HBox();
        hbox.setPrefHeight(50);
        hbox.setStyle("-fx-background-color: black;");
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10,10,10,10));
        pane.setStyle("-fx-background-color: pink;");
        
        //Line linje = new Line(300,500,300,400); //300,500,300,400
        
        //pane.getChildren().add(linje);
        
        root.setCenter(pane);
        root.setBottom(hbox);
        
        //Interaktivitet
        TextField size = new TextField();
        TextField angle = new TextField();
        TextField trunk = new TextField();
        TextField tilfeldighet = new TextField();
        Button draw = new Button("Draw");
        
        draw.setOnAction(e -> {
            pane.getChildren().clear();
            int size2 = parseInt(size.getText()); 
            int angle2 = parseInt(angle.getText()); 
            int trunk2 = parseInt(trunk.getText()); 
            int tilfeldighet2 = parseInt(tilfeldighet.getText());
            tegnTre(size2, trunk2, angle2, tilfeldighet2); // trenger en ekstra input for siste parameter i metoden
            
        });
        
        size.setPromptText("Tre størrelse");
        angle.setPromptText("Vinkel på gren");
        trunk.setPromptText("Stammens størrelse");
        tilfeldighet.setPromptText("Velg tilfeldighet [0-100]");
        
        hbox.getChildren().addAll(size,angle,trunk,tilfeldighet,draw);
        
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Yggdrasil");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    
    //Drivermetode, lager stammen og starter den rekursive metoden

    /**
     *
     * @param str - Antall rekursjoner valgt av brukeren
     * @param stammelengde - Lengde på første strek, valgt av brukeren
     * @param vinkling - Grad av vinkling, valgt av brukeren
     * @param tilfeldighet - Grad av tilfeldighet(0-100%) på treet, valgt av brukeren
     */
    public void tegnTre(int str, int stammelengde, int vinkling, int tilfeldighet) {
        int startPunktX = (int)pane.getWidth()/2, startPunktY = 550;
        Line st = new Line(startPunktX, startPunktY, startPunktX, startPunktY-stammelengde);
        pane.getChildren().add(st);
        
        rekursjon(str, st.getEndX(), st.getEndY(), 0, stammelengde-(stammelengde/4), vinkling, tilfeldighet); //30 endres senere---
    }
    
    
    //Rekursjonsmetode

    /**
     *
     * @param str - Antall rekursjoner gjenværende
     * @param startX - Startposisjon X for hvor neste gren skal starte
     * @param startY - Startposisjon Y for hvor neste gren skal starte
     * @param forrigeVinkling - Vinkling på forrige gren
     * @param lengde - lengden på grenen(e)som skal tegnes
     * @param vinkling - Grad av vinkling, valgt av brukeren
     * @param tilfeldighet - Grad av tilfeldighet(0-100%) på treet, valg av brukeren
     */
    public void rekursjon(int str, double startX, double startY, double forrigeVinkling, 
            double lengde, double vinkling, int tilfeldighet) {
        
        if (str <= 0 || lengde <= 2) 
            return;
        else {
            //Oppretter venstre og høyre gren
            Line venstre = new Line(startX, startY, startX, startY-lengde); 
            Line høyre = new Line(startX, startY, startX, startY-lengde);   
            //Legger til rotasjon
            Rotate rV = new Rotate();
            rV.setPivotX(startX);
            rV.setPivotY(startY);
            rV.setAngle(forrigeVinkling-vinkling);
            venstre.getTransforms().add(rV);
            
            Rotate rH = new Rotate();
            rH.setPivotX(startX);
            rH.setPivotY(startY);
            rH.setAngle(forrigeVinkling+vinkling);
            høyre.getTransforms().add(rH);
            
            //Legger til tilfeldighet(Dersom bruker ønsker det)
            int randomGenerator = (int)(Math.random()*99)+1;
            if (str <= 3 && randomGenerator <= tilfeldighet) {
                
                venstre.setEndY(startY-(lengde+Math.random()*35) );
                høyre.setEndY(startY-(lengde+Math.random()*35) );
                
                rV.setAngle(forrigeVinkling-Math.random()*40);
                rH.setAngle(forrigeVinkling+Math.random()*40);
            }
            
            pane.getChildren().addAll(venstre,høyre);
            //Henter de nye slutt-koordinatene til "grenene"
            Point2D vP = venstre.localToParent(venstre.getEndX(), venstre.getEndY());
            Point2D hP = høyre.localToParent(høyre.getEndX(), høyre.getEndY());
            
            //Metoden gjør to kall på seg selv, siden det skal vokse to grener ut fra hver gren
              //venstre
            rekursjon(str-1, vP.getX(), vP.getY(), forrigeVinkling-vinkling, lengde-(lengde/4), vinkling, tilfeldighet);
              //høyre
            rekursjon(str-1, hP.getX(), hP.getY(), forrigeVinkling+vinkling, (lengde-lengde/4), vinkling, tilfeldighet);
        }
    
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
