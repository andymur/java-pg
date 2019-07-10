package com.andymur.pg.java.javafx;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.sun.javafx.scene.control.skin.TabPaneSkin;

public class ResizableTabPaneRunner extends Application {
	Stage primaryStage;
	private Node tabContent;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		Scene scene = new Scene(buildRoot());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Parent buildRoot() {
		VBox root = new VBox();

		root.setPrefHeight(640);
		root.setPrefWidth(500);

		ResizableHorizontalTabPane tabPane = new ResizableHorizontalTabPane();

		// root.getChildren().add(filledGridPane());
		root.getChildren().add(tabPane);

		tabPane.getTabs().add(createTabItem("First Tab"));

		return root;
	}

	private Tab createTabItem(final String tabCaption) {
		Tab tabItem = new Tab();
		tabItem.setClosable(false);
		tabItem.setText(tabCaption);
		tabItem.setContent(getTabContent());
		return tabItem;
	}

	private Node getTabContent() {
		return filledGridPane();
	}

	private GridPane filledGridPane() {
		return fillGridPane(createGridPane());
	}

	private GridPane fillGridPane(GridPane gridPane) {
		int rowIndex = 0;

		for (int i = 0; i < 10; i++) {
			Label label = new Label("My Label #" + i);
			gridPane.add(label, 1, rowIndex++);
		}

		return gridPane;
	}

	private GridPane createGridPane() {

		int nrFixedColumns = 1;

		GridPane mainPart = new GridPane();
		mainPart.setPadding(new Insets(0, 0, 10, 0));
		mainPart.setAlignment(Pos.TOP_LEFT);
		ColumnConstraints leftColumnConstraints = new ColumnConstraints();
		leftColumnConstraints.setHgrow(Priority.ALWAYS);
		leftColumnConstraints.setHalignment(HPos.RIGHT);
		leftColumnConstraints.setFillWidth(true);
		leftColumnConstraints.setMaxWidth(Double.MAX_VALUE);
		leftColumnConstraints.setPrefWidth(42);
		mainPart.getColumnConstraints().add(leftColumnConstraints);

		List<ColumnConstraints> fixedColumns = IntStream.range(0, nrFixedColumns)
				.mapToObj((nr) -> createFixedColumn())
				.collect(Collectors.toList());
		mainPart.getColumnConstraints().addAll(fixedColumns);

		ColumnConstraints rightColumnConstraints = new ColumnConstraints();
		rightColumnConstraints.setHgrow(Priority.ALWAYS);
		rightColumnConstraints.setHalignment(HPos.LEFT);
		rightColumnConstraints.setFillWidth(true);
		rightColumnConstraints.setMaxWidth(Double.MAX_VALUE);
		rightColumnConstraints.setPrefWidth(42);

		mainPart.getColumnConstraints().add(rightColumnConstraints);
		return mainPart;
	}

	protected static ColumnConstraints createFixedColumn() {
		ColumnConstraints c = new ColumnConstraints();
		c.setHgrow(Priority.NEVER);
		c.setHalignment(HPos.LEFT);
		c.setFillWidth(false);
		return c;
	}


	public class ResizableHorizontalTabPane extends TabPane {

		private static final double DEFAULT_TAB_PANE_HEADER_HEIGHT = 26;

		private final double tabPaneHeaderHeight;

		public ResizableHorizontalTabPane() {
			this(DEFAULT_TAB_PANE_HEADER_HEIGHT);
		}

		public ResizableHorizontalTabPane(final double tabPaneHeaderHeight) {
			this.tabPaneHeaderHeight = tabPaneHeaderHeight;
			// binds the tabpane's preferred height to that of its currently selected tab
			configurePrefHeight();
		}

		private void configurePrefHeight() {
			final ReadOnlyObjectProperty<Tab> selectedItemProperty = selectionModelProperty().get().selectedItemProperty();
			final DoubleBinding currentTabPrefHeightBinding = Bindings.createDoubleBinding(() -> {

				double currentTabPrefHeight = 0d;
				final TabPaneSkin skin = (TabPaneSkin) getSkin();
				if (skin != null) {
					final StackPane tabContent = skin.getSelectedTabContentRegion();
					if (tabContent != null) {
						currentTabPrefHeight = Math.max(
								snapSize(tabContent.prefHeight(-1)),
								snapSize(tabContent.minHeight(-1)));
					}
				}
				System.out.println("CURRENT TAB PREF HEIGHT: " + currentTabPrefHeight);
				return tabPaneHeaderHeight + currentTabPrefHeight;

			} , selectedItemProperty);

			prefHeightProperty().bind(currentTabPrefHeightBinding);
			minHeightProperty().bind(prefHeightProperty());
			maxHeightProperty().bind(prefHeightProperty());
		}
	}
}
