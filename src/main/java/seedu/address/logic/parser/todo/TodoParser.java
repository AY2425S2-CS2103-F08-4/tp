package seedu.address.logic.parser.todo;

import static seedu.address.logic.Messages.MESSAGE_INVALID_ARGUMENTS;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.update.AddContactToTodoCommand;
import seedu.address.logic.commands.update.AddTagToTodoCommand;
import seedu.address.logic.commands.create.AddTodoCommand;
import seedu.address.logic.commands.delete.ClearTodoCommand;
import seedu.address.logic.commands.delete.DeleteTodoCommand;
import seedu.address.logic.commands.read.InfoTodoCommand;
import seedu.address.logic.commands.read.FilterTodoCommand;
import seedu.address.logic.commands.read.ListTodoCommand;
import seedu.address.logic.commands.update.MarkTodoAsDoneCommand;
import seedu.address.logic.commands.update.MarkTodoAsNotDoneCommand;
import seedu.address.logic.commands.update.RemoveContactFromTodoCommand;
import seedu.address.logic.commands.update.RemoveTagFromTodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input for todo commands.
 */
public class TodoParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
        Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(TodoParser.class);

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

        case AddTodoCommand.COMMAND_WORD:
            return new AddTodoCommandParser().parse(arguments);

        case InfoTodoCommand.COMMAND_WORD:
            return new DisplayTodoInfoCommandParser().parse(arguments);

        case ListTodoCommand.COMMAND_WORD:
            return new ListTodoCommand();

        case DeleteTodoCommand.COMMAND_WORD:
            return new DeleteTodoCommandParser().parse(arguments);

        case AddContactToTodoCommand.COMMAND_WORD:
            return new AddContactToTodoCommandParser().parse(arguments);

        case RemoveContactFromTodoCommand.COMMAND_WORD:
            return new RemoveContactFromTodoCommandParser().parse(arguments);

        case MarkTodoAsDoneCommand.COMMAND_WORD:
            return new MarkTodoAsDoneCommandParser().parse(arguments);

        case MarkTodoAsNotDoneCommand.COMMAND_WORD:
            return new MarkTodoAsNotDoneCommandParser().parse(arguments);

        case AddTagToTodoCommand.COMMAND_WORD:
            return new AddTagToTodoCommandParser().parse(arguments);

        case RemoveTagFromTodoCommand.COMMAND_WORD:
            return new RemoveTagFromTodoCommandParser().parse(arguments);

        case ClearTodoCommand.COMMAND_WORD:
            return new ClearTodoCommand();

        case FilterTodoCommand.COMMAND_WORD:
            return new FilterTodoCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
