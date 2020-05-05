package edu.wpi.teamF.ModelClasses.Directions;

public class TravelDirection extends Direction {
  private String from;
  private String to;
  private String floor;

  public TravelDirection(String from, String to, String floor) {
    this.from = from;
    this.to = to;
    this.floor = floor;
  }

  @Override
  // Include the turn-by-turn
  public String getDirectionText() {
    String returnString = "";
    if ("Faulkner".equals(from) && ("15 Francis".equals(to) || "FLEX".equals(to))) {
      returnString =
          "Directions from Faulkner Hospital Atrium Entrance to 15 Francis St Lobby Entrance: \n\nHead southeast on Whitcomb Ave toward Centre St\n"
              + "Travel for 466 ft\n"
              + "Turn left onto Centre St\n"
              + "Travel for 0.5 mi\n"
              + "At the traffic circle, take the 3rd exit onto Arborway\n"
              + "Travel for 0.3 mi\n"
              + "Continue onto Pond St\n"
              + "Travel for 0.3 mi\n"
              + "Continue onto Jamaicaway\n"
              + "Travel for 1.1 mi\n"
              + "Turn right to stay on Jamaicaway\n"
              + "Travel for 0.1 mi\n"
              + "Turn right onto Huntington Ave\n"
              + "Travel for 0.4 mi\n"
              + "Turn left onto Francis St\n"
              + "Travel for 318 ft\n"
              + "Arrive at 15 Francis St\n";
    } else if ("Faulkner".equals(from) && ("45 Francis".equals(to) || "Tower".equals(to))) {
      returnString =
          "Directions from Faulkner Hospital Atrium Entrance to 45 Francis St Main Entrance: \n\nHead southeast on Whitcomb Ave toward Centre St\n"
              + "Travel for 466 ft\n"
              + "Turn left onto Centre St\n"
              + "Travel for 0.5 mi\n"
              + "At the traffic circle, take the 3rd exit onto Arborway\n"
              + "Travel for 0.3 mi\n"
              + "Continue onto Pond St\n"
              + "Travel for 0.3 m\n"
              + "Continue onto Jamaicaway\n"
              + "Travel for 1.1 mi\n"
              + "Keep left to continue on Jamaicaway/Riverway\n"
              + " Continue to follow Riverway\n"
              + "Travel for 0.4 mi\n"
              + "Keep left to stay on Riverway\n"
              + "Travel for 322 ft\n"
              + "Turn right onto Brookline Ave\n"
              + "Travel for 328 ft\n"
              + "Turn right onto Francis St\n"
              + "Travel for 0.2 mi\n"
              + "Arrive at 45 Francis St\n";
    } else if ("Faulkner".equals(from) && "75 Francis".equals(to)) {
      returnString =
          "Directions from Faulkner Hospital Atrium Entrance to 75 Francis St Main Entrance: \n\nHead southeast on Whitcomb Ave toward Centre St\n"
              + "Travel for 466 ft\n"
              + "Turn left onto Centre St\n"
              + "Travel for 0.5 mi\n"
              + "At the traffic circle, take the 3rd exit onto Arborway\n"
              + "Travel for 0.3 mi\n"
              + "Continue onto Pond St\n"
              + "Travel for 0.3 mi\n"
              + "Continue onto Jamaicaway\n"
              + "Travel for 1.1 mi\n"
              + "Keep left to continue on Jamaicaway/Riverway\n"
              + "Travel for 0.4 mi\n"
              + "Keep left to stay on Riverway\n"
              + "Travel for 322 ft\n"
              + "Turn right onto Brookline Ave\n"
              + "Travel for 328 ft\n"
              + "Turn right onto Francis St\n"
              + "Travel for 0.1 mi\n"
              + "Turn left at Vining St\n"
              + "Travel for 121 ft\n"
              + "Arrive at 75 Francis St\n";
    } else if ("Faulkner".equals(from) && "Shapiro".equals(to)) {
      returnString =
          "Directions from Faulkner Hospital Atrium Entrance to Shapiro Cardiovascular Center : \n\nHead southeast on Whitcomb Ave toward Centre St\n"
              + "Travel for 466 ft\n"
              + "Turn left onto Centre St\n"
              + "Travel for 0.5 mi\n"
              + "At the traffic circle, take the 3rd exit onto Arborway\n"
              + "Travel for 0.3 mi\n"
              + "Continue onto Pond St\n"
              + "Travel for 0.3 mi\n"
              + "Continue onto Jamaicaway\n"
              + "Travel for 1.1 mi\n"
              + "Keep left to continue on Jamaicaway/Riverway\n"
              + " Continue to follow Riverway\n"
              + "Travel for 0.4 mi\n"
              + "Keep left to stay on Riverway\n"
              + "Travel for 322 ft\n"
              + "Turn right onto Brookline Ave\n"
              + "Travel for 328 ft\n"
              + "Turn right onto Francis St\n"
              + "Travel for 0.1 mi\n"
              + "Arrive at 70 Francis St\n";
    } else if ("Faulkner".equals(from) && "BTM".equals(to)) {
      returnString =
          "Directions from Faulkner Hospital Atrium Entrance to BTM Fenwood Rd Entrance : \n\nHead southeast on Whitcomb Ave toward Centre St\n"
              + "Travel for 466 ft\n"
              + "Turn left onto Centre St\n"
              + "Travel for 0.5 mi\n"
              + "At the traffic circle, take the 3rd exit onto Arborway\n"
              + "Travel for 0.3 mi\n"
              + "Continue onto Pond St\n"
              + "Travel for 0.3 mi\n"
              + "Continue onto Jamaicaway\n"
              + "Travel for 1.1 mi\n"
              + "Keep left to continue on Jamaicaway/Riverway\n"
              + " Continue to follow Riverway\n"
              + "Travel for 0.4 m\n"
              + "Keep left to stay on Riverway\n"
              + "Travel for 322 ft\n"
              + "Turn right onto Brookline Ave\n"
              + "Travel for 102 ft\n"
              + "Turn right onto Fenwood Rd\n"
              + "Travel for 482 ft\n"
              + "Arrive at 60 Fenwood Rd\n";
    }
    // Start opposite direction
    else if ("Faulkner".equals(to) && ("15 Francis".equals(from) || "FLEX".equals(from))) {
      returnString =
          "Directions from 15 Francis St Lobby Entrance to Faulkner Hospital Atrium Entrance: \n\nHead northwest on Francis St toward St Albans Rd\n"
              + "Travel for 0.3 mi\n"
              + "Turn left onto Brookline Ave\n"
              + "Travel for 354 ft\n"
              + "Turn left onto Riverway\n"
              + "Travel for 0.4 mi\n"
              + "Continue onto Jamaicaway\n"
              + "Travel for 1.2 mi\n"
              + "Continue onto Pond St\n"
              + "Travel for 0.3 mi\n"
              + "Use any lane to turn slightly right onto Arborway\n"
              + "Travel for 0.2 mi\n"
              + "Slight left to stay on Arborway\n"
              + "Travel for 0.2 mi\n"
              + "At the traffic circle, take the 1st exit onto Centre St\n"
              + "Travel for 0.6 mi\n"
              + "Arrive at Faulkner Hospital\n";
    } else if ("Faulkner".equals(to) && ("45 Francis".equals(from) || "Tower".equals(from))) {
      returnString =
          "Directions from 45 Francis St Main Entrance to Faulkner Hospital Atrium Entrance: \n\nHead southeast toward Francis St\n"
              + "Travel for 171 ft\n"
              + "Turn right onto Francis St\n"
              + "Travel for 0.2 mi\n"
              + "Turn left onto Brookline Ave\n"
              + "Travel for 354 ft\n"
              + "Turn left onto Riverway\n"
              + "Travel for 0.4 mi\n"
              + "Continue onto Jamaicaway\n"
              + "Travel for 1.2 mi\n"
              + "Continue onto Pond St\n"
              + "Travel for 0.3 mi\n"
              + "Use any lane to turn slightly right onto Arborway\n"
              + "Travel for 0.2 mi\n"
              + "Slight left to stay on Arborway\n"
              + "Travel for 0.2 mi\n"
              + "At the traffic circle, take the 1st exit onto Centre St\n"
              + "Travel for 0.6 mi\n"
              + "Arrive at Faulkner Hospital\n";
    } else if ("Faulkner".equals(to) && "75 Francis".equals(from)) {
      returnString =
          "Directions from 75 Francis St Main Entrance to Faulkner Hospital Atrium Entrance: \n\nHead northwest toward Binney St\n"
              + "Travel for 322 ft\n"
              + "Turn left onto Binney St\n"
              + "Travel for 115 ft\n"
              + "Turn right onto Francis St\n"
              + "Travel for 374 ft\n"
              + "Turn left at the 1st cross street onto Brookline Ave\n"
              + "Travel for 354 ft\n"
              + "Turn left onto Riverway\n"
              + "Travel for 0.4 mi\n"
              + "Continue onto Jamaicaway\n"
              + "Travel for 1.2 mi\n"
              + "Continue onto Pond St\n"
              + "Travel for 0.3 mi\n"
              + "Use any lane to turn slightly right onto Arborway\n"
              + "Travel for 0.2 mi\n"
              + "Slight left to stay on Arborway\n"
              + "Travel for 0.2 m\n"
              + "At the traffic circle, take the 1st exit onto Centre St\n"
              + "Travel for 0.6 mi\n"
              + "Arrive at Faulkner Hospital\n";
    } else if ("Faulkner".equals(to) && "Shapiro".equals(from)) {
      returnString =
          "Directions from Shapiro Cardiovascular Center to Faulkner Hospital Atrium Entrance: \n\nHead northwest on Francis St toward Binney St\n"
              + "Travel for 0.1 m\n"
              + "Turn left at the 2nd cross street onto Brookline Ave\n"
              + "Travel for 354 ft\n"
              + "Turn left onto Riverway\n"
              + "Travel for 0.4 m\n"
              + "Continue onto Jamaicaway\n"
              + "Travel for 1.2 mi\n"
              + "Continue onto Pond St\n"
              + "Travel for 0.3 mi\n"
              + "Use any lane to turn slightly right onto Arborway\n"
              + "Travel for 0.2 mi\n"
              + "Slight left to stay on Arborway\n"
              + "Travel for 0.2 mi\n"
              + "At the traffic circle, take the 1st exit onto Centre St\n"
              + "Travel for 0.6 mi\n"
              + "Turn right onto Whitcomb Ave\n"
              + "Travel for 466 ft\n"
              + "Arrive at Faulkner Hospital\n";
    } else if ("Faulkner".equals(to) && "BTM".equals(from)) {
      returnString =
          "Directions from BTM Fenwood Rd Entrance to Faulkner Hospital Atrium Entrance: \n\nTake Binney St to Francis St\n"
              + "Travel for 335 ft\n"
              + "Head northwest on Fenwood Rd toward Binney St\n"
              + "Travel for 112 ft\n"
              + "Turn right onto Binney St\n"
              + "Travel for 223 ft\n"
              + "Turn left onto Francis St\n"
              + "Travel for 374 ft\n"
              + "Turn left at the 1st cross street onto Brookline Ave\n"
              + "Travel for 354 ft\n"
              + "Turn left onto Riverway\n"
              + "Travel for 0.4 mi\n"
              + "Continue onto Jamaicaway\n"
              + "Travel for 1.2 mi\n"
              + "Continue onto Pond St\n"
              + "Travel for 0.3 mi\n"
              + "Use any lane to turn slightly right onto Arborway\n"
              + "Travel for 0.2 mi\n"
              + "Slight left to stay on Arborway\n"
              + "Travel for 0.2 mi\n"
              + "At the traffic circle, take the 1st exit onto Centre St\n"
              + "Travel for 0.6 mi\n"
              + "Turn right onto Whitcomb Ave\n"
              + "Travel for 466 ft\n"
              + "Arrive at Faulkner Hospital\n";
    } else {
      returnString = ("Travel from " + from + " to " + to + ".");
    }
    return returnString;
  }

