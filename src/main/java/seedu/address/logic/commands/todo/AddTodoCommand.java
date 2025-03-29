package seedu.address.logic.commands.todo;

import static seedu.address.logic.TodoMessages.MESSAGE_DUPLICATE_TODO;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.AddCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;

/**
 * Adds a todo.
 */
public class AddTodoCommand extends AddCommand<Todo> {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a todo to the app. "
            + "Parameters: "
            + PREFIX_TODO_NAME_LONG + " NAME "
            + PREFIX_TODO_DEADLINE_LONG + " PREFIX_TODO_DEADLINE "
            + PREFIX_TODO_LOCATION_LONG + " LOCATION\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_TODO_NAME_LONG + " Grading students projects "
            + PREFIX_TODO_DEADLINE_LONG + " 25-05-23 17:00 "
            + PREFIX_TODO_LOCATION_LONG + " NUS Science ";

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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTodoCommand otherAddTodoCommand)) {
            return false;
        }

        return itemToAdd.equals(otherAddTodoCommand.itemToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", itemToAdd)
                .toString();
    }
}
