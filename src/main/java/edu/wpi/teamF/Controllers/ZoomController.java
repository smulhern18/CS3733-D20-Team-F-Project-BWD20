package edu.wpi.teamF.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;


import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class ZoomController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView imaeg;


    public void zoom(ScrollEvent event) {
        double zoomFactor = 1.5;
        if (event.getDeltaY() <= 0) {
            // zoom out
            zoomFactor = 1 / zoomFactor;
        }
        scrollPane.getViewportBounds()

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


