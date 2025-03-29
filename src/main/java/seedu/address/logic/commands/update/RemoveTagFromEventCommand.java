package seedu.address.logic.commands.update;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EventMessages;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Tag;
import seedu.address.model.event.Event;

/**
 * Removes a tag from a specified event.
 */
public class RemoveTagFromEventCommand extends EditCommand<Event> {
    public static final String COMMAND_WORD = "untag";
    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Removes a tag from a specified event.\n"
            + "Parameters: INDEX "
            + PREFIX_EVENT_TAG_LONG + " <tag>\n"
            + "Example: " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_TAG_LONG + " TA ";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed tag from event: %1$s";
    public static final String MESSAGE_NO_TAG_PRESENT = "The tag is already removed from specified event.";

    private final Tag tag;

    /**
     * Creates a RemoveTagFromEventCommand to remove tags from a event at a specified index.
     */
    public RemoveTagFromEventCommand(Index index, Tag tag) {
        super(index, Model::getEventManagerAndList);
        requireNonNull(tag);
        this.tag = tag;
    }

    @Override
    public Event createEditedItem(Model model, Event eventToEdit) throws CommandException {
        if (!eventToEdit.getTags().contains(tag)) {
            throw new CommandException(String.format(MESSAGE_NO_TAG_PRESENT));
        }
        Set<Tag> newTags = new HashSet<>(eventToEdit.getTags());
        newTags.remove(this.tag);
        return new Event(
                eventToEdit.getName(),
                eventToEdit.getStartTime(),
                eventToEdit.getEndTime(),
                eventToEdit.getLocation(),
                eventToEdit.getContacts(),
                eventToEdit.getMarkedList(),
                Set.copyOf(newTags)
        );
    }

    @Override
    public String getInvalidIndexMessage() {
        return EventMessages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
    }

    @Override
    public String getDuplicateMessage() {
        return EventMessages.MESSAGE_DUPLICATE_EVENT;
    }

    @Override
    public String getSuccessMessage(Event editedItem) {
        return String.format(MESSAGE_REMOVE_TAG_SUCCESS, Messages.format(editedItem));
    }
}
