package edu.wpi.teamF.Controllers.com.twilio;

// Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class phoneComms {
  private static final String ACCOUNT_SID = "ACd77f1098fb05ebf0c1bf799a088a3518";
  private static final String AUTH_TOKEN = "b46184eb317b3c1f124910cfb5e44f6e";

  public phoneComms() {}

  public static Boolean sendMsg(String toPhone, String msg) {
    try {
      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
      Message message =
          Message.creator(
                  new PhoneNumber("+1" + toPhone), // to
                  new PhoneNumber("+15085937151"), // from
                  msg)
              .create();
      System.out.println("Sent SMS, SID: " + message.getSid());
      return true;
    } catch (Exception e) {
      System.out.println("Error sending SMS");
      return false;
    }
  }

  public static Boolean callPhone(String toPhone, String twiml) {
    try {
      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
      Call call =
          Call.creator(
                  new com.twilio.type.PhoneNumber("+1" + toPhone), // to
                  new com.twilio.type.PhoneNumber("+15085937151"), // from
                  new com.twilio.type.Twiml(twiml))
              .create();
      System.out.println("Completed call, SID: " + call.getSid());
      return true;
    } catch (Exception e) {
      System.out.println("Error executing phone call");
      return false;
    }
  }
}
