package edu.wpi.teamF.DatabaseManipulators;


import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;

import java.util.List;

public class ServiceRequestFactory {
    private static final ServiceRequestFactory factory = new ServiceRequestFactory();

    public static ServiceRequestFactory getFactory() {
        return factory;
    }

    public void create(ServiceRequest serviceRequest) {

    }

    public ServiceRequest read(String id) {
        return null;
    }

    public void update(ServiceRequest serviceRequest) {

    }

    public void delete(String id) {

    }

    public List<ServiceRequest> getServicesByLocation(Node location) {
        return null;
    }

    public List<ServiceRequest> getAllServices() {
        return null;
    }
}
