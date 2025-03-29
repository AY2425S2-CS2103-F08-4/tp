package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.item.ItemManagerWithFilteredList;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoManagerWithFilteredList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;

    private final ItemManagerWithFilteredList<Contact> contactManagerAndList;
    private final ItemManagerWithFilteredList<Todo> todoManagerAndList;
    private final ItemManagerWithFilteredList<Event> eventManagerAndList;

    /**
     * Initializes a ModelManager with the given managers with lists and userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs,
                        ItemManagerWithFilteredList<Contact> contactManagerAndList,
                        ItemManagerWithFilteredList<Todo> todoManagerAndList,
                        ItemManagerWithFilteredList<Event> eventManagerAndList) {
        requireAllNonNull(userPrefs, contactManagerAndList, todoManagerAndList, eventManagerAndList);

        logger.fine("Initializing with contact manager: " + contactManagerAndList
                + ", todo manager: " + todoManagerAndList
                + ", event manager: " + eventManagerAndList
                + ", and user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        this.contactManagerAndList = contactManagerAndList;
        this.todoManagerAndList = todoManagerAndList;
        this.eventManagerAndList = eventManagerAndList;
    }

    /**
     * Initializes a default ModelManager.
     */
    public ModelManager() {
        this(new UserPrefs(), new ContactManagerWithFilteredList(),
                new TodoManagerWithFilteredList(), new EventManagerWithFilteredList());
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getTodoListFilePath() {
        return userPrefs.getTodoListFilePath();
    }

    @Override
    public void setTodoListFilePath(Path todoListFilePath) {
        requireNonNull(todoListFilePath);
        userPrefs.setTodoListFilePath(todoListFilePath);
    }

    @Override
    public Path getEventListFilePath() {
        return userPrefs.getEventListFilePath();
    }

    @Override
    public void setEventListFilePath(Path eventListFilePath) {
        requireNonNull(eventListFilePath);
        userPrefs.setEventListFilePath(eventListFilePath);
    }

    @Override
    public ItemManagerWithFilteredList<Contact> getContactManagerAndList() {
        return contactManagerAndList;
    }

    @Override
    public ItemManagerWithFilteredList<Todo> getTodoManagerAndList() {
        return todoManagerAndList;
    }

    @Override
    public ItemManagerWithFilteredList<Event> getEventManagerAndList() {
        return eventManagerAndList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager otherModelManager)) {
            return false;
        }

        return userPrefs.equals(otherModelManager.userPrefs)
                && contactManagerAndList.equals(otherModelManager.contactManagerAndList)
                && todoManagerAndList.equals(otherModelManager.todoManagerAndList)
                && eventManagerAndList.equals(otherModelManager.eventManagerAndList);
    }

}
