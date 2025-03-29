package seedu.address.logic.commands.create;

import static seedu.address.logic.EventMessages.MESSAGE_DUPLICATE_EVENT;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_START_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds an {@code Event} to the app.
 */
public class AddEventCommand extends AddCommand<Event> {

    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds an event to the app. "
            + "Parameters: "
            + PREFIX_EVENT_NAME_LONG + " NAME "
            + PREFIX_EVENT_START_LONG + " START_DATETIME "
            + PREFIX_EVENT_END_LONG + " END_DATETIME "
            + PREFIX_EVENT_LOCATION_LONG + " LOCATION "
            + "[" + PREFIX_EVENT_TAG_LONG + " TAG]...\n"
            + "Example: " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME_LONG + " CS2040S tutorial "
            + PREFIX_EVENT_START_LONG + " 24-08-26 12:00 "
            + PREFIX_EVENT_END_LONG + " 24-08-26 14:00 "
            + PREFIX_EVENT_LOCATION_LONG + " NUS SoC COM1 "
            + PREFIX_EVENT_TAG_LONG + " weaker ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";

    /**
     * Creates an AddEventCommand to add the specified {@code event}.
     */
    public AddEventCommand(Event event) {
        super(event, Model::getEventManagerAndList);
    }

    @Override
    public String getDuplicateItemMessage() {
        return MESSAGE_DUPLICATE_EVENT;
    }

    @Override
    public String getSuccessMessage(Event event) {
        return String.format(MESSAGE_SUCCESS, Messages.format(event));
    }

}
