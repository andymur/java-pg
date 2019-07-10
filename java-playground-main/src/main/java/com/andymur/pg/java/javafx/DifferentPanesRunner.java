// samples from here: http://www.guigarage.com/2015/10/javafx-8-refcard/
// https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
package com.andymur.pg.java.javafx;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DifferentPanesRunner extends Application {

	private static final int OUTER_PANE_DEFAULT_SIZE = 400;

	private static final String GRUEN = "#0f0";
	private static final String ROT = "#f00";
	private static final String BLAU = "#00f";
	private static final String GELB = "#ff0";
	private static final String MAGENTA = "#f0f";
	private static final String SCHWARZ = "#000";

	private static final List<String> ALL_COLORS = Arrays.asList(GRUEN, ROT, BLAU, GELB, MAGENTA, SCHWARZ);

	Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		Scene scene = new Scene(/*stackPaneCase()*//*borderPaneCase()vBoxCase()*//*anchorPaneCase()*//*tilePaneCase()*//*flowPaneCase()*/gridPaneCase());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private static HBox hBoxCase() {
		HBox hboxPane = new HBox();
		sizeOuterPane(hboxPane);

		for (String color: ALL_COLORS) {
			hboxPane.getChildren().add(innerPane(color, 100));
		}

		return hboxPane;
	}

	private static VBox vBoxCase() {
		VBox vboxPane = new VBox();
		sizeOuterPane(vboxPane);

		for (String color: ALL_COLORS) {
			vboxPane.getChildren().add(innerPane(color, 100));
		}

		return vboxPane;
	}

	private static BorderPane borderPaneCase() {
		BorderPane borderPane = new BorderPane();
		sizeOuterPane(borderPane);

		borderPane.setCenter(innerPane(GRUEN, 100));
		borderPane.setLeft(innerPane(ROT, 100));
		borderPane.setRight(innerPane(GELB, 100));
		borderPane.setTop(innerPane(MAGENTA, 100));
		borderPane.setBottom(innerPane(BLAU, 100));

		return borderPane;
	}

	private static StackPane stackPaneCase() {
		final int edgeDecreaseStep = 50;

		StackPane outerPane = new StackPane();
		outerPane.setPrefHeight(OUTER_PANE_DEFAULT_SIZE);
		outerPane.setPrefWidth(OUTER_PANE_DEFAULT_SIZE);

		int currentEdgeSize = OUTER_PANE_DEFAULT_SIZE;

		for (String color: ALL_COLORS) {
			System.out.println("trying to create square pane with size of: " + currentEdgeSize);
			Pane pane = innerPane(color, currentEdgeSize, currentEdgeSize);
			outerPane.getChildren().add(pane);

			currentEdgeSize -= edgeDecreaseStep;
		}

		return outerPane;
	}

	private static AnchorPane anchorPaneCase() {
		AnchorPane anchorPane = new AnchorPane();
		sizeOuterPane(anchorPane);


		Pane blauPane = innerRandomSizedPane(BLAU, 50, 100);
		anchorPane.getChildren().add(blauPane);
		AnchorPane.setBottomAnchor(blauPane, 20.0);
		AnchorPane.setRightAnchor(blauPane, 20.0);


		Pane rotPane = innerRandomSizedPane(ROT, 50, 100);
		anchorPane.getChildren().add(rotPane);
		AnchorPane.setTopAnchor(rotPane, 20.0);
		AnchorPane.setLeftAnchor(rotPane, 20.0);

		Pane gelbPane = innerRandomSizedPane(GELB, 50, 50);
		anchorPane.getChildren().add(gelbPane);
		AnchorPane.setTopAnchor(gelbPane, 20.0);
		AnchorPane.setLeftAnchor(gelbPane, 170.);

		return anchorPane;
	}

	private static TilePane tilePaneCase() {
		TilePane tilePane = new TilePane(Orientation.VERTICAL);

		tilePane.setPrefColumns(2);
		tilePane.setPrefRows(3);

		for (String color: ALL_COLORS) {
			tilePane.getChildren().add(innerRandomSizedPane(color, 50, 100));
		}

		return tilePane;
	}

	private static FlowPane flowPaneCase() {
		FlowPane flowPane = new FlowPane(Orientation.VERTICAL);

		for (String color: ALL_COLORS) {
			flowPane.getChildren().add(innerRandomSizedPane(color, 50, 100));
		}

		return flowPane;
	}

	private static GridPane gridPaneCase() {
		GridPane gridPane = new GridPane();

		List<Pane> allColorPanes = ALL_COLORS.stream().map(color -> innerPane(color, 50, 100))
				.collect(Collectors.toList());

		gridPane.add(allColorPanes.get(0), 0, 0);
		gridPane.add(allColorPanes.get(1), 2, 0);

		gridPane.add(allColorPanes.get(2), 0, 1);
		gridPane.add(allColorPanes.get(3), 1, 1);
		gridPane.add(allColorPanes.get(4), 2, 1);

		gridPane.add(allColorPanes.get(5), 0, 2);

		GridPane.setRowSpan(allColorPanes.get(0), 2);

		GridPane.setColumnSpan(allColorPanes.get(3), 2);
		GridPane.setColumnSpan(allColorPanes.get(4), 2);

		return gridPane;
	}

	private static Pane innerRandomSizedPane(final String hexColor, final int minSize, final int randomPart) {
		Random randomGen = new Random(System.nanoTime());
		int rotPaneRandomPrefSize = randomGen.nextInt(100) + 50;
		return innerPane(hexColor,  rotPaneRandomPrefSize, rotPaneRandomPrefSize);
	}

	private static Pane innerPane(final String hexColor, final int prefSize, final int maxSize) {
		Pane pane = innerPane(hexColor, prefSize);

		pane.setMaxWidth(prefSize);
		pane.setMaxHeight(prefSize);

		return pane;
	}

	private static Pane innerPane(final String hexColor, final int prefSize) {
		BorderPane borderPane = new BorderPane();
		borderPane.setStyle("-fx-background-color: " + hexColor);

		borderPane.setPrefWidth(prefSize);
		borderPane.setPrefHeight(prefSize);

		return borderPane;
	}

	private static void sizeOuterPane(Pane pane) {
		pane.setPrefHeight(OUTER_PANE_DEFAULT_SIZE);
		pane.setPrefWidth(OUTER_PANE_DEFAULT_SIZE);
	}

}
