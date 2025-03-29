package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
        + "Supports three main commands:\n"
        + CONTACT_COMMAND_WORD + ": Manages your contacts.\n"
        + TODO_COMMAND_WORD + ": Manages your todos.\n"
        + EVENT_COMMAND_WORD + ": Manages your events.";

    private final String helpMessage;

    /**
     * Constructs a new HelpCommand for a specified feature.
     *
     * @param helpMessage the help message to be displayed;
     */
    public HelpCommand(String helpMessage) {
        this.helpMessage = helpMessage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(helpMessage, false, false);
    }
}
