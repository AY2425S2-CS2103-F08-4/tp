package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_PERSON_LONG;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EventMessages;
import seedu.address.logic.commands.event.RemovePersonFromLogEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemovePersonFromLogEventCommandParser object.
 */
public class RemovePersonFromLogEventCommandParser implements Parser<RemovePersonFromLogEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddPersonToLogEventCommandParser and returns a AddPersonToLogEventCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RemovePersonFromLogEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_LINKED_PERSON_LONG);
        if (!argMultimap.arePrefixesPresent(PREFIX_EVENT_LINKED_PERSON_LONG)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemovePersonFromLogEventCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemovePersonFromLogEventCommand.MESSAGE_USAGE), pe);
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_LINKED_PERSON_LONG);
        List<Index> personIndices = ParserUtil.parseIndices(
            argMultimap.getValue(PREFIX_EVENT_LINKED_PERSON_LONG).get());
        if (personIndices.isEmpty()) {
            throw new ParseException(EventMessages.MESSAGE_NOT_REMOVED);
        }
        return new RemovePersonFromLogEventCommand(index, personIndices);
    }
}
