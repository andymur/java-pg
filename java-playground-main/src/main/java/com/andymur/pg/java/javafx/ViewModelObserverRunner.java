package com.andymur.pg.java.javafx;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewModelObserverRunner extends Application {

	private ViewModel viewModel = new ViewModel();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = initPane();

		primaryStage.setTitle("");
		Scene scene = new Scene(root, 320, 200);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Pane initPane() {
		Pane vBox = new VBox();

		TextField textField = new TextField("");

		textField.textProperty().bindBidirectional(viewModel.textProperty());

		Button okButton = new Button("Ok");

		okButton.setOnMouseClicked(
				event -> {
					System.out.println(viewModel.getText());
				}
		);
		vBox.getChildren().addAll(textField, okButton);
		return vBox;
	}

	public static void main(String[] args) {
		Object o = null;
		Boolean a = (Boolean) o ? true : false;
		launch(args);
	}

	static class ViewModel {
		private StringProperty textProperty = new SimpleStringProperty();

		public StringProperty textProperty() {
			return textProperty;
		}

		public void setText(final String text) {
			textProperty.set(text);
		}

		public String getText() {
			return textProperty.get();
		}
	}
}
