package com.andymur.pg.java.javafx;

import java.io.IOException;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlgoObserverExperimentRunner extends Application {

	private static final String INITIAL_VALUE = "Benvenuto tutti";

	private Stage primaryStage;
	private String editableFieldValue = "";
	private final SimpleStringProperty editableFieldValueProperty = new SimpleStringProperty("");
	private StringBinding received;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("My new brand app");
		this.primaryStage = primaryStage;

		initRootLayout();
	}

	private void initRootLayout() throws IOException {
		Pane rootLayout = new VBox();
		rootLayout.setPrefWidth(322);
		rootLayout.setPrefHeight(200);
		addControls(rootLayout);
		Scene scene = new Scene(rootLayout);


		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void addControls(final Pane rootLayout) {

		final TextField editableField = new TextField(INITIAL_VALUE);

		editableField.setOnKeyReleased(event -> {
			editableFieldValue = editableField.getText() + "changed";
			//System.out.println("Changed " + editableFieldValueProperty);
			editableFieldValueProperty.set(editableFieldValue);
		});

		editableFieldValueProperty.set(editableField.getText() + "changed");

		final Label label = new Label("");
		final Button okButton = new Button("Ok");


		/*editableFieldValueProperty.addListener(
				(observable, oldValue, newValue) -> {
					label.setText(String.valueOf(new Random().nextGaussian()));
				}
		);*/
		//label.textProperty().bind(editableFieldValueProperty);

		received = Bindings.createStringBinding(
				() -> {
					return String.valueOf(new Random().nextGaussian()).concat(editableFieldValueProperty.get());
				},
				editableFieldValueProperty
		);

		label.textProperty().bind(
				received
		);

		ListView<String> list = new ListView<String>();

		ObservableList<String> items = FXCollections.observableArrayList (
				"Single", "Double", "Suite", "Family App");

		list.setItems(items);


		rootLayout.getChildren().addAll(
				editableField,
				label,
				list,
				okButton
		);
	}
}
