package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;

import seedu.address.logic.commands.create.AddTodoCommand;
import seedu.address.model.todo.Todo;

/**
 * A utility class for Todo.
 */
public class TodoUtil {

    /**
     * Returns an add command string for adding the {@code todo}.
     */
    public static String getAddCommand(Todo todo) {
        return TODO_COMMAND_WORD + " " + AddTodoCommand.COMMAND_WORD + " " + getTodoDetails(todo);
    }

    /**
     * Returns the part of command string for the given {@code todo}'s details.
     */
    public static String getTodoDetails(Todo todo) {
        String sb = PREFIX_TODO_NAME_LONG + todo.getName().name + " "
                + PREFIX_TODO_DEADLINE_LONG + todo.getDeadline().toString() + " "
                + PREFIX_TODO_LOCATION_LONG + todo.getLocation().value + " ";
        return sb;
    }
}
