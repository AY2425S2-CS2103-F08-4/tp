
package seedu.address.logic.commands.delete;

import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.model.Model;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoManager;

/**
 * Clears the todo list.
 */
public class ClearTodoCommand extends ClearCommand<Todo> {
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD + ": Clears the todo list.";
    public static final String MESSAGE_SUCCESS = "Cleared todo list.";

    /**
     * Creates a {@code ClearTodoCommand} to clear the todo list in the Model.
     */
    public ClearTodoCommand() {
        super(Model::getTodoManagerAndList, TodoManager::new);
    }

    @Override
    public String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }
}
