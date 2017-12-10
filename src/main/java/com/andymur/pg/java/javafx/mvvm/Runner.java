package com.andymur.pg.java.javafx.mvvm;

import com.andymur.pg.java.javafx.mvvm.domain.Order;
import com.andymur.pg.java.javafx.mvvm.general.OrderModel;
import com.andymur.pg.java.javafx.mvvm.general.OrderView;
import com.andymur.pg.java.javafx.mvvm.general.OrderViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by andymur on 12/10/17.
 */
public class Runner extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private OrderView orderView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("My new brand app");
        this.primaryStage = primaryStage;

        initRootLayout();
        showOverview();
        OrderModel orderModel = new OrderModel();
        orderView = new OrderView(new OrderViewModel(orderModel.orders()));
        orderView.addOrder(Order.OrderType.ENTERTAINMENT, 100);
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
        loader.setLocation(Paths.get("src/main/resources/view/" + resource + ".fxml").toUri().toURL());
        return loader;
    }
}
