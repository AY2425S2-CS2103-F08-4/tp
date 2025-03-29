package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_CONTACT_LONG;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EventMessages;
import seedu.address.logic.commands.update.AddContactToEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddContactToEventCommandParser object
 */
public class AddContactToEventCommandParser implements Parser<AddContactToEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddContactToEventCommandParser and returns a AddContactToEventCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddContactToEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TODO_LINKED_CONTACT_LONG);
        if (!argMultimap.arePrefixesPresent(PREFIX_TODO_LINKED_CONTACT_LONG)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddContactToEventCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddContactToEventCommand.MESSAGE_USAGE), pe);
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TODO_LINKED_CONTACT_LONG);
        List<Index> contactIndices =
                ParserUtil.parseIndices(argMultimap.getValue(PREFIX_TODO_LINKED_CONTACT_LONG).get());
        if (contactIndices.isEmpty()) {
            throw new ParseException(EventMessages.MESSAGE_NOT_REMOVED);
        }
        return new AddContactToEventCommand(index, contactIndices);
    }
}
