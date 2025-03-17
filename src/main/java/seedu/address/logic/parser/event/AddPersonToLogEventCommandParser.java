package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_LINKED_PERSON_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.AddPersonToLogEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddPersonToLogEventCommandParser object.
 */
public class AddPersonToLogEventCommandParser implements Parser<AddPersonToLogEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddPersonToLogEventCommandParser and returns a AddPersonToLogEventCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddPersonToLogEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LINKED_PERSON_INDEX);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPersonToLogEventCommand.MESSAGE_USAGE), pe);
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LINKED_PERSON_INDEX);
        List<Index> personIndices =
                ParserUtil.parseIndices(argMultimap.getValue(PREFIX_LINKED_PERSON_INDEX).get());
        if (personIndices.isEmpty()) {
            throw new ParseException(AddPersonToLogEventCommand.MESSAGE_NOT_REMOVED);
        }
        return new AddPersonToLogEventCommand(index, personIndices);
    }
}
