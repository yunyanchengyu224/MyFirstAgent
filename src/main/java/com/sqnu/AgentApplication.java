package com.sqnu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.HttpURLConnection;
import java.net.URI;

public class AgentApplication extends Application {
    private ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        springContext = SpringApplication.run(ServerApplication.class);
        System.out.println("AI面试官桌面应用已启动");

        WebView webView = new WebView();
        webView.setContextMenuEnabled(false);
        String serverUrl = "http://localhost:8080";
        if (!isServerReady(serverUrl)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("启动失败");
            alert.setHeaderText("本地服务未能启动");
            alert.setContentText("请检查 8080 端口是否被占用，然后重新启动应用。\n" + serverUrl);
            alert.showAndWait();
            stop();
            return;
        }
        webView.getEngine().load(serverUrl);

        stage.setTitle("AI智能模拟面试系统");
        stage.setMinWidth(720);
        stage.setMinHeight(560);
        stage.setScene(new Scene(webView, 960, 720));
        stage.show();
    }

    private boolean isServerReady(String serverUrl) {
        for (int attempt = 0; attempt < 20; attempt++) {
            try {
                HttpURLConnection connection = (HttpURLConnection) URI.create(serverUrl + "/api/init").toURL().openConnection();
                connection.setConnectTimeout(500);
                connection.setReadTimeout(500);
                connection.setRequestMethod("GET");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    return true;
                }
            } catch (Exception ignored) {
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }

    @Override
    public void stop() {
        if (springContext != null) {
            springContext.close();
        }
        Platform.exit();
        System.exit(0);
    }
}
