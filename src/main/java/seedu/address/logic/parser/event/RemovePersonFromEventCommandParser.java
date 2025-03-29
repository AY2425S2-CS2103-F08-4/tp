package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_PERSON_LONG;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EventMessages;
import seedu.address.logic.commands.event.RemovePersonFromEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemovePersonFromEventCommandParser object.
 */
public class RemovePersonFromEventCommandParser implements Parser<RemovePersonFromEventCommand> {
    @Override
    public RemovePersonFromEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TODO_LINKED_PERSON_LONG);
        if (!argMultimap.arePrefixesPresent(PREFIX_TODO_LINKED_PERSON_LONG)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemovePersonFromEventCommand.MESSAGE_USAGE));
        }
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemovePersonFromEventCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TODO_LINKED_PERSON_LONG);
        List<Index> personIndices =
                ParserUtil.parseIndices(argMultimap.getValue(PREFIX_TODO_LINKED_PERSON_LONG).get());

        if (personIndices.isEmpty()) {
            throw new ParseException(EventMessages.MESSAGE_NOT_REMOVED);
        }

        return new RemovePersonFromEventCommand(index, personIndices);
    }
}
