package edu.wpi.teamF.Controllers.UISettings;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Zoom {

  private double maxScaleFactor;

  private Group zoomNode;

  private double scaleValue;
  private double zoomIntensity = 0.02;

  private ScrollPane scrollPane;
  private StackPane stackPane;

  public void makeZoomable(ScrollPane scrollPane, StackPane stackPane, double maxScaleFactor) {
    this.maxScaleFactor = maxScaleFactor;
    this.scrollPane = scrollPane;
    this.stackPane = stackPane;
    zoomNode = new Group(this.stackPane);
    scrollPane.setContent(outerNode(zoomNode));

    scrollPane.setPannable(true);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setFitToHeight(true); // center
    scrollPane.setFitToWidth(true); // center
    scaleValue = maxScaleFactor;
    updateScale();
  }

  private Node outerNode(Node node) {
    Node outerNode = centeredNode(node);
    outerNode.setOnScroll(
        e -> {
          e.consume();
          onScroll(e.getTextDeltaY(), new Point2D(e.getX(), e.getY()));
        });
    return outerNode;
  }

  private Node centeredNode(Node node) {
    VBox vBox = new VBox(node);
    vBox.setAlignment(Pos.CENTER);
    return vBox;
  }

  private void updateScale() {
    stackPane.setScaleX(scaleValue);
    stackPane.setScaleY(scaleValue);
  }

  private void onScroll(double wheelDelta, Point2D mousePoint) {
    double zoomFactor = Math.exp(wheelDelta * zoomIntensity);
    System.out.println("Scale factor: " + scaleValue);

    Bounds innerBounds = zoomNode.getLayoutBounds();
    Bounds viewportBounds = scrollPane.getViewportBounds();
    System.out.println(
        innerBounds.getWidth() + " " + viewportBounds.getWidth() + " " + scrollPane.getHvalue());

    // calculate pixel offsets from [0, 1] range
    double valX = scrollPane.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
    double valY = scrollPane.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

    scaleValue = Math.max(maxScaleFactor, scaleValue * zoomFactor);
    System.out.println("Scale factor: " + scaleValue);
    updateScale();
    scrollPane.layout(); // refresh ScrollPane scroll positions & target bounds

    // convert target coordinates to zoomTarget coordinates
    Point2D posInZoomTarget = stackPane.parentToLocal(zoomNode.parentToLocal(mousePoint));

    // calculate adjustment of scroll position (pixels)
    Point2D adjustment =
        stackPane
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