  @Override
  // Include the URL
  public String getDirectionTextSMS() {
    String returnString = "";

    if ("Faulkner".equals(from) && ("15 Francis".equals(to) || "FLEX".equals(to))) {
      returnString =
          "Travel from Faulkner Hospital Atrium Entrance to 15 Francis St Lobby Entrance. For directions, go to: http://tiny.cc/zybfoz ";
    } else if ("Faulkner".equals(from) && ("45 Francis".equals(to) || "Tower".equals(to))) {
      returnString =
          "Travel from Faulkner Hospital Atrium Entrance to 45 Francis St Main Entrance. For directions, go to: http://tiny.cc/b0bfoz";
    } else if ("Faulkner".equals(from) && "75 Francis".equals(to)) {
      returnString =
          "Travel from Faulkner Hospital Atrium Entrance to 75 Francis St Main Entrance. For directions, go to: http://tiny.cc/s1bfoz";
    } else if ("Faulkner".equals(from) && "Shapiro".equals(to)) {
      returnString =
          "Travel from Faulkner Hospital Atrium Entrance to Shapiro Cardiovascular Center. For directions, go to: http://tiny.cc/g2bfoz";
    } else if ("Faulkner".equals(from) && "BTM".equals(to)) {
      returnString =
          "Travel from Faulkner Hospital Atrium Entrance to BTM Fenwood Rd Entrance. For directions, go to: http://tiny.cc/92bfoz";
    }
    // Start opposite direction
    else if ("Faulkner".equals(to) && ("15 Francis".equals(from) || "FLEX".equals(from))) {
      returnString =
          "Travel from 15 Francis St Lobby Entrance to Faulkner Hospital Atrium Entrance. For directions, go to: http://tiny.cc/i4bfoz";
    } else if ("Faulkner".equals(to) && ("45 Francis".equals(from) || "Tower".equals(from))) {
      returnString =
          "Travel from 45 Francis St Main Entrance to Faulkner Hospital Atrium Entrance. For directions, go to: http://tiny.cc/04bfoz";
    } else if ("Faulkner".equals(to) && "75 Francis".equals(from)) {
      returnString =
          "Travel from 75 Francis St Main Entrance to Faulkner Hospital Atrium Entrance. For directions, go to: http://tiny.cc/y5bfoz";
    } else if ("Faulkner".equals(to) && "Shapiro".equals(from)) {
      returnString =
          "Travel from Shapiro Cardiovascular Center to Faulkner Hospital Atrium Entrance. For directions, go to: http://tiny.cc/06bfoz";
    } else if ("Faulkner".equals(to) && "BTM".equals(from)) {
      returnString =
          "Travel from BTM Fenwood Rd Entrance to Faulkner Hospital Atrium Entrance. For directions, go to: http://tiny.cc/v7bfoz";
    } else {
      returnString = ("Travel from " + from + " to " + to + ".");
    }

    return returnString;
  }

