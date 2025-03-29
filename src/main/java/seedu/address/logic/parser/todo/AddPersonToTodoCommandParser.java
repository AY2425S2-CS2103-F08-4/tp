package seedu.address.logic.parser.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.TodoMessages.MESSAGE_MISSING_PERSON_INDEX;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_PERSON_LONG;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.todo.AddPersonToTodoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddPersonToTodoCommandParser object
 */
public class AddPersonToTodoCommandParser implements Parser<AddPersonToTodoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddPersonToTodoCommandParser and returns a AddPersonToTodoCommandParser object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPersonToTodoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TODO_LINKED_PERSON_LONG);

        if (!argMultimap.arePrefixesPresent(PREFIX_TODO_LINKED_PERSON_LONG)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPersonToTodoCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TODO_LINKED_PERSON_LONG);
        List<Index> personIndices =
                ParserUtil.parseIndices(argMultimap.getValue(PREFIX_TODO_LINKED_PERSON_LONG).get());

        if (personIndices.isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_PERSON_INDEX);
        }

        return new AddPersonToTodoCommand(index, personIndices);
    }

}
