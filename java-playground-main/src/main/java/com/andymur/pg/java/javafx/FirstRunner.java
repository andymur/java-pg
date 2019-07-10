package com.andymur.pg.java.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;

// http://code.makery.ch/library/javafx-8-tutorial/ru/part1/

public class FirstRunner extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("My new brand app");
		this.primaryStage = primaryStage;

		initRootLayout();
		showOverview();
	}

	private void initRootLayout() throws IOException {
		rootLayout = (BorderPane) prepareLoader("RootLayout").load();
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	private void showOverview() throws IOException {
		AnchorPane overviewPane = (AnchorPane) prepareLoader("PersonOverview").load();;
		rootLayout.setCenter(overviewPane);
	}

	private FXMLLoader prepareLoader(String resource) throws MalformedURLException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Paths.get("src/main/resources/view/"+ resource + ".fxml").toUri().toURL());
		return loader;
	}
}
