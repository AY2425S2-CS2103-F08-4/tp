package seedu.address.logic.commands.read;

import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;

import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Lists all contacts in the address book to the user.
 */
public class ListContactCommand extends ListCommand<Contact> {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD + ": Lists all contacts.";

    public static final String MESSAGE_SUCCESS = "Listed all contacts";

    /**
     * Creates a {@code ListContactCommand}.
     */
    public ListContactCommand() {
        super(Model::getContactManagerAndList);
    }

    @Override
    public String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }
}
