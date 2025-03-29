package seedu.address.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_DISPLAY_SPECIFIC_CONTACT_INFO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showContactAtIndex;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.TodoManagerWithFilteredList;

/**
 * Contains integration tests (interaction with the Model) for {@code InfoContactCommand}.
 */
public class InfoContactCommandTest {
    private final Model model = new ModelManager(
            new UserPrefs(),
            new ContactManagerWithFilteredList(getTypicalAddressBook()),
            new TodoManagerWithFilteredList(),
            new EventManagerWithFilteredList()
    );
    private final Model expectedModel = new ModelManager(
            new UserPrefs(),
            new ContactManagerWithFilteredList(getTypicalAddressBook()),
            new TodoManagerWithFilteredList(),
            new EventManagerWithFilteredList()
    );

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Contact contactToShow =
                model.getContactManagerAndList().getFilteredItemsList().get(INDEX_FIRST.getZeroBased());
        InfoContactCommand infoCommand = new InfoContactCommand(INDEX_FIRST);

        String expectedMessage = String.format(MESSAGE_DISPLAY_SPECIFIC_CONTACT_INFO,
                contactToShow.getName());
        assertCommandSuccess(infoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getContactManagerAndList()
                .getFilteredItemsList().size() + 1);
        InfoContactCommand infoCommand = new InfoContactCommand(outOfBoundIndex);
        assertCommandFailure(infoCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showContactAtIndex(model, INDEX_FIRST);
        showContactAtIndex(expectedModel, INDEX_FIRST);
        Contact contactToShow = model.getContactManagerAndList()
                .getFilteredItemsList().get(INDEX_FIRST.getZeroBased());
        InfoContactCommand infoCommand = new InfoContactCommand(INDEX_FIRST);
        String expectedMessage = String.format(MESSAGE_DISPLAY_SPECIFIC_CONTACT_INFO,
                contactToShow.getName());
        assertCommandSuccess(infoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showContactAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getContactManagerAndList().getItemManager()
                .getItemList().size());
        InfoContactCommand infoCommand = new InfoContactCommand(outOfBoundIndex);
        assertCommandFailure(infoCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        InfoContactCommand showFirstCommand = new InfoContactCommand(INDEX_FIRST);
        InfoContactCommand showSecondCommand = new InfoContactCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values -> returns true
        InfoContactCommand showFirstCommandCopy = new InfoContactCommand(INDEX_FIRST);
        assertTrue(showFirstCommand.equals(showFirstCommandCopy));

        // different types -> returns false
        assertFalse(showFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));
    }

    @Test
    public void toStringMethod() {
        InfoContactCommand infoCommand = new InfoContactCommand(INDEX_FIRST);
        String expected =
                InfoContactCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST + "}";
        assertEquals(expected, infoCommand.toString());
    }
}
