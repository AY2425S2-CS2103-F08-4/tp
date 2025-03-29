package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.item.ItemManager;
import seedu.address.model.todo.Todo;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     */
    ItemManager<Contact> getAddressBook();

    /**
     * Returns the Todo list.
     */
    ItemManager<Todo> getTodoList();

    /**
     * Returns the Event list.
     */
    ItemManager<Event> getEventList();

    /**
     * Returns an unmodifiable view of the filtered list of contacts
     */
    ObservableList<Contact> getFilteredContactList();

    /**
     * Returns an unmodifiable view of the filtered list of todos
     */
    ObservableList<Todo> getFilteredTodoList();

    /**
     * Returns an unmodifiable view of the filtered list of events
     */
    ObservableList<Event> getFilteredEventList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' todo list file path.
     */
    Path getTodoListFilePath();

    /**
     * Returns the user prefs' event list file path.
     */
    Path getEventListFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
