package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.util.Date;

public class LanguageServiceRequest extends ServiceRequest {
  private String language;
  private String problemType;

  public LanguageServiceRequest(
      String id,
      Node location,
      String assignee,
      String description,
      Date dateTimeSubmitted,
      int priority,
      boolean complete,
      String language,
      String problemType)
      throws ValidationException {
    super(id, location, assignee, description, dateTimeSubmitted, priority, complete);
    setProblemType(problemType);
    setLanguage(language);
  }

  public LanguageServiceRequest(
      Node location,
      String description,
      String assignee,
      Date dateTimeSubmitted,
      int priority,
      String language,
      String problemType)
      throws ValidationException {
    super(location, assignee, description, dateTimeSubmitted, priority);
    setProblemType(problemType);
    setLanguage(language);
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String makeModel) throws ValidationException {
    Validators.makeValidation(makeModel);
    this.language = makeModel;
  }

  public String getProblemType() {
    return problemType;
  }

  public void setProblemType(String problemType) throws ValidationException {
    Validators.hardwareSoftwareValidation(problemType);
    this.problemType = problemType;
  }
}
