package seedu.address.logic.commands.contact;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactManager;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.TodoManagerWithFilteredList;

public class ClearContactCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearContactCommand(), model, ClearContactCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(getTypicalAddressBook()),
                new TodoManagerWithFilteredList(),
                new EventManagerWithFilteredList()
        );
        Model expectedModel = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(getTypicalAddressBook()),
                new TodoManagerWithFilteredList(),
                new EventManagerWithFilteredList()
        );
        expectedModel.getContactManagerAndList().setItemManager(new ContactManager());

        assertCommandSuccess(new ClearContactCommand(), model, ClearContactCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
