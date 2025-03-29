package seedu.address.logic.commands.create;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;
import seedu.address.model.event.Event;
import seedu.address.model.item.ItemManager;
import seedu.address.model.item.ItemManagerWithFilteredList;
import seedu.address.model.todo.Todo;
import seedu.address.testutil.ContactBuilder;

public class AddContactCommandTest {

    @Test
    public void constructor_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddContactCommand(null));
    }

    @Test
    public void execute_contactAcceptedByModel_addSuccessful() throws Exception {
        ContactManagerAndListStubAcceptingContactAdded managerAndListStub =
                new ContactManagerAndListStubAcceptingContactAdded();
        ModelStub modelStub = new ModelStub(managerAndListStub);
        Contact validContact = new ContactBuilder().build();

        CommandResult commandResult = new AddContactCommand(validContact).execute(modelStub);

        assertEquals(String.format(AddContactCommand.MESSAGE_SUCCESS, Messages.format(validContact)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validContact), managerAndListStub.contactsAdded);
    }

    @Test
    public void execute_duplicateContact_throwsCommandException() {
        Contact validContact = new ContactBuilder().build();
        AddContactCommand addContactCommand = new AddContactCommand(validContact);
        ModelStub modelStub = new ModelStub(new ContactManagerAndListStub(validContact));

        assertThrows(CommandException.class, AddContactCommand.MESSAGE_DUPLICATE_CONTACT, () ->
                addContactCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Contact alice = new ContactBuilder().withName("Alice").build();
        Contact bob = new ContactBuilder().withName("Bob").build();
        AddContactCommand addAliceCommand = new AddContactCommand(alice);
        AddContactCommand addBobCommand = new AddContactCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddContactCommand addAliceCommandCopy = new AddContactCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different contact -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddContactCommand addContactCommand = new AddContactCommand(ALICE);
        String expected = AddContactCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addContactCommand.toString());
    }

    /**
     * A default model stub.
     */
    private class ModelStub implements Model {
        private final ItemManagerWithFilteredList<Contact> managerAndList;

        private ModelStub(ItemManagerWithFilteredList<Contact> managerAndList) {
            this.managerAndList = managerAndList;
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTodoListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTodoListFilePath(Path todoListFilePath) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Path getEventListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEventListFilePath(Path eventListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ItemManagerWithFilteredList<Contact> getContactManagerAndList() {
            return managerAndList;
        }

        @Override
        public ItemManagerWithFilteredList<Todo> getTodoManagerAndList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ItemManagerWithFilteredList<Event> getEventManagerAndList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A ItemManagerWithFilteredList stub that contains a single contact.
     */
    private class ContactManagerAndListStub extends ItemManagerWithFilteredList<Contact> {
        private final Contact contact;

        ContactManagerAndListStub(Contact contact) {
            super(new ContactManager());
            requireNonNull(contact);
            this.contact = contact;
        }

        @Override
        public boolean hasItem(Contact contact) {
            requireNonNull(contact);
            return this.contact.isSameContact(contact);
        }
    }

    /**
     * A ItemManagerWithFilteredList stub that always accept the contact being added.
     */
    private class ContactManagerAndListStubAcceptingContactAdded
            extends ItemManagerWithFilteredList<Contact> {
        final ArrayList<Contact> contactsAdded = new ArrayList<>();

        public ContactManagerAndListStubAcceptingContactAdded() {
            super(new ContactManager());
        }

        @Override
        public boolean hasItem(Contact contact) {
            requireNonNull(contact);
            return contactsAdded.stream().anyMatch(contact::isSameContact);
        }

        @Override
        public void addItem(Contact contact) {
            requireNonNull(contact);
            contactsAdded.add(contact);
        }

        @Override
        public ItemManager<Contact> getItemManager() {
            return new ContactManager();
        }
    }

}
