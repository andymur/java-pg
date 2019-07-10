package com.andymur.pg.java.javafx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WeirdObserverExperimentRunner extends Application {

	private SimpleStringProperty wireValueProperty = new SimpleStringProperty();

	private final List<Observable> observables = new ArrayList<>();
	private final List<MyObservable> myObservables = new ArrayList<>();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = initPane();

		primaryStage.setTitle("");
		Scene scene = new Scene(root, 320, 200);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private SimpleStringProperty changedValueProperty = new SimpleStringProperty();
	private SimpleBooleanProperty changedCheckBoxValueProperty = new SimpleBooleanProperty();

	private MyObservable textValueObservable = new MyObservable();
	private MyObservable checkBoxValueObservable = new MyObservable();

	public Pane initPane() {
		Pane vBox = new VBox();

		final Label label = new Label("");
		final TextField firstTextField = new TextField("");
		firstTextField.setAlignment(Pos.CENTER_RIGHT);
		final CheckBox checkBox = new CheckBox();

		firstTextField.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					changedValueProperty.set(newValue);
					textValueObservable.fireEvent();
				}
		);

		checkBox.selectedProperty().addListener(
				(observable, oldValue, newValue) -> {
					changedCheckBoxValueProperty.set(newValue);
					checkBoxValueObservable.fireEvent();
				}
		);

		vBox.getChildren().addAll(
				label,
				firstTextField,
				checkBox);

		observables.add(changedValueProperty);
		observables.add(changedCheckBoxValueProperty);

		myObservables.addAll(Arrays.asList(textValueObservable, checkBoxValueObservable));

		final Observable[] observablesArray = observables.toArray(new Observable[]{});


		Binding brokenBinding = Bindings.createStringBinding(
				() -> String.valueOf(new Random().nextGaussian()),
				observablesArray
		);

		Binding<String> workingBinding = Bindings.createStringBinding(
				() -> {
					final String value = observablesArray[0].toString();
					final String length = value == null ? "0" : String.valueOf(value.length());
					return String.valueOf(new Random().nextGaussian()) + "|" + length;
				},
				observablesArray
		);

		//label.textProperty().bind(workingBinding);

		for (MyObservable observable: myObservables) {
			observable.addListener(() -> wireValueProperty.setValue(String.valueOf(new Random().nextGaussian())));
		}

		label.textProperty().bind(
				wireValueProperty
		);

		return vBox;
	}

	interface MyListener {
		void update();
	}

	static class MyObservable {
		List<MyListener> listeners = new ArrayList<>();

		public void addListener(MyListener listener) {
			listeners.add(listener);
		}

		public boolean removeListener(MyListener listener) {
			return listeners.remove(listener);
		}

		public void fireEvent() {
			listeners.forEach(
					MyListener::update
			);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
