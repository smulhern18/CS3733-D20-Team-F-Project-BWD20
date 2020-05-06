package edu.wpi.teamF.Controllers;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

public class GoogleMaps {
  private GeoApiContext context =
      new GeoApiContext.Builder().apiKey("AIzaSyB61pjpz4PvzIKYCsYiwHoWQctXiw9soHc").build();
  private DirectionsRoute[] calculatedRoutes;
  private String origin;
  private String destination;

  public GoogleMaps(String origin, String destination) {
    this.origin = origin;
    this.destination = destination;
  }

  public String driveTime() {
    try {
      DirectionsResult result =
          DirectionsApi.newRequest(context)
              .alternatives(true)
              .mode(TravelMode.DRIVING)
              .origin(origin)
              .destination(destination)
              .departureTimeNow()
              .await();
      return result
          .routes[0]
          .legs[0]
          .durationInTraffic
          .toString()
          .replace("hour", "h")
          .replace("mins", "min");
    } catch (Exception e) {
      e.printStackTrace();
      return "Unavailable";
    }
  }

  public String driveDistance() {
    try {
      DirectionsResult result =
          DirectionsApi.newRequest(context)
              .alternatives(true)
              .mode(TravelMode.DRIVING)
              .origin(origin)
              .destination(destination)
              .departureTimeNow()
              .await();
      return result.routes[0].legs[0].distance.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public String transitTime() {
    try {
      DirectionsResult result =
          DirectionsApi.newRequest(context)
              .alternatives(true)
              .mode(TravelMode.TRANSIT)
              .origin(origin)
              .destination(destination)
              .departureTimeNow()
              .await();
      return result
          .routes[0]
          .legs[0]
          .duration
          .toString()
          .replace("hour", "h")
          .replace("mins", "min");
    } catch (Exception e) {
      e.printStackTrace();
      return "Unavailable";
    }
  }

  public String transitDistance() {
    try {
      DirectionsResult result =
          DirectionsApi.newRequest(context)
              .alternatives(true)
              .mode(TravelMode.TRANSIT)
              .origin(origin)
              .destination(destination)
              .departureTimeNow()
              .await();
      return result.routes[0].legs[0].distance.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public String bikeTime() {
    try {
      DirectionsResult result =
          DirectionsApi.newRequest(context)
              .alternatives(true)
              .mode(TravelMode.BICYCLING)
              .origin(origin)
              .destination(destination)
              .departureTimeNow()
              .await();
      return result
          .routes[0]
          .legs[0]
          .duration
          .toString()
          .replace("hour", "h")
          .replace("mins", "min");
    } catch (Exception e) {
      e.printStackTrace();
      return "Unavailable";
    }
  }

  public String bikeDistance() {
    try {
      DirectionsResult result =
          DirectionsApi.newRequest(context)
              .alternatives(true)
              .mode(TravelMode.BICYCLING)
              .origin(origin)
              .destination(destination)
              .departureTimeNow()
              .await();
      return result.routes[0].legs[0].distance.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public String walkTime() {
    try {
      DirectionsResult result =
          DirectionsApi.newRequest(context)
              .alternatives(true)
              .mode(TravelMode.WALKING)
              .origin(origin)
              .destination(destination)
              .departureTimeNow()
              .await();
      return result
          .routes[0]
          .legs[0]
          .duration
          .toString()
          .replace("hour", "h")
          .replace("mins", "min");
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public String walkDistance() {
    try {
      DirectionsResult result =
          DirectionsApi.newRequest(context)
              .alternatives(true)
              .mode(TravelMode.WALKING)
              .origin(origin)
              .destination(destination)
              .departureTimeNow()
              .await();
      return result.routes[0].legs[0].distance.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }
}