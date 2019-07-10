package com.andymur.pg.java.javafx;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class ToolBarExperimentRunner extends Application {

	private Stage primaryStage;
	private BorderPane borderPane;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("My new brand app");
		this.primaryStage = primaryStage;

		initRootLayout();
		show();
	}

	private void initRootLayout() {
		borderPane = new BorderPane();

		borderPane.setPrefWidth(800);
		borderPane.setPrefHeight(400);

		Scene scene = new Scene(borderPane);
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	private void show() {
		Button newButton = new Button("New");
		Button clipboardButton = new Button("Clip");
		Button importButton = new Button("Import");
		Button exportButton = new Button("Export");
		Button templateButton = new Button("Template");

		ToolBar toolBar = new ToolBar(newButton, clipboardButton, importButton, new Separator(), exportButton, templateButton);
		toolBar.setOrientation(Orientation.VERTICAL);
		VBox leftBox = new VBox(40, toolBar);

		leftBox.prefHeightProperty().bind(borderPane.heightProperty());
		leftBox.prefWidthProperty().bind(borderPane.widthProperty());

		toolBar.prefHeightProperty().bind(leftBox.heightProperty());
		toolBar.prefWidthProperty().bind(leftBox.widthProperty());


		borderPane.setLeft(leftBox);

		newButton.setOnMouseClicked(
				event -> {
					Popup popup = new Popup();
					popup.getContent().addAll(new Button("AAAA"));

					popup.setWidth(100);
					popup.setHeight(100);
					popup.show(borderPane.getScene().getWindow());
				}
		);

	}

}
