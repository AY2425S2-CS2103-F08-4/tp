package seedu.address.logic.parser.event;

import static seedu.address.logic.Messages.MESSAGE_INVALID_ARGUMENTS;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.event.AddContactToEventCommand;
import seedu.address.logic.commands.event.AddContactToLogEventCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.event.AddTagToEventCommand;
import seedu.address.logic.commands.event.ClearEventCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.event.DisplayEventInformationCommand;
import seedu.address.logic.commands.event.FilterEventCommand;
import seedu.address.logic.commands.event.ListEventCommand;
import seedu.address.logic.commands.event.RemoveContactFromEventCommand;
import seedu.address.logic.commands.event.RemoveContactFromLogEventCommand;
import seedu.address.logic.commands.event.RemoveTagFromEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input for event commands.
 */
public class EventParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
        Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(EventParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_ARGUMENTS, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e.,
        // FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case DisplayEventInformationCommand.COMMAND_WORD:
            return new DisplayEventInfoCommandParser().parse(arguments);

        case ListEventCommand.COMMAND_WORD:
            return new ListEventCommand();

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        case AddContactToEventCommand.COMMAND_WORD:
            return new AddContactToEventCommandParser().parse(arguments);

        case RemoveContactFromEventCommand.COMMAND_WORD:
            return new RemoveContactFromEventCommandParser().parse(arguments);

        case AddContactToLogEventCommand.COMMAND_WORD:
            return new AddContactToLogEventCommandParser().parse(arguments);

        case RemoveContactFromLogEventCommand.COMMAND_WORD:
            return new RemoveContactFromLogEventCommandParser().parse(arguments);

        case FilterEventCommand.COMMAND_WORD:
            return new FilterEventCommandParser().parse(arguments);

        case AddTagToEventCommand.COMMAND_WORD:
            return new AddTagToEventCommandParser().parse(arguments);

        case RemoveTagFromEventCommand.COMMAND_WORD:
            return new RemoveTagFromEventCommandParser().parse(arguments);

        case ClearEventCommand.COMMAND_WORD:
            return new ClearEventCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
