package seedu.address.storage.contact;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;
import seedu.address.model.item.ItemManager;

/**
 * Represents a storage for {@link ContactManager}.
 */
public interface ContactStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ItemManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ItemManager<Contact>> readAddressBook() throws DataLoadingException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ItemManager<Contact>> readAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ItemManager} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ItemManager<Contact> addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ItemManager)
     */
    void saveAddressBook(ItemManager<Contact> addressBook, Path filePath) throws IOException;

}
