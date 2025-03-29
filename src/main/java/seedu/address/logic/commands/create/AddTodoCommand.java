package seedu.address.logic.commands.create;

import static seedu.address.logic.TodoMessages.MESSAGE_DUPLICATE_TODO;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_TAG_LONG;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;

/**
 * Adds a {@code Todo} to the app.
 */
public class AddTodoCommand extends AddCommand<Todo> {

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a todo to the app. "
            + "Parameters: "
            + PREFIX_TODO_NAME_LONG + " NAME "
            + PREFIX_TODO_DEADLINE_LONG + " DEADLINE "
            + PREFIX_TODO_LOCATION_LONG + " LOCATION "
            + "[" + PREFIX_TODO_TAG_LONG + " TAG]...\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_TODO_NAME_LONG + " Grading students projects "
            + PREFIX_TODO_DEADLINE_LONG + " 25-05-23 17:00 "
            + PREFIX_TODO_LOCATION_LONG + " NUS Science "
            + PREFIX_TODO_TAG_LONG + " important ";

    public static final String MESSAGE_SUCCESS = "New todo added: %1$s";

    /**
     * Creates an AddTodoCommand to add the specified {@code todo}.
     */
    public AddTodoCommand(Todo todo) {
        super(todo, Model::getTodoManagerAndList);
    }

    @Override
    public String getDuplicateItemMessage() {
        return MESSAGE_DUPLICATE_TODO;
    }

    @Override
    public String getSuccessMessage(Todo todo) {
        return String.format(MESSAGE_SUCCESS, Messages.format(todo));
    }

}
