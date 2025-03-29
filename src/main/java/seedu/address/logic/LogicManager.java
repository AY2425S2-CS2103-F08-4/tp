package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserImpl;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.item.ItemManager;
import seedu.address.model.todo.Todo;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ParserImpl parser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        parser = new ParserImpl();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = parser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getContactManagerAndList().getItemManager());
            storage.saveTodoList(model.getTodoManagerAndList().getItemManager());
            storage.saveEventList(model.getEventManagerAndList().getItemManager());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ItemManager<Contact> getAddressBook() {
        return model.getContactManagerAndList().getItemManager();
    }

    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return model.getContactManagerAndList().getFilteredItemsList();
    }

    @Override
    public ItemManager<Todo> getTodoList() {
        return model.getTodoManagerAndList().getItemManager();
    }

    @Override
    public ItemManager<Event> getEventList() {
        return model.getEventManagerAndList().getItemManager();
    }

    @Override
    public ObservableList<Todo> getFilteredTodoList() {
        return model.getTodoManagerAndList().getFilteredItemsList();
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return model.getEventManagerAndList().getFilteredItemsList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public Path getTodoListFilePath() {
        return model.getTodoListFilePath();
    }

    @Override
    public Path getEventListFilePath() {
        return model.getEventListFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
