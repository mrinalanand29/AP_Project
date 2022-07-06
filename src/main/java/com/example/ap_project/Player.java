package com.example.ap_project;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.io.FileNotFoundException;

public class Player {
    private String name;
    private ImageView token;
    private int[] current_position = new int[2];

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int[] getCurrent_position() {
        return current_position;
    }

    public void setCurrent_position(int[] current_position) {
        for(int i =0; i < 2; i++){
            this.current_position[i] = current_position[i];
        }
    }

    public Player(String name, int x_token, int y_token){
        this.name = name;
        this.current_position = new int[]{-1, -1};
        token = new ImageView();
        token.setX(x_token);
        token.setY(y_token);
        token.setVisible(true);
        token.toFront();
    }


    public void update_position(int x, int y){
        current_position[0] = current_position[0] + x;
        current_position[1] = current_position[1] + y;
    }

    public void setToken(ImageView token) {
        this.token = token;
    }

    public ImageView getToken() {
        return token;
    }
}
