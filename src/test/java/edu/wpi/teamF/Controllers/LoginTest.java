package edu.wpi.teamF.Controllers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.AccountFactory;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.TestData;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import javax.management.InstanceNotFoundException;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

public class LoginTest {}
