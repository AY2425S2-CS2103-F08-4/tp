package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactBuilder;

public class ContactManagerTest {

    private final ContactManager contactManager = new ContactManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), contactManager.getItemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> contactManager.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        ContactManager newData = getTypicalAddressBook();
        contactManager.resetData(newData);
        assertEquals(newData, contactManager);
    }

    @Test
    public void hasContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> contactManager.hasItem(null));
    }

    @Test
    public void hasContact_contactNotInAddressBook_returnsFalse() {
        assertFalse(contactManager.hasItem(ALICE));
    }

    @Test
    public void hasContact_contactInAddressBook_returnsTrue() {
        contactManager.addItem(ALICE);
        assertTrue(contactManager.hasItem(ALICE));
    }

    @Test
    public void hasContact_contactWithSameIdentityFieldsInAddressBook_returnsTrue() {
        contactManager.addItem(ALICE);
        Contact editedAlice = new ContactBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(contactManager.hasItem(editedAlice));
    }

    @Test
    public void getContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                contactManager.getItemList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = ContactManager.class.getCanonicalName()
                + "{contacts=" + contactManager.getItemList() + "}";
        assertEquals(expected, contactManager.toString());
    }
}
