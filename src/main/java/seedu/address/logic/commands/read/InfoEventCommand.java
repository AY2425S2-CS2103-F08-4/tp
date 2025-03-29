package seedu.address.logic.commands.read;

import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Displays an event's information identified using its displayed index.
 */
public class InfoEventCommand extends InfoCommand<Event> {

    public static final String COMMAND_WORD = "info";

    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Displays the complete information belonging to the event identified by the index "
            + "number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX =
            "The event index provided is invalid";
    public static final String MESSAGE_DISPLAY_INFO = "%1$s";

    /**
     * Creates a {@code InfoEventCommand} to display information of the {@code Event} at
     * the specified {@code index}.
     * @throws NullPointerException if {@code index} is {@code null}.
     */
    public InfoEventCommand(Index index) {
        super(index, Model::getEventManagerAndList);
    }

    @Override
    public String getInvalidIndexMessage() {
        return MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
    }

    @Override
    public String getInformationMessage(Event itemToDisplay) {
        return String.format(MESSAGE_DISPLAY_INFO, Messages.format(itemToDisplay));
    }
}
