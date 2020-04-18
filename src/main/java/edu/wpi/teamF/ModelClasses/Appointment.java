package edu.wpi.teamF.ModelClasses;

public class Appointment {

    private String id;
    private Node location;
    private String room;
    private String userID;
    private String PCP;

    public Appointment(String id, Node location, String room, String userID, String PCP) throws ValidationException{
        setId(id);
        setLocation(location);
        setRoom(room);
        setUserId(userID);
        setPCP(PCP);
    }

    /**
     *  Get the id of the appointment
     * @return the id
     */
    public String getId(){
        return id;
    }

    /**
     * Set the id of the appointment
     * @param id the id to set
     * @throws ValidationException should the validators fail
     */
    public void setId(String id) throws ValidationException{
        Validators.idValidation(id);
        this.id = id;
    }

    /**
     * Get the location of the appointment
     * @return the location
     */
    public Node getLocation(){
        return location;
    }

    /**
     * Set the location of the appointment
     * @param location the location to set
     * @throws ValidationException should the validators fail
     */
    public void setLocation(Node location) throws ValidationException{
        Validators.nodeValidation(location);
        this.location = location;
    }
    /**
     * Get the room of the appointment
     * @return the room
     */
    public String getRoom(){
        return room;
    }
    /**
     * Set the room of the appointment
     * @param room the room to set
     * @throws ValidationException should the validators fail
     */
    public void setRoom(String room) throws ValidationException{
        Validators.roomValidation(room);
        this.room = room;
    }
    /**
     * Get the userID of the appointment
     * @return the userID
     */
    public String getUserID(){
        return userID;
    }

    /**
     * Set the userID of the appointment
     * @param userID the userID to set
     * @throws ValidationException should the validators fail
     */
    public void setUserId(String userID) throws ValidationException{
        Validators.userIDValidation(userID);
        this.userID = userID;
    }
    /**
     * Get the PCP of the appointment
     * @return the PCP
     */
    public String getPCP(){
        return PCP;
    }
    /**
     * Set the PCP of the appointment
     * @param PCP the PCP to set
     * @throws ValidationException should the validators fail
     */
    public void setPCP(String PCP) throws ValidationException{
        Validators.PCPValidation(PCP);
        this.PCP = PCP;
    }

}
