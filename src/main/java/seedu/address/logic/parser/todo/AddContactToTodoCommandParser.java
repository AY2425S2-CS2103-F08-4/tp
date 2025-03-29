package seedu.address.logic.parser.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.TodoMessages.MESSAGE_MISSING_CONTACT_INDEX;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_CONTACT_LONG;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.todo.AddContactToTodoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddContactToTodoCommandParser object
 */
public class AddContactToTodoCommandParser implements Parser<AddContactToTodoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddContactToTodoCommandParser and returns a AddContactToTodoCommandParser object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddContactToTodoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TODO_LINKED_CONTACT_LONG);

        if (!argMultimap.arePrefixesPresent(PREFIX_TODO_LINKED_CONTACT_LONG)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddContactToTodoCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TODO_LINKED_CONTACT_LONG);
        List<Index> contactIndices =
                ParserUtil.parseIndices(argMultimap.getValue(PREFIX_TODO_LINKED_CONTACT_LONG).get());

        if (contactIndices.isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_CONTACT_INDEX);
        }

        return new AddContactToTodoCommand(index, contactIndices);
    }

}
