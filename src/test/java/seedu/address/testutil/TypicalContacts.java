package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;

/**
 * A utility class containing a list of {@code Contact} objects to be used in tests.
 */
public class TypicalContacts {

    public static final Contact ALICE = new ContactBuilder()
            .withId("A00000001")
            .withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withCourse("CS50")
            .withGroup("T33")
            .withTags("friends").build();
    public static final Contact BENSON = new ContactBuilder()
            .withId("A00000002")
            .withName("Benson Meier")
            .withEmail("johnd@example.com")
            .withCourse("CS50")
            .withGroup("T34")
            .withTags("owesMoney", "friends").build();
    public static final Contact CARL = new ContactBuilder()
            .withId("A00000003")
            .withName("Carl Kurz")
            .withCourse("CS60")
            .withGroup("T33")
            .withEmail("heinz@example.com").build();
    public static final Contact DANIEL = new ContactBuilder()
            .withId("A00000004")
            .withName("Daniel Meier")
            .withEmail("cornelia@example.com")
            .withCourse("CS60")
            .withGroup("T34")
            .withTags("friends").build();
    public static final Contact ELLE = new ContactBuilder()
            .withId("A00000005")
            .withName("Elle Meyer")
            .withCourse("CS60")
            .withGroup("T33")
            .withEmail("werner@example.com").build();
    public static final Contact FIONA = new ContactBuilder()
            .withId("A00000006")
            .withName("Fiona Kunz")
            .withCourse("CS70")
            .withGroup("T33")
            .withEmail("lydia@example.com").build();
    public static final Contact GEORGE = new ContactBuilder()
            .withId("A00000007")
            .withName("George Best")
            .withCourse("CS60")
            .withGroup("T34")
            .withEmail("anna@example.com").build();

    // Manually added
    public static final Contact HOON = new ContactBuilder()
            .withId("A00000008")
            .withName("Hoon Meier")
            .withCourse("CS80")
            .withGroup("T33")
            .withEmail("stefan@example.com").build();
    public static final Contact IDA = new ContactBuilder()
            .withId("A00000009")
            .withName("Ida Mueller")
            .withCourse("CS80")
            .withGroup("T34")
            .withEmail("hans@example.com").build();

    // Manually added - Contact's details found in {@code CommandTestUtil}
    public static final Contact AMY = new ContactBuilder()
            .withId(VALID_ID_AMY)
            .withName(VALID_NAME_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withCourse(VALID_COURSE_AMY)
            .withGroup(VALID_GROUP_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Contact BOB = new ContactBuilder()
            .withId(VALID_ID_BOB)
            .withName(VALID_NAME_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withCourse(VALID_COURSE_BOB)
            .withGroup(VALID_GROUP_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalContacts() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical contacts.
     */
    public static ContactManager getTypicalAddressBook() {
        ContactManager ab = new ContactManager();
        for (Contact contact : getTypicalContacts()) {
            ab.addItem(contact);
        }
        return ab;
    }

    public static List<Contact> getTypicalContacts() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
