package seedu.address.logic.parser.todo;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;

import seedu.address.logic.commands.create.AddTodoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoDeadline;
import seedu.address.model.todo.TodoLocation;
import seedu.address.model.todo.TodoName;

/**
 * Parses input arguments and creates a new AddTodoCommand object.
 */
public class AddTodoCommandParser implements Parser<AddTodoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddTodoCommand and returns
     * an AddTodoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddTodoCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TODO_NAME_LONG,
            PREFIX_TODO_DEADLINE_LONG, PREFIX_TODO_LOCATION_LONG);

        if (!argMultimap.arePrefixesPresent(PREFIX_TODO_NAME_LONG, PREFIX_TODO_DEADLINE_LONG,
            PREFIX_TODO_LOCATION_LONG) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTodoCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TODO_NAME_LONG, PREFIX_TODO_DEADLINE_LONG,
            PREFIX_TODO_LOCATION_LONG);
        TodoName name = TodoParserUtil.parseName(argMultimap.getValue(PREFIX_TODO_NAME_LONG).get());
        TodoDeadline deadline =
                TodoParserUtil.parseDeadline(argMultimap.getValue(PREFIX_TODO_DEADLINE_LONG).get());
        TodoLocation location =
                TodoParserUtil.parseLocation(argMultimap.getValue(PREFIX_TODO_LOCATION_LONG).get());

        Todo todo = new Todo(name, deadline, location);

        return new AddTodoCommand(todo);
    }
}
