package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EventMessages;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Tag;

/**
 * Adds a tag to a specified event
 */
public class AddTagToEventCommand extends EditCommand<Event> {
    public static final String COMMAND_WORD = "tag";
    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a tag to a specified event.\n"
            + "Parameters: INDEX "
            + PREFIX_EVENT_TAG_LONG + " <tag>\n"
            + "Example: " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_TAG_LONG + " TA ";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag to event: %1$s";
    public static final String MESSAGE_DUPLICATE_TAGS = "The tag is already assigned to this event";

    private final Tag tag;

    /**
     * Creates a AddTagToEventCommmand to add tags to an event at a specific index.
     */
    public AddTagToEventCommand(Index index, Tag tag) {
        super(index, Model::getEventManagerAndList);
        requireNonNull(tag);
        this.tag = tag;
    }

    @Override
    public Event createEditedItem(Model model, Event eventToEdit) throws CommandException {
        if (eventToEdit.getTags().contains(tag)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TAGS));
        }
        Set<Tag> newTags = new HashSet<>(eventToEdit.getTags());
        newTags.add(this.tag);
        return new Event(
                eventToEdit.getName(),
                eventToEdit.getStartTime(),
                eventToEdit.getEndTime(),
                eventToEdit.getLocation(),
                eventToEdit.getPersons(),
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
        return String.format(MESSAGE_ADD_TAG_SUCCESS, Messages.format(editedItem));
    }
}
