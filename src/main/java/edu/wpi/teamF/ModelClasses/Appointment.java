package edu.wpi.teamF.ModelClasses;

public class Appointment {

    private String id;
    private Node location;
    private String room;
    private String userID;
    private String PCP;

    public Appointment(String id, Node location, String room, String userID, String PCP){
        setId(id);
        setLocation(location);
        setRoom(room);
        setUserId(userID);
        setPCP(PCP);
    }

    /**
     *
     * @return
     */
    public String getId(){
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id){
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Node getLocation(){
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(Node location){
        this.location = location;
    }
    /**
     *
     * @return
     */
    public String getRoom(){
        return room;
    }
    /**
     *
     * @param room
     */
    public void setRoom(String room){
        this.room = room;
    }
    /**
     *
     * @return
     */
    public String getUserID(){
        return userID;
    }
    /**
     *
     * @param userID
     */
    public void setUserId(String userID){
        this.userID = userID;
    }
    /**
     *
     * @return
     */
    public String getPCP(){
        return PCP;
    }
    /**
     *
     * @param PCP
     */
    public void setPCP(String PCP){
        this.PCP = PCP;
    }

}
