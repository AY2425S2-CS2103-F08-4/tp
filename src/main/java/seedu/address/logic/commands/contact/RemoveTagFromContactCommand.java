package seedu.address.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Tag;

/**
 * Removes a tag from a specified contact.
 */
public class RemoveTagFromContactCommand extends EditCommand<Contact> {
    public static final String COMMAND_WORD = "untag";
    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Removes a tag from a specified contact.\n"
            + "Parameters: INDEX " + PREFIX_CONTACT_TAG_LONG + " <tag>\n"
            + "Example: " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_CONTACT_TAG_LONG + " TA";
    public static final String MESSAGE_REMOVE_TAG_SUCCESSFUL = "Removed tag from contact: %1$s";
    public static final String MESSAGE_MISSING_TAG = "The tag is already removed from this contact";
    private final Tag tag;

    /**
     * Creates an EditCommand to remove a tag from a specified {@code Contact}.
     */
    public RemoveTagFromContactCommand(Index index, Tag tag) {
        super(index, Model::getContactManagerAndList);
        requireNonNull(tag);
        this.tag = tag;
    }

    @Override
    public Contact createEditedItem(Model model, Contact contactToEdit) throws CommandException {
        if (!contactToEdit.getTags().contains(tag)) {
            throw new CommandException(MESSAGE_MISSING_TAG);
        }
        Set<Tag> newTags = new HashSet<>(contactToEdit.getTags());
        newTags.remove(tag);
        return new Contact(
            contactToEdit.getId(),
            contactToEdit.getName(),
            contactToEdit.getEmail(),
            contactToEdit.getCourse(),
            contactToEdit.getGroup(),
            Set.copyOf(newTags)
        );
    }

    @Override
    public String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX;
    }

    @Override
    public String getDuplicateMessage() {
        return Messages.MESSAGE_DUPLICATE_FIELDS;
    }

    @Override
    public String getSuccessMessage(Contact editedContact) {
        return String.format(MESSAGE_REMOVE_TAG_SUCCESSFUL, Messages.format(editedContact));
    }
}
