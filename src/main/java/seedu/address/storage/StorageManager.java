package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.item.ItemManager;
import seedu.address.model.todo.Todo;
import seedu.address.storage.contact.ContactStorage;
import seedu.address.storage.event.EventStorage;
import seedu.address.storage.todo.TodoStorage;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ContactStorage addressBookStorage;
    private TodoStorage todoStorage;
    private EventStorage eventStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(ContactStorage addressBookStorage,
                          TodoStorage todoStorage,
                          EventStorage eventStorage,
                          UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.todoStorage = todoStorage;
        this.eventStorage = eventStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ItemManager<Contact>> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ItemManager<Contact>> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ItemManager<Contact> addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ItemManager<Contact> addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }


    // ================ TodoList methods ==============================

    @Override
    public Path getTodoListFilePath() {
        return todoStorage.getTodoListFilePath();
    }

    @Override
    public Optional<ItemManager<Todo>> readTodoList() throws DataLoadingException {
        return readTodoList(todoStorage.getTodoListFilePath());
    }

    @Override
    public Optional<ItemManager<Todo>> readTodoList(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return todoStorage.readTodoList(filePath);
    }

    @Override
    public void saveTodoList(ItemManager<Todo> addressBook) throws IOException {
        saveTodoList(addressBook, todoStorage.getTodoListFilePath());
    }

    @Override
    public void saveTodoList(ItemManager<Todo> addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        todoStorage.saveTodoList(addressBook, filePath);
    }

    // =============== EventList methods ==============================

    @Override
    public Path getEventListFilePath() {
        return eventStorage.getEventListFilePath();
    }

    @Override
    public Optional<ItemManager<Event>> readEventList() throws DataLoadingException {
        return readEventList(eventStorage.getEventListFilePath());
    }

    @Override
    public Optional<ItemManager<Event>> readEventList(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eventStorage.readEventList(filePath);
    }

    @Override
    public void saveEventList(ItemManager<Event> addressBook) throws IOException {
        saveEventList(addressBook, eventStorage.getEventListFilePath());
    }

    @Override
    public void saveEventList(ItemManager<Event> addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eventStorage.saveEventList(addressBook, filePath);
    }

}
