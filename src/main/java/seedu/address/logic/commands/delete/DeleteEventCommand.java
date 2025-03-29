package seedu.address.logic.commands.delete;

import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Deletes an event identified using its displayed index.
 */
public class DeleteEventCommand extends DeleteCommand<Event> {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the event indentified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Examples " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_EVENT_DISPLAY_INDEX = "The event index provided is invalid.";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted event: %1$s";

    /**
     * Creates a {@code DeleteEventCommand} to delete an {@code Event} at the specified
     * {@code targetIndex}.
     *
     * @throws NullPointerException if {@code targetIndex} is {@code null}.
     */
    public DeleteEventCommand(Index targetIndex) {
        super(targetIndex, Model::getEventManagerAndList);
    }

    @Override
    public String getInvalidIndexMessage() {
        return MESSAGE_INVALID_EVENT_DISPLAY_INDEX;
    }

    @Override
    public String getSuccessMessage(Event event) {
        return String.format(MESSAGE_DELETE_EVENT_SUCCESS, Messages.format(event));
    }
}
