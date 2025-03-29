package seedu.address.logic.parser.event;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.delete.DeleteEventCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Deletes an event identified using its displayed index.
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand and returns
     * a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEventCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteEventCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteEventCommand.MESSAGE_USAGE), pe);
        }
    }
}
