package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.contact.ContactManager;
import seedu.address.storage.contact.JsonSerializableContactManager;
import seedu.address.testutil.TypicalContacts;

public class JsonSerializableContactManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_CONTACTS_FILE = TEST_DATA_FOLDER.resolve("typicalContactsAddressBook.json");
    private static final Path INVALID_CONTACT_FILE = TEST_DATA_FOLDER.resolve("invalidContactAddressBook.json");
    private static final Path DUPLICATE_CONTACT_FILE = TEST_DATA_FOLDER.resolve("duplicateContactAddressBook.json");

    @Test
    public void toModelType_typicalContactsFile_success() throws Exception {
        JsonSerializableContactManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_CONTACTS_FILE,
                JsonSerializableContactManager.class).get();
        ContactManager contactManagerFromFile = dataFromFile.toModelType();
        ContactManager typicalContactsContactManager = TypicalContacts.getTypicalAddressBook();
        assertEquals(contactManagerFromFile, typicalContactsContactManager);
    }

    @Test
    public void toModelType_invalidContactFile_throwsIllegalValueException() throws Exception {
        JsonSerializableContactManager dataFromFile = JsonUtil.readJsonFile(INVALID_CONTACT_FILE,
                JsonSerializableContactManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateContacts_throwsIllegalValueException() throws Exception {
        JsonSerializableContactManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CONTACT_FILE,
                JsonSerializableContactManager.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableContactManager.MESSAGE_DUPLICATE_CONTACT,
                dataFromFile::toModelType);
    }

}
