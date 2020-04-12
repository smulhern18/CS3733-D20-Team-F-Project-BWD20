package edu.wpi.teamF.controllers;


import java.io.IOException;

public class DownloadDataController extends SceneController {

    //Scene Controllers

    SceneController sceneController = new SceneController();

    public void displayButton(javafx.event.ActionEvent actionEvent) throws IOException {
        sceneController.switchScene("DisplayData");
    }

    public void mainMenuButton(javafx.event.ActionEvent actionEvent) throws IOException {
        sceneController.switchScene("MainMenu");
    }

    public void modifyButton(javafx.event.ActionEvent actionEvent) throws IOException {
        sceneController.switchScene("ModifyValues");
    }

    public void pathfinderButton(javafx.event.ActionEvent actionEvent) throws IOException {
        sceneController.switchScene("Pathfinder");
    }

}

