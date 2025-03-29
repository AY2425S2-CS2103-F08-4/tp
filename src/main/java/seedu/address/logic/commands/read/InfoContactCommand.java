package seedu.address.logic.commands.read;

import static seedu.address.logic.Messages.MESSAGE_DISPLAY_SPECIFIC_CONTACT_INFO;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Finds and lists all contacts in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class InfoContactCommand extends InfoCommand<Contact> {

    public static final String COMMAND_WORD = "info";

    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD + ": Displays the complete "
        + "information belonging to the contact identified by the index number used in the displayed contact list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " 2";

    public InfoContactCommand(Index targetIndex) {
        super(targetIndex, Model::getContactManagerAndList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InfoContactCommand otherInfoCommand)) {
            return false;
        }

        return index.equals(otherInfoCommand.index);
    }


    @Override
    public String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX;
    }

    @Override
    public String getInformationMessage(Contact contactToDisplay) {
        return String.format(MESSAGE_DISPLAY_SPECIFIC_CONTACT_INFO, contactToDisplay.getName());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .toString();
    }
}
