package seedu.address.logic.parser.todo;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.MarkTodoAsDoneCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkTodoAsDoneCommand object
 */
public class MarkTodoAsDoneCommandParser implements Parser<MarkTodoAsDoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkTodoAsDoneCommand and
     * returns a MarkTodoAsDoneCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkTodoAsDoneCommand parse(String args) throws ParseException {
        Index index = ParserUtil.parseIndex(args);
        return new MarkTodoAsDoneCommand(index);
    }

}
