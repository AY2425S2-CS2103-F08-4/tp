package seedu.address.logic.commands.contact;

import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.DeleteCommand;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Deletes a contact identified using it's displayed index from the address book.
 */
public class DeleteContactCommand extends DeleteCommand<Contact> {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD + ": Deletes the contact "
            + "identified by the index number used in the displayed contact list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted Contact: %1$s";

    public DeleteContactCommand(Index targetIndex) {
        super(targetIndex, Model::getContactManagerAndList);
    }


    @Override
    public String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX;
    }

    @Override
    public String getSuccessMessage(Contact contactToDelete) {
        return String.format(MESSAGE_DELETE_CONTACT_SUCCESS, Messages.format(contactToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteContactCommand otherDeleteCommand)) {
            return false;
        }

        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
