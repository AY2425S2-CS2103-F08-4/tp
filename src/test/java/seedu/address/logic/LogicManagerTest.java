package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.create.AddContactCommand;
import seedu.address.logic.commands.read.ListContactCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.item.ItemManager;
import seedu.address.model.todo.Todo;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.contact.JsonContactStorage;
import seedu.address.storage.event.JsonEventStorage;
import seedu.address.storage.todo.JsonTodoStorage;
import seedu.address.testutil.ContactBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private final Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonContactStorage addressBookStorage =
                new JsonContactStorage(temporaryFolder.resolve("addressBook.json"));
        JsonTodoStorage todoStorage =
                new JsonTodoStorage(temporaryFolder.resolve("todolist.json"));
        JsonEventStorage eventStorage =
                new JsonEventStorage(temporaryFolder.resolve("eventlist.json"));

        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, todoStorage, eventStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "contact delete 9";
        assertCommandException(deleteCommand);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = CONTACT_COMMAND_WORD + " " + ListContactCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListContactCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void getFilteredContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredContactList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand) {
        assertCommandFailure(inputCommand, ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand) {
        assertCommandFailure(inputCommand, CommandException.class, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(
                new UserPrefs(),
                model.getContactManagerAndList(),
                model.getTodoManagerAndList(),
                model.getEventManagerAndList()
        );
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with an AddressBookStorage that throws the IOException e when saving
        JsonContactStorage addressBookStorage = new JsonContactStorage(prefPath) {
            @Override
            public void saveAddressBook(ItemManager<Contact> addressBook, Path filePath)
                    throws IOException {
                throw e;
            }
        };
        JsonTodoStorage todoStorage = new JsonTodoStorage(prefPath) {
            @Override
            public void saveTodoList(ItemManager<Todo> todoManager, Path filePath)
                    throws IOException {
                throw e;
            }
        };
        JsonEventStorage eventStorage = new JsonEventStorage(prefPath) {
            @Override
            public void saveEventList(ItemManager<Event> eventManager, Path filePath)
                    throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, todoStorage, eventStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Triggers the saveAddressBook method by executing an add command
        String addCommand =
                CONTACT_COMMAND_WORD + " " + AddContactCommand.COMMAND_WORD + ID_DESC_AMY + NAME_DESC_AMY
                + EMAIL_DESC_AMY + COURSE_DESC_AMY + GROUP_DESC_AMY;
        Contact expectedContact = new ContactBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.getContactManagerAndList().addItem(expectedContact);
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }
}
