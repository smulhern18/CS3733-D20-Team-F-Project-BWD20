package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LanguageServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageServiceRequestFactory {
  NodeFactory nodeFactory = NodeFactory.getFactory();
  private static final LanguageServiceRequestFactory factory = new LanguageServiceRequestFactory();
  private static final ServiceRequestFactory serviceRequestFactory =
      ServiceRequestFactory.getFactory();

  public static LanguageServiceRequestFactory getFactory() {
    return factory;
  }

  public void create(LanguageServiceRequest languageServiceRequest) throws ValidationException {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.LANGUAGE_REQUEST_TABLE_NAME
            + " ( "
            + DatabaseManager.SERVICEID_KEY
            + ", "
            + DatabaseManager.LANGUAGE_KEY
            + ", "
            + DatabaseManager.PROBLEMTYPE_KEY
            + " ) "
            + "VALUES (?, ?, ?, ?)";
    Validators.languageServiceValidation(languageServiceRequest);
    serviceRequestFactory.create(languageServiceRequest);
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, languageServiceRequest.getId());
      prepareStatement.setString(param++, languageServiceRequest.getLanguage());
      prepareStatement.setString(param++, languageServiceRequest.getProblemType());
      try {
        int numRows = prepareStatement.executeUpdate();
        if (numRows < 1) {
          throw new SQLException("Created more than one rows");
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public LanguageServiceRequest read(String id) {
    LanguageServiceRequest languageService = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.LANGUAGE_REQUEST_TABLE_NAME
            + " WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, id);

      try {
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          ServiceRequest serviceRequest = serviceRequestFactory.read(id);
          languageService =
              new LanguageServiceRequest(
                  serviceRequest.getId(),
                  serviceRequest.getLocation(),
                  serviceRequest.getAssignee(),
                  serviceRequest.getDescription(),
                  serviceRequest.getDateTimeSubmitted(),
                  serviceRequest.getPriority(),
                  serviceRequest.getComplete(),
                  resultSet.getString(DatabaseManager.LANGUAGE_KEY),
                  resultSet.getString(DatabaseManager.PROBLEMTYPE_KEY));
        }
      } catch (ValidationException e) {
        throw e;
      }
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return languageService;
  }

  public void update(LanguageServiceRequest languageServiceRequest) {
    String updateStatement =
        "UPDATE "
            + DatabaseManager.LANGUAGE_REQUEST_TABLE_NAME
            + " SET "
            + DatabaseManager.SERVICEID_KEY
            + " = ?, "
            + DatabaseManager.LANGUAGE_KEY
            + " = ?, "
            + DatabaseManager.PROBLEMTYPE_KEY
            + " = ?, "
            + "WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      serviceRequestFactory.update(languageServiceRequest);
      preparedStatement.setString(param++, languageServiceRequest.getId());
      preparedStatement.setString(param++, languageServiceRequest.getLanguage());
      preparedStatement.setString(param++, languageServiceRequest.getProblemType());
      preparedStatement.setString(param++, languageServiceRequest.getId());
      int numRows = preparedStatement.executeUpdate();
      if (numRows != 1) {
        throw new Exception("Updated " + numRows + " rows");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void delete(String id) {

    String deleteStatement =
        "DELETE FROM "
            + DatabaseManager.LANGUAGE_REQUEST_TABLE_NAME
            + " WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";
    serviceRequestFactory.delete(id);
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(deleteStatement)) {
      preparedStatement.setString(1, id);

      int numRows = preparedStatement.executeUpdate();
      if (numRows > 1) {
        throw new SQLException("Deleted " + numRows + " rows");
      }
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage() + ", " + e.getCause());
    }
  }

  public List<LanguageServiceRequest> getLanguageRequestsByLocation(Node location) {
    List<LanguageServiceRequest> languageRequests = new ArrayList<>();
    for (ServiceRequest serviceRequest :
        serviceRequestFactory.getServiceRequestsByLocation(location)) {
      LanguageServiceRequest languageReadRequest = read(serviceRequest.getId());
      if (languageReadRequest != null) {
        languageRequests.add(languageReadRequest);
      }
    }
    if (languageRequests.size() == 0) {
      return null;
    } else {
      return languageRequests;
    }
  }

  public List<LanguageServiceRequest> getAllLanguageRequests() {
    List<LanguageServiceRequest> languageRequests = new ArrayList<>();
    String selectStatement = "SELECT * FROM " + DatabaseManager.LANGUAGE_REQUEST_TABLE_NAME;

    try (PreparedStatement preparedStatement =
            DatabaseManager.getConnection().prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      languageRequests = new ArrayList<>();
      ;
      while (resultSet.next()) {
        ServiceRequest serviceRequest =
            serviceRequestFactory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
        LanguageServiceRequest languageServiceRequest =
            factory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
        languageRequests.add(
            new LanguageServiceRequest(
                serviceRequest.getId(),
                serviceRequest.getLocation(),
                serviceRequest.getAssignee(),
                serviceRequest.getDescription(),
                serviceRequest.getDateTimeSubmitted(),
                serviceRequest.getPriority(),
                serviceRequest.getComplete(),
                languageServiceRequest.getLanguage(),
                languageServiceRequest.getProblemType()));
      }
    } catch (Exception e) {
      System.out.println(
          "Exception in SecurityFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return languageRequests;
  }
}
