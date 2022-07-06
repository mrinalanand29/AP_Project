package com.example.ap_project;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game_Controller {
    private Scene scene;
    private Stage stage;
    private Die d1;
    private static int turn = 0;
    public volatile int x_shift, y_shift;
    private Player player1, player2;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private int count;



    @FXML
    private ImageView arrow;

    @FXML
    private ImageView p1;

    @FXML
    private ImageView p2;

    @FXML
    private TextField play1;

    @FXML
    private TextField play2;

    @FXML
    private Button dice_roll_button;

    @FXML
    private Group pause;

    @FXML
    private Group win_menu;

    @FXML
    private ImageView token1;

    @FXML
    private ImageView token2;

    @FXML
    private TextField dice_roller1 = new TextField();

    @FXML
    private TextField dice_roller2 = new TextField();

    public void first_move(Player p){
        TranslateTransition trans = new TranslateTransition();
        trans.setNode(p.getToken());
        trans.setDuration(Duration.millis(500));
        if(p == player2) trans.setByX(x_shift * 55);
        else trans.setByX(x_shift * 55);
        trans.setByY(y_shift * (-53));
        trans.play();
    }

    public void move0(Player p){
        TranslateTransition trans = new TranslateTransition();
        trans.setNode(p.getToken());
        trans.setDuration(Duration.millis(50));
        if(p == player2) trans.setByX(x_shift * 46);
        else trans.setByX(x_shift * 48);
        trans.setByY(y_shift * (-49));
        trans.play();
    }

    public void move1(Player p){
        TranslateTransition trans = new TranslateTransition();
        trans.setNode(p.getToken());
        trans.setDuration(Duration.millis(1000));
        if(p == player2) trans.setByX(x_shift * 46);
        else trans.setByX(x_shift * 48);
        trans.setByY(y_shift * (-49));
        trans.play();
    }

    @FXML
    void roll_die(Player p) throws FileNotFoundException, InterruptedException {

        Thread thr = new Thread() {
            @Override
            public void run() {

                arrow.setVisible(false);
                try {
                    count = d1.roll();
                } catch (FileNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(count);
                arrow.setVisible(true);
        //x diff = 52, y diff = -53

                int finalCount = count;
                Thread t=new Thread(){
                    public void run(){

        if(finalCount == 1 && p.getCurrent_position()[1] == -1){
                x_shift = 1;
                y_shift = 1;
                first_move(p);
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            p.setCurrent_position(new int[]{0, 0});

        }

        else if(p.getCurrent_position()[1] != -1) {
            if(p.getCurrent_position()[1] < 9 || (p.getCurrent_position()[1] == 9 && p.getCurrent_position()[0] - finalCount >= 0)){
                for (int i = 0; i < finalCount; i++) {
                    if (p.getCurrent_position()[1] % 2 == 0) {
                        x_shift = 1;
                        if (p.getCurrent_position()[0] == 9) {
                            x_shift = 0;
                            y_shift = 1;
                        } else
                            y_shift = 0;
                    } else if (p.getCurrent_position()[1] % 2 != 0) {
                        x_shift = -1;
                        if (p.getCurrent_position()[0] == 0) {
                            x_shift = 0;
                            y_shift = 1;
                        } else
                            y_shift = 0;
                    } else {
                        x_shift = 0;
                        y_shift = 0;
                    }
                    p.update_position(x_shift, y_shift);
                    move0(p);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(p.getCurrent_position()[0] == 0 && p.getCurrent_position()[1] == 9){
                        if(p == player2) {
                            System.out.println("pLayer 2 wins.\n");
                            play1.setText(player2.getName());
                            play2.setText(player1.getName());
                        }
                        else {
                            System.out.println("Player 1 wins.\n");
                            play1.setText(player1.getName());
                            play2.setText(player2.getName());
                        }

                        scene = token1.getScene();
                        for (Node n: ((AnchorPane)scene.getRoot()).getChildren()){
                            n.setOpacity(0.55);
                        }
                        win_menu.setOpacity(1);
                        TranslateTransition t2 = new TranslateTransition();
                        t2.setNode(win_menu);
                        //t2.setFromY(27);
                        t2.setByY(500);
                        t2.setDuration(Duration.millis(500));
                        t2.play();
                    }


                }
                for (Snake s : snakes) {
                    if (p.getCurrent_position()[0] == s.getPath().get(0).getKey() && p.getCurrent_position()[1] == s.getPath().get(0).getValue()) {
                        for (int i = 1; i < s.getPath().size(); i++) {
                            int x_coordinate = s.getPath().get(i).getKey(), y_coordinate = s.getPath().get(i).getValue();
                            p.setCurrent_position(new int[]{x_coordinate, y_coordinate});
                            x_shift = x_coordinate - s.getPath().get(i - 1).getKey();
                            y_shift = y_coordinate - s.getPath().get(i - 1).getValue();
                            move0(p);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }

                for (Ladder l : ladders) {
                    if (p.getCurrent_position()[0] == l.getStart().getKey() && p.getCurrent_position()[1] == l.getStart().getValue()) {
                        int x_coordinate = l.getEnd().getKey(), y_coordinate = l.getEnd().getValue();
                        p.setCurrent_position(new int[]{x_coordinate, y_coordinate});
                        x_shift = x_coordinate - l.getStart().getKey();
                        y_shift = y_coordinate - l.getStart().getValue();
                        move1(p);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
                        }

                        if(p == player2) {
                            try {
                                p1.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\player1_light.png")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            try {
                                p2.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\player2_dark.png")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            dice_roller1.setVisible(true);
                            dice_roller2.setVisible(false);
                        }
                        else{
                            try {
                                p1.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\player1_dark.png")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            try {
                                p2.setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\player2_light.png")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            dice_roller2.setVisible(true);
                            dice_roller1.setVisible(false);
                        }
                    }

                };
                t.start();


            }
        };

        thr.start();

    }

    @FXML
    public void pause_function(ActionEvent e){
        scene = token1.getScene();
        for (Node n: ((AnchorPane)scene.getRoot()).getChildren()){
            n.setOpacity(0.55);
        }
        pause.setOpacity(1);
        TranslateTransition trans = new TranslateTransition();
        trans.setNode(pause);
        trans.setFromY(27);
        trans.setByY(-500);
        trans.setDuration(Duration.millis(500));
        trans.play();
    }

    public void return_to_game(ActionEvent e){
        scene = token1.getScene();
        for (Node n: ((AnchorPane)scene.getRoot()).getChildren()){
            n.setOpacity(1);
        }
        //pause.setOpacity();
        TranslateTransition trans = new TranslateTransition();
        trans.setNode(pause);
        trans.setFromY(-500);
        trans.setByY(500);
        trans.setDuration(Duration.millis(500));
        trans.play();
    }

    public void switch4(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home_page.fxml"));
        Parent root = fxmlLoader.load();
        scene = new Scene(root, 615, 615);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void switch5(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home_page.fxml"));
        Parent root = fxmlLoader.load();
        scene = new Scene(root, 615, 615);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void setPlayer1(String name){
        dice_roller1.setText(name);
    }

    public void setPlayer2(String name){
        dice_roller2.setText(name);
    }

    @FXML
    public void image_as_button() {
            scene = token1.getScene();
            d1.getFace().setOnMouseClicked(mouseEvent -> {
                try {
                    if (turn % 2 == 0) {
                        roll_die(player1);

                           }
                    else {
                        roll_die(player2);

                    }
                    Thread t = new Thread(){
                        public void run(){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    t.start();
                    turn++;

                } catch (FileNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

    @FXML
    public void setup_game() throws FileNotFoundException {
        scene = token1.getScene();
        d1 = new Die(272, 560);
        ((AnchorPane)scene.getRoot()).getChildren().add(d1.getFace());

        TranslateTransition trans = new TranslateTransition();
        trans.setDuration(Duration.seconds(1));
        trans.setNode(arrow);
        trans.setToX(0);
        trans.setToY(-10);

        trans.setAutoReverse(true);
        trans.setCycleCount(TranslateTransition.INDEFINITE);
        trans.play();



        player1 = new Player(dice_roller1.getText(), -1, -1);
        player1.setToken(token1);
        player1.getToken().setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\1714131.png")));
        player2 = new Player(dice_roller2.getText(), -1, -1);
        player2.setToken(token2);
        player2.getToken().setImage(new Image(new FileInputStream("C:\\Users\\Mrinal\\IdeaProjects\\AP_Project\\src\\main\\resources\\com\\example\\ap_project\\green.png")));
        //ArrayList<Pair<Integer, Integer>> snake_path = new ArrayList<>();
        snakes = new ArrayList<>();
        ladders = new ArrayList<>();


        ArrayList<Pair<Integer, Integer>> sp1 = new ArrayList<>();
        sp1.add(new Pair<>(1, 9));
        sp1.add((new Pair<>(2,9)));
        sp1.add(new Pair<>(2, 8));
        sp1.add(new Pair<>(1, 8));
        sp1.add(new Pair<>(1, 7));
        sp1.add(new Pair<>(2, 7));
        Snake s1 = new Snake(sp1);
        snakes.add(s1);

        ArrayList<Pair<Integer, Integer>> sp2 = new ArrayList<>();
        sp2.add(new Pair<>(4, 9));
        sp2.add(new Pair<>(4, 8));
        sp2.add(new Pair<>(4, 7));
        sp2.add(new Pair<>(4, 6));
        Snake s2 = new Snake(sp2);
        snakes.add(s2);

        ArrayList<Pair<Integer, Integer>> sp3 = new ArrayList<>();
        sp3.add(new Pair<>(6, 9));
        sp3.add(new Pair<>(7, 9));
        sp3.add(new Pair<>(7, 8));
        sp3.add(new Pair<>(6, 8));
        sp3.add(new Pair<>(6, 7));
        sp3.add(new Pair<>(7, 7));
        sp3.add(new Pair<>(7, 6));
        sp3.add(new Pair<>(6, 6));
        sp3.add(new Pair<>(6, 5));
        sp3.add(new Pair<>(7, 5));
        Snake s3 = new Snake(sp3);
        snakes.add(s3);

        ArrayList<Pair<Integer, Integer>> sp4 = new ArrayList<>();
        sp4.add(new Pair<>(9, 8));
        sp4.add(new Pair<>(9, 7));
        sp4.add(new Pair<>(9, 6));
        sp4.add(new Pair<>(9, 5));
        sp4.add(new Pair<>(9, 4));
        Snake s4 = new Snake(sp4);
        snakes.add(s4);

        ArrayList<Pair<Integer, Integer>> sp5 = new ArrayList<>();
        sp5.add(new Pair<>(0, 8));
        sp5.add(new Pair<>(1, 8));
        sp5.add(new Pair<>(1, 7));
        sp5.add(new Pair<>(0, 7));
        sp5.add(new Pair<>(0, 6));
        sp5.add(new Pair<>(1, 6));
        sp5.add(new Pair<>(2, 6));
        Snake s5 = new Snake(sp5);
        snakes.add(s5);

        ArrayList<Pair<Integer, Integer>> sp6 = new ArrayList<>();
        sp6.add(new Pair<>(5, 7));
        sp6.add(new Pair<>(6, 7));
        sp6.add(new Pair<>(6, 6));
        sp6.add(new Pair<>(5, 6));
        sp6.add(new Pair<>(5, 5));
        sp6.add(new Pair<>(6, 5));
        Snake s6 = new Snake(sp6);
        snakes.add(s6);

        ArrayList<Pair<Integer, Integer>> sp7 = new ArrayList<>();
        sp7.add(new Pair<>(2, 4));
        sp7.add(new Pair<>(1, 4));
        sp7.add(new Pair<>(1, 3));
        sp7.add(new Pair<>(2, 3));
        sp7.add(new Pair<>(2, 2));
        sp7.add(new Pair<>(1, 2));
        Snake s7 = new Snake(sp7);
        snakes.add(s7);

        ArrayList<Pair<Integer, Integer>> sp8 = new ArrayList<>();
        sp8.add(new Pair<>(4, 5));
        sp8.add(new Pair<>(3, 5));
        sp8.add(new Pair<>(3, 4));
        sp8.add(new Pair<>(4, 3));
        sp8.add(new Pair<>(3, 3));
        sp8.add(new Pair<>(2, 2));
        sp8.add(new Pair<>(3, 2));
        sp8.add(new Pair<>(3, 1));
        sp8.add(new Pair<>(2, 1));
        Snake s8 = new Snake(sp8);
        snakes.add(s8);

        ArrayList<Pair<Integer, Integer>> sp9 = new ArrayList<>();
        sp9.add(new Pair<>(4, 3));
        sp9.add(new Pair<>(5, 3));
        sp9.add(new Pair<>(5, 2));
        sp9.add(new Pair<>(4, 2));
        sp9.add(new Pair<>(5, 1));
        sp9.add(new Pair<>(6, 1));
        Snake s9 = new Snake(sp9);
        snakes.add(s9);

        ArrayList<Pair<Integer,Integer>> sp10 = new ArrayList<>();
        sp10.add(new Pair<>(9, 1));
        sp10.add(new Pair<>(8, 1));
        sp10.add(new Pair<>(9, 0));
        sp10.add(new Pair<>(8, 0));
        Snake s10 = new Snake(sp10);
        snakes.add(s10);


        Ladder l1 = new Ladder(new Pair<>(3, 0), new Pair<>(4, 2));
        Ladder l2 = new Ladder(new Pair<>(7, 0), new Pair<>(9, 3));
        Ladder l3 = new Ladder(new Pair<>(7, 2), new Pair<>(5, 4));
        Ladder l4 = new Ladder(new Pair<>(0, 2), new Pair<>(0, 5));
        Ladder l5 = new Ladder(new Pair<>(1, 4), new Pair<>(0, 7));
        Ladder l6 = new Ladder(new Pair<>(2, 5), new Pair<>(3, 7));
        Ladder l7 = new Ladder(new Pair<>(8, 5), new Pair<>(7, 6));
        Ladder l8 = new Ladder(new Pair<>(8, 6), new Pair<>(7,9));
        Ladder l9 = new Ladder(new Pair<>(3, 8), new Pair<>(2, 9));
        Ladder l10 = new Ladder(new Pair<>(8, 3), new Pair<>(7, 4));
        ladders.add(l1);
        ladders.add(l2);
        ladders.add(l3);
        ladders.add(l4);
        ladders.add(l5);
        ladders.add(l6);
        ladders.add(l7);
        ladders.add(l8);
        ladders.add(l9);
        ladders.add(l10);

    }
}
