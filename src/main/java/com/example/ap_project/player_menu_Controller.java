package com.example.ap_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class player_menu_Controller {

    @FXML
    private ImageView group;

    @FXML
    private TextField name1;

    @FXML
    private TextField name2;

    @FXML
    public void switch1(ActionEvent e) throws IOException {
        String s1 = name1.getText();
        String s2 = name2.getText();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 615, 615);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Game_Controller controller = fxmlLoader.getController();
        controller.setPlayer1(s1);
        controller.setPlayer2(s2);
        stage.setScene(scene);
        controller.setup_game();
        stage.show();
    }

    @FXML
    void switch3(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home_page.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 615, 615);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
