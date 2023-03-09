package com.example.goodshooter;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import static java.lang.Math.pow;

public class MainController {
    @FXML
    private Group arrow;
    @FXML
    private Label shoots;
    @FXML
    private Text score;
    @FXML
    private Circle c1,c2;
    @FXML
    private Button bStop,bStart,bShoot;
    Thread thread;
    private double border_y = 400;
    private double border_x = 550;

    private boolean inGame = false;

    private boolean inAction = false;
    @FXML
    protected void Stop() {
        inGame=false;
        bStart.setDisable(false);
        bStop.setDisable(true);
        bShoot.setDisable(true);
        c1.setLayoutY(200);
        c2.setLayoutY(200);
        thread.interrupt();
        thread=null;
    }

    protected void ArrowStartPos(){
        inAction=false;
        bShoot.setDisable(false);
        arrow.setVisible(true);
        arrow.setLayoutX(100);
    }
    @FXML
    protected void Shoot() {
        inAction = true;
        bShoot.setDisable(true);
        arrow.setVisible(true);
        shoots.setText(String.valueOf(Integer.parseInt(shoots.getText())+1));
    }
    @FXML
    protected void runCircle(){
        score.setText("0");
        shoots.setText("0");
        ArrowStartPos();
        arrow.setVisible(true);
        bStop.setDisable(false);
        bStart.setDisable(true);
        thread = new Thread(()->
        {

            double speedC1 = 5;
            double speedC2 = 10;
            double speedArrow = 15.0;

            inGame = true;
            double c=15;
            while (inGame)
            {
                double y1 = c1.getLayoutY();double x1 = c1.getLayoutX();
                double y2 = c2.getLayoutY();double x2 = c2.getLayoutX();
                y1+=speedC1;
                c1.setLayoutY(y1);

                y2+=speedC2;
                c2.setLayoutY(y2);

                if (y1 >= border_y - c1.getRadius() || y1 <=  c1.getRadius() ) {
                    speedC1*= -1;

                }
                if (y2 >= border_y - c2.getRadius() || y2 <=  c2.getRadius() ) {
                    speedC2*=-1;
                }
                if(inAction){
                    double arrowx = arrow.getLayoutX();
                    double arrowy = arrow.getLayoutY();

                    arrowx+=speedArrow;
                    arrow.setLayoutX(arrowx);

                    if(arrowx >= border_x){
                        ArrowStartPos();
                    }
                    else if(Math.sqrt(pow((arrowx+c-x1),2)+pow((arrowy-y1),2)) <= c1.getRadius()){
                        score.setText(String.valueOf(Integer.parseInt(score.getText())+1));
                        ArrowStartPos();
                    }
                    else if(Math.sqrt(pow((arrowx-x2),2)+pow((arrowy-y2),2)) <= c2.getRadius()){
                        score.setText(String.valueOf(Integer.parseInt(score.getText())+2));
                        ArrowStartPos();
                    }
                }
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    inGame = false;
                }
            }
        }

        );thread.start();
    }

}
