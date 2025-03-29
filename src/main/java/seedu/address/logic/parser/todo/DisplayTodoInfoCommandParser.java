package seedu.address.logic.parser.todo;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.read.DisplayTodoInformationCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new InfoCommand object
 */
public class DisplayTodoInfoCommandParser implements Parser<DisplayTodoInformationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the InfoCommand and returns an
     * InfoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayTodoInformationCommand parse(String args) throws ParseException {
        Index index = ParserUtil.parseIndex(args);
        return new DisplayTodoInformationCommand(index);
    }
}
