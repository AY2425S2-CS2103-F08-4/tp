package seedu.address.logic.commands.delete;

import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;

import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;

/**
 * Clears the address book.
 */
public class ClearContactCommand extends ClearCommand<Contact> {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD + ": Clears the address book.";

    /**
     * Creates a {@code ClearContactCommand} to clear the contact list in the model.
     */
    public ClearContactCommand() {
        super(Model::getContactManagerAndList, ContactManager::new);
    }


    @Override
    public String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }
}
