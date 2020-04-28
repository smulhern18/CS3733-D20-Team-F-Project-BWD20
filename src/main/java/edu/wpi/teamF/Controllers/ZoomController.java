package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.Controllers.UISettings.UISetting;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ZoomController implements Initializable {

  private Group zoomNode;

  private double scaleValue;
  private double zoomIntensity = 0.02;

  @FXML private ScrollPane scrollPane;
  @FXML private StackPane mapPane;

  @FXML private ImageView mapImage;
  @FXML private Label testLabel;

  UISetting uiSetting = new UISetting();

  public ZoomController() throws Exception {}

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // zoomPane = new ZoomableScrollPane(scrollPane);
    //    zoomNode = new Group(mapPane);
    //    scrollPane.setContent(outerNode(zoomNode));
    //
    //    scrollPane.setPannable(true);
    //    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    //    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    //    scrollPane.setFitToHeight(true); // center
    //    scrollPane.setFitToWidth(true); // center
    //    scaleValue = 1;
    //    updateScale();
    uiSetting.makeZoomable(scrollPane, mapPane, 1);
  }

  private Node outerNode(Node node) {
    Node outerNode = centeredNode(node);
    outerNode.setOnScroll(
        e -> {
          e.consume();
          onScroll(e.getTextDeltaY(), new Point2D(e.getX(), e.getY()));
          testLabel.setText(
              "H: "
                  + scrollPane.getHeight()
                  + ""
                  + "W: "
                  + scrollPane.getWidth()
                  + "Scale: "
                  + mapPane.getScaleX());
        });
    return outerNode;
  }

  private Node centeredNode(Node node) {
    VBox vBox = new VBox(node);
    vBox.setAlignment(Pos.CENTER);
    return vBox;
  }

  private void updateScale() {
    mapPane.setScaleX(scaleValue);
    mapPane.setScaleY(scaleValue);
  }

  private void onScroll(double wheelDelta, Point2D mousePoint) {
    double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

    Bounds innerBounds = zoomNode.getLayoutBounds();
    Bounds viewportBounds = scrollPane.getViewportBounds();
    System.out.println(
        innerBounds.getWidth() + " " + viewportBounds.getWidth() + " " + scrollPane.getHvalue());

    // calculate pixel offsets from [0, 1] range
    double valX = scrollPane.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
    double valY = scrollPane.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

    scaleValue = Math.max(1, scaleValue * zoomFactor);
    updateScale();
    scrollPane.layout(); // refresh ScrollPane scroll positions & target bounds

    // convert target coordinates to zoomTarget coordinates
    Point2D posInZoomTarget = mapPane.parentToLocal(zoomNode.parentToLocal(mousePoint));

    // calculate adjustment of scroll position (pixels)
    Point2D adjustment =
        mapPane
            .getLocalToParentTransform()
            .deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

    // convert back to [0, 1] range
    // (too large/small values are automatically corrected by ScrollPane)
    Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
    scrollPane.setHvalue(
        (valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
    scrollPane.setVvalue(
        (valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
  }
}
