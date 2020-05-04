package edu.wpi.cs3733.d20.teamF;


import edu.wpi.cs3733.d20.teamF.ModelClasses.Account.Account;
import edu.wpi.cs3733.d20.teamF.ModelClasses.Account.Staff;
import edu.wpi.cs3733.d20.teamF.ModelClasses.Account.Admin;
import edu.wpi.cs3733.d20.teamF.ModelClasses.MaintenanceRequest;

import java.util.Date;

    public class TestData {

        public String[] validEdgeIDs = {
                "nodeA_nodeH", "nodeB_nodeG", "nodeC_nodeF", "nodeD_nodeE", "nodeT_nodeT"
        };

        public String[] invalidEdgeIDs = {
                "nodeA", "", null, "this string is waaaaaaaaay toooooooo loooooooong to be valid"
        };

        public String[] validNodeIDs = {
                "nodeA", "nodeB", "nodeC", "nodeD", "nodeE", "nodeF", "nodeG", "nodeH"
        };

        public String[] invalidNodeIDS = {
                "", null, "this string is waaaaaaaaay toooooooo loooooooong to be valid"
        };

        public Short[] validCoordinates = {10, 20, 100, 2};

        public Short[] invalidCoordinates = {0, -100, null};

        public String[] validBuildings = {"Building A", "Building B", "Evil Headquarters", "That one"};

        public String[] invalidBuildings = {
                "", null, "this string is waaaaaaaaay toooooooo loooooooong to be valid"
        };

        public String[] validLongNames = {
                "Intensive Care Unit",
                "Administrative offices",
                "Department of Computer Science",
                "Hallway outside everything"
        };

        public String[] invalidLongNames = {
                "",
                null,
                "Did you ever hear the Tragedy of Darth Plagueis the wise? I thought not. It’s not a story the Jedi would tell you. It’s a Sith legend. Darth Plagueis was a Dark Lord of the Sith, so powerful and so wise he could use the Force to influence the midichlorians to create life... He had such a knowledge of the dark side that he could even keep the ones he cared about from dying. The dark side of the Force is a pathway to many abilities some consider to be unnatural. He became so powerful... the only thing he was afraid of was losing his power, which eventually, of course, he did. Unfortunately, he taught his apprentice everything he knew, then his apprentice killed him in his sleep. It’s ironic he could save others from death, but not himself."
        };

        public String[] validShortNames = {"ICU", "Admin Offices", "Dept of CS", "HallOutside"};

        public String[] invalidShortNames = {
                "", null, "this string is waaaaaaaaay toooooooo loooooooong to be valid"
        };

        public Short[] validFloors = {1, 5, 10, 3};

        public Short[] invalidFloors = {null, -3, 0};

        public String[] validUsernames = {"This one", "TheCuddleMonster", "sjmulhern", "Snuggles"};

        public String[] invalidUsernames = {
                "", null, "this string is waaaaaaaaay toooooooo loooooooong to be valid"
        };

        public String[] validPasswords = {
                "Areally$tr0ngPassw@rd", "aWeakerPassw0rd", "weakPassword", "password"
        };

        public String[] invalidPasswords = {
                "weakPWD", "", null, "this string is waaaaaaaaay toooooooo loooooooong to be valid"
        };

        public String[] validNames = {"Ferdinand", "Nikolas", "Constantine", "Nero"};

        public String[] invalidNames = {
                "", null, "this string is waaaaaaaaay toooooooo loooooooong to be valid"
        };

        public String[] validEmails = {
                "sjmulhern@wpi.edu", "joeBiden@usa.gov", "putin@rus.ru", "wack@giggle.pig"
        };

        public String[] invalidEmails = {"sjmulhern", "@wpi.edu", "", null};

        public Account.Type[] validAccountTypes = Account.Type.values();

        public Staff.Specialty[] validStaffSpecialtys = Staff.Specialty.values();

        public Account[] validAccounts = new Account[]{
                new Admin(validNames[0], validNames[0], validEmails[0], validUsernames[0], validPasswords[0]),
                new Staff(validNames[1], validNames[1], validEmails[1], validUsernames[1], validPasswords[1], validStaffSpecialtys[1])
        };

        public Date[] validDates = {new Date(0), new Date(10), new Date(110), new Date(1110)};
        public Date[] validCompletedDates = {new Date(10), new Date(100), new Date(300), new Date(2000)};
        public String[] validIDs = {"12312442", "123124134", "124134123412", "213124124"};
        public String[] validDescriptions = {"12312442", "123124134", "124134123412", "213124124"};
        public String[] validAssignees = {"Roman", "Sully", "Denver", "Charles"};
        public String[] validSongs = {"AAA", "BBB", "CCC", "DDD"};

        public MaintenanceRequest[] validMaintenanceRequests = {
                new MaintenanceRequest(
                        validIDs[0],
                        validNodeIDs[0],
                        validAssignees[0],
                        validDescriptions[0],
                        validDates[0],
                        2,
                        true,
                        validCompletedDates[0]),
                new MaintenanceRequest(
                        validIDs[1],
                        validNodeIDs[1],
                        validAssignees[1],
                        validDescriptions[1],
                        validDates[1],
                        2,
                        false,
                        validCompletedDates[1]),
                new MaintenanceRequest(
                        validIDs[2],
                        validNodeIDs[2],
                        validAssignees[2],
                        validDescriptions[2],
                        validDates[2],
                        2,
                        true,
                        validCompletedDates[2]),
                new MaintenanceRequest(
                        validIDs[3],
                        validNodeIDs[3],
                        validAssignees[3],
                        validDescriptions[3],
                        validDates[3],
                        2,
                        false,
                        validCompletedDates[3]),
        };

        public String[] validTypes = {
                "MRI machine", "patient on stretcher", "patient in wheelchair", "patient in bed"
        };

        public String[] validMedicineTypes = {"Advil", "Penicillin", "Potassium", "Dopamine"};
        public String[] validInstructions = {
                "Take 2 twice a day",
                "Take with food",
                "Don't operate heavy machinery after taking",
                "Take 1 once a day"
        };

        public String[] validRecipientNames = {"Reilly Norum", "Matt Olson", "Adi Oliver", "Brady Norum"};
        public Integer[] validRoomNumbers = {5678, 78, 5, 943};
        public String[] validMessages = {
                "Hope you feel better soon!",
                "Hope these flowers make your day a little brighter!",
                "Congratulations on the new baby! Can't wait to meet her! Best wishes.",
                "Don't die."
        };
        public String[] validBuyerNames = {"Ollie Norum", "Reilly Norum", "Bob Johnson", "Griffin Norum"};
        public String[] validPhoneNumber = {"7743121480", "5088297547", "7745768349", "8679045674"};
        //  public Boolean[] validGiftWrap = {true, false};
        public String[] validBouquetType = {
                "All Occasion Classic", "Classic Dozen", "Contemporary Orchids", "Dish Garden"
        };
        // complete boolean

        public TestData() throws Exception {}
}
