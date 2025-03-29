package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.BYE_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EXIT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.HELP_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.KILL_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.QUIT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.contact.ContactParser;
import seedu.address.logic.parser.event.EventParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.todo.TodoParser;

/**
 * Parses user input.
 */
public class ParserImpl {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)" + "(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(ParserImpl.class);

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e.,
        // FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case EXIT_COMMAND_WORD, BYE_COMMAND_WORD, QUIT_COMMAND_WORD, KILL_COMMAND_WORD:
            return new ExitCommand();

        case HELP_COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        case CONTACT_COMMAND_WORD:
            return new ContactParser().parseCommand(arguments);

        case TODO_COMMAND_WORD:
            return new TodoParser().parseCommand(arguments);

        case EVENT_COMMAND_WORD:
            return new EventParser().parseCommand(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
