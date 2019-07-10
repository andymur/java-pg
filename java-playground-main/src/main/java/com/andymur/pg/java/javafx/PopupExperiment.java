package com.andymur.pg.java.javafx;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * Created by andymur on 12/1/17.
 */
public class PopupExperiment extends Application {


    private Stage primaryStage;
    private BorderPane rootLayout;

    private ToolBar toolBar;

    private Button newButton;
    private Button closeButton;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("My new brand app");
        this.primaryStage = primaryStage;

        buildUi();
    }

    private void buildUi() {
        rootLayout = new BorderPane();
        rootLayout.setPrefWidth(600);
        rootLayout.setPrefHeight(400);

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        buildToolbar();

        primaryStage.show();

        final Popup popup = new Popup();
        popup.setHeight(50);
        popup.setWidth(50);
        Menu menu = new Menu();

        menu.getItems().addAll(
                new MenuItem("AAAA"),
                new MenuItem("BBBB")
        );


        popup.getContent().addAll(new MenuBar(menu)/*new Button("Pfff")*//*new Circle(25, 25, 50, Color.AQUAMARINE)*/);



        final PopupControl popupControl = new PopupControl();
        popupControl.setPrefHeight(50);
        popupControl.setPrefWidth(50);

        popupControl.setStyle("-fx-background-color: blue;");
        popupControl.setSkin(new PopupControlSkin(popupControl));

        ContextMenu contextMenu = new ContextMenu();

        contextMenu.getItems().addAll(
                new MenuItem("AAAA"),
                new MenuItem("BBBB")
        );

        newButton.setOnMouseClicked(
                event -> {
                    //popupControl.show(toolBar, 20, 30);
                    //popupControl.show(primaryStage);
                    //menu.show();
                    contextMenu.show(newButton, newButton.getLayoutX(), newButton.getLayoutY());
                }
        );

        closeButton.setOnMouseClicked(
                event -> {
                    popupControl.hide();
                }
        );
    }

    private class PopupControlSkin implements Skin<PopupControl> {

        PopupControl popupControl;
        MenuBar menuBar;

        public PopupControlSkin(PopupControl popupControl) {
            this.popupControl = popupControl;
            menuBar = new MenuBar();
            ContextMenu contextMenu = new ContextMenu();
            Menu menu = new Menu();

            menu.getItems().addAll(
                    new MenuItem("AAAA"),
                    new MenuItem("BBBB")
            );

            contextMenu.getItems().addAll(
                    new MenuItem("AAAA"),
                    new MenuItem("BBBB")
            );

            menuBar.setContextMenu(contextMenu);
            //menuBar.getMenus().addAll(menu);

            contextMenu.onShownProperty().bind(
                    popupControl.onShownProperty()
            );

        }

        @Override
        public PopupControl getSkinnable() {
            return popupControl;
        }

        @Override
        public Node getNode() {
            return menuBar;
        }

        @Override
        public void dispose() {

        }
    }

    private void buildToolbar() {
        toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);

        newButton = new Button("New...");

        toolBar.getItems().add(newButton);
        closeButton = new Button("Close...");
        toolBar.getItems().add(closeButton);


        rootLayout.setLeft(toolBar);
    }
    
}
