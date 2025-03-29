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
 * Adds a tag to a Contact in contact.
 */
public class AddTagToContactCommand extends EditCommand<Contact> {
    public static final String COMMAND_WORD = "tag";
    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a tag to a specified contact.\n"
            + "Parameters: INDEX " + PREFIX_CONTACT_TAG_LONG + " <tag>\n"
            + "Example: " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_CONTACT_TAG_LONG + " TA";
    public static final String MESSAGE_ADD_TAG_SUCCESSFUL = "Added tag to contact: %1$s";
    public static final String MESSAGE_DUPLICATE_TAGS = "The tag is already assigned to this contact";
    private final Tag tag;

    /**
     * Creates an EditCommand to add a tag to the specified {@code Contact}
     */
    public AddTagToContactCommand(Index index, Tag tag) {
        super(index, Model::getContactManagerAndList);
        requireNonNull(tag);
        this.tag = tag;
    }

    @Override
    public Contact createEditedItem(Model model, Contact contactToEdit) throws CommandException {
        if (contactToEdit.getTags().contains(tag)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TAGS));
        }
        Set<Tag> newTags = new HashSet<>(contactToEdit.getTags());
        newTags.add(tag);
        return new Contact(
                contactToEdit.getId(),
                contactToEdit.getName(),
                contactToEdit.getEmail(),
                contactToEdit.getCourse(),
                contactToEdit.getGroup(),
                Set.copyOf(newTags));
    }

    @Override
    public String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX;
    }

    @Override
    public String getDuplicateMessage() {
        return MESSAGE_DUPLICATE_TAGS;
    }

    @Override
    public String getSuccessMessage(Contact editedContact) {
        return String.format(MESSAGE_ADD_TAG_SUCCESSFUL, Messages.format(editedContact));
    }
}
