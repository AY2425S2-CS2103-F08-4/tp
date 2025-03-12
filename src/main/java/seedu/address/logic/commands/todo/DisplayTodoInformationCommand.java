package seedu.address.logic.commands.todo;

import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.DisplayInformationCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;

/**
 * Displays a todo's information identified using its displayed index.
 */
public class DisplayTodoInformationCommand extends DisplayInformationCommand<Todo> {

    public static final String COMMAND_WORD = "info";

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Displays the complete information belonging to the todo identified by the index "
            + "number used in the displayed todo list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_TODO_DISPLAYED_INDEX =
            "The todo index provided is invalid";
    public static final String MESSAGE_DISPLAY_INFO = "%1$s";

    /**
     * Creates a {@code DisplayTodoInformationCommand} to display information of the {@code Todo} at
     * the specified {@code index}.
     * @throws NullPointerException if {@code index} is {@code null}.
     */
    public DisplayTodoInformationCommand(Index index) {
        super(index, Model::getTodoManagerAndList);
    }

    @Override
    public String getInvalidIndexMessage() {
        return MESSAGE_INVALID_TODO_DISPLAYED_INDEX;
    }

    @Override
    public String getInformationMessage(Todo itemToDisplay) {
        return String.format(MESSAGE_DISPLAY_INFO, Messages.format(itemToDisplay));
    }
}
