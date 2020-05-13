package edu.wpi.teamF;

import edu.wpi.teamF.Controllers.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  private static SceneController sceneController;
  public static Stage pStage;

  public App() throws Exception {}

  public static SceneController getSceneController() {
    return sceneController;
  }

  private LoginController loginController = new LoginController();
  private DataManipulatorController dataManipulatorController = new DataManipulatorController();
  private HelpController helpController = new HelpController();
  private MainMenuController mainMenuController = new MainMenuController();
  private MenuBarController menuBarController = new MenuBarController();
  private PathfinderController pathfinderController = new PathfinderController();
  private RegisterController registerController = new RegisterController();
  private ServiceRequestMainController serviceRequestController =
      new ServiceRequestMainController();
  private AccountsController accountsController = new AccountsController();
  private ComputerServiceController computerController = new ComputerServiceController();
  private MedicineDeliveryController medicineController = new MedicineDeliveryController();
  private MaintenanceRequestController maintenanceRequestController =
      new MaintenanceRequestController();
  private DataViewController dataViewController = new DataViewController();
  private TranslatorController translatorController = new TranslatorController();
  private ContactUsController contactUsController = new ContactUsController();

  @Override
  public void init() {}

  @Override
  public void start(Stage primaryStage) throws IOException {
    pStage = primaryStage;

    Scene primaryScene = new Scene(new AnchorPane());
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setControllerFactory(
        controllerClass -> {
          if (controllerClass.equals(LoginController.class)) {
            return loginController;
          } else if (controllerClass.equals(DataManipulatorController.class)) {
            return dataManipulatorController;
          } else if (controllerClass.equals(HelpController.class)) {
            return helpController;
          } else if (controllerClass.equals(MainMenuController.class)) {
            return mainMenuController;
          } else if (controllerClass.equals(MenuBarController.class)) {
            return menuBarController;
          } else if (controllerClass.equals(PathfinderController.class)) {
            return pathfinderController;
          } else if (controllerClass.equals(RegisterController.class)) {
            return registerController;
          } else if (controllerClass.equals(ServiceRequestMainController.class)) {
            return serviceRequestController;
          } else if (controllerClass.equals(AccountsController.class)) {
            return accountsController;
          } else if (controllerClass.equals(ComputerServiceController.class)) {
            return computerController;
          } else if (controllerClass.equals(MedicineDeliveryController.class)) {
            return medicineController;
          } else if (controllerClass.equals(MaintenanceRequestController.class)) {
            return maintenanceRequestController;
          } else if (controllerClass.equals(DataViewController.class)) {
            return dataViewController;
          } else if (controllerClass.equals(TranslatorController.class)) {
            return translatorController;
          } else if (controllerClass.equals(ContactUsController.class)) {
            return contactUsController;
          }
          return null;
        });
    sceneController = new SceneController(fxmlLoader, primaryStage, primaryScene);
    Parent root = fxmlLoader.load(getClass().getResource("Views/ServiceRequestMain.fxml"));
    primaryScene.setRoot(root);
    primaryStage.setScene(primaryScene);
    // primaryStage.setAlwaysOnTop(true);
    primaryStage.show();
    primaryStage.setMaximized(true);

    //    primaryStage.setFullScreen(true);
  }

  public static Stage getPrimaryStage() {
    return pStage;
  }

  public void reset() throws Exception {
    pathfinderController = new PathfinderController();
    menuBarController.logout(new ActionEvent());
  }

  @Override
  public void stop() {}
}
