package seedu.address.storage.contact;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.contact.Contact;
import seedu.address.model.item.ItemManager;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonContactStorage implements ContactStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonContactStorage.class);

    private Path filePath;

    public JsonContactStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ItemManager<Contact>> readAddressBook() throws DataLoadingException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ItemManager<Contact>> readAddressBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableContactManager> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableContactManager.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveAddressBook(ItemManager<Contact> addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ItemManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveAddressBook(ItemManager<Contact> addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableContactManager(addressBook), filePath);
    }

}