  @Override
  // Do not include the turn-by-turn
  public String getDirectionTextCall() {
    String returnString = "";
    if ("Faulkner".equals(from) && ("15 Francis".equals(to) || "FLEX".equals(to))) {
      returnString =
          "Travel from Faulkner Hospital Atrium Entrance to 15 Francis St Lobby Entrance.";
    } else if ("Faulkner".equals(from) && ("45 Francis".equals(to) || "Tower".equals(to))) {
      returnString =
          "Travel from Faulkner Hospital Atrium Entrance to 45 Francis St Main Entrance.";
    } else if ("Faulkner".equals(from) && "75 Francis".equals(to)) {
      returnString =
          "Travel from Faulkner Hospital Atrium Entrance to 75 Francis St Main Entrance.";
    } else if ("Faulkner".equals(from) && "Shapiro".equals(to)) {
      returnString =
          "Travel from Faulkner Hospital Atrium Entrance to Shapiro Cardiovascular Center.";
    } else if ("Faulkner".equals(from) && "BTM".equals(to)) {
      returnString = "Travel from Faulkner Hospital Atrium Entrance to BTM Fenwood Rd Entrance.";
    }
    // Start opposite direction
    else if ("Faulkner".equals(to) && ("15 Francis".equals(from) || "FLEX".equals(from))) {
      returnString =
          "Travel from 15 Francis St Lobby Entrance to Faulkner Hospital Atrium Entrance.";
    } else if ("Faulkner".equals(to) && ("45 Francis".equals(from) || "Tower".equals(from))) {
      returnString =
          "Travel from 45 Francis St Main Entrance to Faulkner Hospital Atrium Entrance.";
    } else if ("Faulkner".equals(to) && "75 Francis".equals(from)) {
      returnString =
          "Travel from 75 Francis St Main Entrance to Faulkner Hospital Atrium Entrance.";
    } else if ("Faulkner".equals(to) && "Shapiro".equals(from)) {
      returnString =
          "Travel from Shapiro Cardiovascular Center to Faulkner Hospital Atrium Entrance.";
    } else if ("Faulkner".equals(to) && "BTM".equals(from)) {
      returnString = "Travel from BTM Fenwood Rd Entrance to Faulkner Hospital Atrium Entrance.";
    } else {
      returnString = ("Travel from " + from + " to " + to + ".");
    }
    return returnString;
  }

  @Override
  // Do not include the turn-by-turn
  public String getDirectionTextPrint() {
    return getDirectionTextCall();
  }

  @Override
  public String getFloor() {
    return floor;
  }
}
