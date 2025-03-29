package seedu.address.logic.commands.delete;

import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;

import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;

/**
 * Clears the event list.
 */
public class ClearEventCommand extends ClearCommand<Event> {
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD + ": Clears the event list.\n";
    public static final String MESSAGE_SUCCESS = "Cleared event list.";

    /**
     * Creates a {@code ClearEventCommand} to clear the event list in the Model.
     */
    public ClearEventCommand() {
        super(Model::getEventManagerAndList, EventManager::new);
    }

    @Override
    public String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }
}
