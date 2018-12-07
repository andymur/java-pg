package com.andymur.pg.java.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextFormatterRunner extends Application {
	private static final String INITIAL_VALUE = "3.1415";

	private Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
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
		final CheckBox commaCheckbox = new CheckBox("Comma separated");
		commaCheckbox.setSelected(false);


		editableField.setTextFormatter( new TextFormatter<Object>(change -> {
			/*final String currentText = change.getText();

			String commaSymbol;

			if (commaCheckbox.isSelected()) {
				commaSymbol = ",";
			} else {
				commaSymbol = "\\.";
			}

			change.setText(currentText.replaceAll("[^0-9" + commaSymbol + "]", ""));
			return change;*/

			//final String regexp = "^\\d*\\.?\\d+|\\d+\\.?\\d*$";

			final String regexp = "^\\d*$";
			final String currentText = ((TextField) change.getControl()).getText().concat(change.getText());

			if (!currentText.matches(regexp)) {
				change.setText("");
			} else {
				change.setText(change.getText());
			}

			return change;
		}));
		rootLayout.getChildren().addAll(editableField, commaCheckbox);
	}
}
