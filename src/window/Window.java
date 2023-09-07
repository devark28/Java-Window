/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author PC
 */
public class Window extends Application {
    
    Stage primaryStage;
    Double ScreenX;
    Double ScreenY;
    
    Double WindowH;
    Double WindowW;
    
    Double WindowY;
    Double WindowX;
    
    Double MinW;
    Double MinH;
    
    Double PrefW;
    Double PrefH;
    
    Double ResiX;
    Double ResiY;
    Double ResiX_;
    Double ResiY_;
    
    SimpleBooleanProperty Resizing = new SimpleBooleanProperty();
    Boolean Maximised;
    Boolean Resize;
    
    Boolean onX = false;
    Boolean onY = false;
    Boolean onX_ = false;
    Boolean onY_ = false;
    
    @Override
    public void start(Stage primaryStage) {
        Scene scene;
        this.primaryStage = primaryStage;
        Button i = new Button("I");
        Button m = new Button("M");
        Button c = new Button("C");
        Button menu = new Button("MENU");
        
        /**********************************************************************
         * border hover handeling
         **********************************************************************/
        
        HBox WinTools = new HBox(5,i,m,c);
        HBox title = new HBox(new Label("Title"));
        HBox.setHgrow(title, Priority.ALWAYS);
        title.setAlignment(Pos.TOP_CENTER);
        HBox Head = new HBox(5,menu,title,WinTools);
        
        BorderPane root = new BorderPane();
        root.getStyleClass().add("window");
        StackPane holder = new StackPane(Head);
        
        Line top = new Line();
        top.endXProperty().bind(root.layoutXProperty().add(root.widthProperty()));
        top.getStyleClass().add("line");
        top.setCursor(Cursor.V_RESIZE);
        root.setTop(top);
        
        Line right = new Line();
        right.startXProperty().bind(root.layoutXProperty().add(root.widthProperty()));
        right.startYProperty().bind(root.layoutYProperty());
        right.endXProperty().bind(root.layoutXProperty().add(root.widthProperty()));
        right.endYProperty().bind(root.layoutYProperty().add(root.heightProperty()));
        right.getStyleClass().add("line");
        right.setCursor(Cursor.H_RESIZE);
        root.setRight(right);
        
        Line bottom = new Line();
        bottom.endXProperty().bind(root.layoutXProperty().add(root.widthProperty()));
        bottom.getStyleClass().add("line");
        bottom.setCursor(Cursor.V_RESIZE);
        root.setBottom(bottom);
        
        Line left = new Line();
        left.startXProperty().bind(root.layoutXProperty());
        left.startYProperty().bind(root.layoutYProperty());
        left.endXProperty().bind(root.layoutXProperty());
        left.endYProperty().bind(root.layoutYProperty().add(root.heightProperty()));
        left.getStyleClass().add("line");
        left.setCursor(Cursor.H_RESIZE);
        root.setLeft(left);
        
        root.setCenter(holder);
        MinW = 200.0;
        MinH = 150.0;
        
        /**********************************************************************
         * border resize handeling
         **********************************************************************/
        
        //bottom
        
        bottom.setOnMousePressed((event) -> {
            Resize = false;
            onY_ = true;
            ScreenY = event.getScreenY();
            WindowH = primaryStage.getHeight();
        });
        bottom.setOnMouseDragged((event) -> {
            Resizing.set(true);
            primaryStage.setHeight(WindowH + event.getScreenY()-ScreenY);
            Resizing.set(false);
        });
        bottom.setOnMouseReleased((event) -> {
            Resize = true;
            onY_ = false;
            Resizing.set(true);
            Resizing.set(false);
            Resize = false;
        });
        
        //top
        
        top.setOnMousePressed((event) -> {
            Resize = false;
            onY = true;
            ScreenY = event.getScreenY();
            WindowH = primaryStage.getHeight();
        });
        top.setOnMouseDragged((event) -> {
            Resizing.set(true);
            primaryStage.setHeight(WindowH + ScreenY-event.getScreenY());
            primaryStage.setY(event.getScreenY());
            Resizing.set(false);
        });
        top.setOnMouseReleased((event) -> {
            Resize = true;
            onY = false;
            Resizing.set(true);
            Resizing.set(false);
            Resize = false;
        });
        
        //right
        
        right.setOnMousePressed((event) -> {
            Resize = false;
            onX_ = true;
            ScreenX = event.getScreenX();
            WindowW = primaryStage.getWidth();
        });
        right.setOnMouseDragged((event) -> {
            Resizing.set(true);
            primaryStage.setWidth(WindowW + event.getScreenX()-ScreenX);
            Resizing.set(false);
        });
        right.setOnMouseReleased((event) -> {
            Resize = true;
            onX_ = false;
            Resizing.set(true);
            Resizing.set(false);
            Resize = false;
        });
        
        //left
        
        left.setOnMousePressed((event) -> {
            Resize = false;
            onX = true;
            ScreenX = event.getScreenX();
            WindowW = primaryStage.getWidth();
        });
        left.setOnMouseDragged((event) -> {
            Resizing.set(true);
            primaryStage.setWidth(WindowW + ScreenX-event.getScreenX());
            primaryStage.setX(event.getScreenX());
            Resizing.set(false);
        });
        left.setOnMouseReleased((event) -> {
            Resize = true;
            onX = false;
            Resizing.set(true);
            Resizing.set(false);
            Resize = false;
        });
        
        //drag
        
        holder.setOnMousePressed((event) -> {
            ScreenX = event.getScreenX();
            ScreenY = event.getScreenY();
            WindowX = primaryStage.getX();
            WindowY = primaryStage.getY();
        });
        holder.setOnMouseDragged((event) -> {
            primaryStage.setX(event.getScreenX()+WindowX-ScreenX);
            primaryStage.setY(event.getScreenY()+WindowY-ScreenY);
        });
        
        /**********************************************************************
         * maximum resizing handeling
         **********************************************************************/
        
        Stage maxOrienter = new Stage();
        maxOrienter.setOpacity(0);
        Maximised = maxOrienter.isMaximized();
        
        holder.setOnMouseClicked((event) -> {
            if(event.getClickCount()==2 && event.getButton().equals(MouseButton.PRIMARY)){
                maxOrienter.show();
                if(maxOrienter.isMaximized()){
                    //minimize
                    maxOrienter.setMaximized(false);
                    primaryStage.setWidth(PrefW);
                    primaryStage.setHeight(PrefH);
                    primaryStage.centerOnScreen();
                    Maximised = false;
                }else{
                    //maximize
                    maxOrienter.setMaximized(true);
                    PrefW = primaryStage.getWidth();
                    PrefH = primaryStage.getHeight();
                    primaryStage.setY(maxOrienter.getY()+8);
                    primaryStage.setX(maxOrienter.getX()+8);
                    primaryStage.setWidth(maxOrienter.getWidth()-16);
                    primaryStage.setHeight(maxOrienter.getHeight()-16);
                    Maximised = true;
                }
                maxOrienter.hide();
            }
            if(event.getButton().equals(MouseButton.SECONDARY)){
                primaryStage.hide();
            }
        });
        
        /**********************************************************************
         * screen bounds resizing handeling
         **********************************************************************/
        
        Resizing.addListener((observable) -> {
            if (Resizing.get()) {
                ResiX = primaryStage.getX();
                ResiY = primaryStage.getY();
                ResiX_ = primaryStage.getX() + primaryStage.getWidth();
                ResiY_ = primaryStage.getY() + primaryStage.getHeight();
                System.out.println(" X : "+ResiX+"| Y : "+ResiY);
                maxOrienter.show();
                if(ResiX <= maxOrienter.getX()+16 || ResiY <= maxOrienter.getY()+16 || ResiX_ >= maxOrienter.getX() + maxOrienter.getWidth()-16 || ResiY_ >= maxOrienter.getY() + maxOrienter.getHeight()-32){
                    maxOrienter.setMaximized(true);
                }
                //
                if(onX){
                    Maximised = ResiX <= maxOrienter.getX()+16 ? Boolean.TRUE : Boolean.FALSE;
                }else if(onY){
                    Maximised = ResiY <= maxOrienter.getY()+16 ? Boolean.TRUE : Boolean.FALSE;
                }else if(onX_){
                    Maximised = ResiX_ >= maxOrienter.getX() + maxOrienter.getWidth()-16 ? Boolean.TRUE : Boolean.FALSE;
                }else if(onY_){
                    Maximised = ResiY_ >= maxOrienter.getY() + maxOrienter.getHeight()-32 ? Boolean.TRUE : Boolean.FALSE;
                }
                maxOrienter.hide();
            }else{
                System.out.println("Maximised : "+Maximised+" Resize : "+Resize);
                if(Maximised && Resize){
                    maxOrienter.show();
                    PrefW = primaryStage.getWidth();
                    PrefH = primaryStage.getHeight();
                    primaryStage.setX(maxOrienter.getX()+8);
                    primaryStage.setY(maxOrienter.getY()+8);
                    primaryStage.setWidth(maxOrienter.getWidth()-16);
                    primaryStage.setHeight(maxOrienter.getHeight()-16);
                    maxOrienter.hide();
                }
            }
        });
        
        /**********************************************************************
         * scene mounting
         **********************************************************************/
        
        
        
        scene = new Scene(root);
        scene.getStylesheets().add("/window/window.css");
        scene.setFill(null);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        PrefW = primaryStage.getWidth();
        PrefH = primaryStage.getHeight();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
