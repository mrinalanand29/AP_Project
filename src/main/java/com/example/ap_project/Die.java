package com.example.ap_project;

import javafx.scene.control.Button;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class Die {

        private int value;

        private ImageView face;

        public Die(double x, double y) throws FileNotFoundException {
            face = new ImageView();
            face.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\d5.png")));
            face.setX(x);
            face.setY(y);
            face.setPreserveRatio(true);
            face.setFitHeight(36);
            face.setFitWidth(36);
        }

        public int roll() throws FileNotFoundException, InterruptedException {
            face.setDisable(true);
            Thread thread = new Thread(){
                public void run(){
                    try{
                        for (int i = 0; i < 3; i++) {

                            face.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\d1.png")));
                            Thread.sleep(50);
                            face.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\d2.png")));
                            Thread.sleep(50);
                            face.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\d3.png")));
                            Thread.sleep(50);
                            face.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\d4.png")));
                            Thread.sleep(50);
                            face.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\d5.png")));
                            Thread.sleep(50);
                            face.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\d6.png")));
                            Thread.sleep(50);

                        }

                        //Thread.sleep(1000);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            Random random = new Random();
            int x = random.nextInt(6);
            Thread thread1 = new Thread(){
                public void run(){

                    try {
                        Thread.sleep(1000);
                        if(x == 0) {
                            face.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\d6.png")));
                            setValue(6);
                        }
                        else if (x== 1){
                            face.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\d1.png")));
                            setValue(x);
                        }
                        else if (x== 2){
                            face.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\d2.png")));
                            setValue(x);
                        }
                        else if (x== 3){
                            face.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\d3.png")));
                            setValue(x);
                        }
                        else if (x== 4){
                            face.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\d4.png")));
                            setValue(x);
                        }
                        else{
                            face.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\d5.png")));
                            setValue(x);
                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

            };
            //Thread.sleep(1000);
            thread1.start();

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            face.setDisable(false);
        return getValue();
    }

        public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ImageView getFace() {
        return face;
    }
}
