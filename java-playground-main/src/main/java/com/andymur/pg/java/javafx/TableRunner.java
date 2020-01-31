package com.andymur.pg.java.javafx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.WeakEventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableRunner extends Application {

	private TableView<City> cityTable;
	private ArrayList<String> headerValues = new ArrayList<>(Arrays.asList("Name", "Population", "Rating"));

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		Parent root = initPane();
		primaryStage.setTitle("");
		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(getClass().getResource("/table-runner.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Pane initPane() {
		Pane vBox = new VBox();
		cityTable = new TableView<>();
		List<TableColumn<City, String>> columns = new ArrayList<>();

		for (final String headerValue: headerValues) {
			TableColumn<City, String> column = new TableColumn<>(headerValue);
			column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFieldValue(headerValue)));

			if ("Rating".equals(headerValue)) {
				final RatingCell ratingCell = new RatingCell();
				ratingCell.setOnMouseClicked(new WeakEventHandler<>(event -> onCellClick(cityTable)));
				column.setCellFactory(ratingColumn -> ratingCell);
				/*column.setCellFactory(tc -> {
					TableCell<City, String> cell = new TableCell<>();
					Text text = new Text();
					cell.setGraphic(text);
					cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
					text.wrappingWidthProperty().bind(column.widthProperty());
					text.textProperty().bind(cell.itemProperty());
					text.getStyleClass().add("table-cell-text");
					return cell ;
				});*/
			}

			columns.add(column);
		}


		cityTable.getColumns().addAll(columns);
		cityTable.setItems(FXCollections.observableArrayList(new City("Paris", 2000000),
				new City("Saint Petersburg", 5000000)));
		vBox.getChildren().addAll(cityTable);
		vBox.requestLayout();
		cityTable.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(final ObservableValue<? extends Bounds> observable, final Bounds oldValue, final Bounds newValue) {
				System.out.println("HAHAHAHA" + newValue.getWidth());
			}
		});
		return vBox;
	}

	private Object onCellClick(final TableView<City> cityTable) {
		System.out.println("" + cityTable.layoutBoundsProperty().get());
		return "";
	}

	// our custom table column cell
	public static class RatingCell extends TableCell<City, String> {

		@Override
		protected void updateItem(final String item, final boolean empty) {
			super.updateItem(item, empty);
			if (item != null && this.getTableRow() != null) {
				setText(item);
				int ratingScore = ((City) this.getTableRow().getItem()).getRatingScore();
				getStyleClass().add("rating");
				if (ratingScore >= 5) {
					getStyleClass().add("rating-high");
				} else if (ratingScore > 3) {
					getStyleClass().add("rating-medium");
				} else {
					getStyleClass().add("rating-low");
				}
			} else {
				setText("");
				setStyle("");
			}
		}
	}

	// our dto
	public static class City {
		private String name;
		private int population;

		public City(final String name, final int population) {
			this.name = name;
			this.population = population;
		}

		public String getName() {
			return name;
		}

		public void setName(final String name) {
			this.name = name;
		}

		public String getPopulation() {
			return String.valueOf(population);
		}

		public void setPopulation(final int population) {
			this.population = population;
		}

		public String getRating() {
			if (name.equals("Paris")) {
				return "*****";
			} else if (name.equals("Saint Petersburg")) {
				return "****";
			} else {
				return "***";
			}
		}

		public int getRatingScore() {
			return getRating().length();
		}

		public String getFieldValue(final String fieldValue) {
			switch (fieldValue.toLowerCase().trim()) {
				case "rating":
					return getRating();
				case "population":
					return getPopulation();
				case "name":
					return getName();
			}
			throw new IllegalArgumentException("no such field");
		}
	}
}
