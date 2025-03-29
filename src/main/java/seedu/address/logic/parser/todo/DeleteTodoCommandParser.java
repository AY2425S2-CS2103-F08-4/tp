package seedu.address.logic.parser.todo;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.delete.DeleteTodoCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTodoCommand object
 */
public class DeleteTodoCommandParser implements Parser<DeleteTodoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand and returns
     * a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTodoCommand parse(String args) throws ParseException {
        Index index = ParserUtil.parseIndex(args);
        return new DeleteTodoCommand(index);
    }

}
