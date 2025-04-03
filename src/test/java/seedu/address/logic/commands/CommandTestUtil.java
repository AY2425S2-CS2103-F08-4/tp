package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.read.FilterCommand;
import seedu.address.logic.commands.read.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;
import seedu.address.ui.ListPanelViewType;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        ListPanelViewType viewType = ListPanelViewType.NONE;

        // Check if the command has a getListPanelViewType method
        if (command instanceof ItemCommand) {
            if (command instanceof ListCommand) {
                viewType = ((ListCommand<?, ?>) command).getListPanelViewType();
            } else if (command instanceof FilterCommand) {
                viewType = ((FilterCommand<?, ?>) command).getListPanelViewType();
            }
        }

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, viewType);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);

    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered contact list and selected contact in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ContactManager expectedContactManager =
                new ContactManager(actualModel.getContactManagerAndList().getItemManager());
        List<Contact> expectedFilteredList =
                new ArrayList<>(actualModel.getContactManagerAndList().getFilteredItemsList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedContactManager, actualModel.getContactManagerAndList().getItemManager());
        assertEquals(expectedFilteredList,
                actualModel.getContactManagerAndList().getFilteredItemsList());
    }

}
