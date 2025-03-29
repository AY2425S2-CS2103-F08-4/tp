package seedu.address.logic.parser.contact;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.read.InfoContactCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new InfoCommand object
 */
public class InfoContactCommandParser implements Parser<InfoContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the InfoCommand and returns an
     * InfoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public InfoContactCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new InfoContactCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            InfoContactCommand.MESSAGE_USAGE), pe);
        }
    }
}
