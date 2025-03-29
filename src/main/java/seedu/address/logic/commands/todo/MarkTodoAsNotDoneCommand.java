package seedu.address.logic.commands.todo;

import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.TodoMessages;
import seedu.address.logic.abstractcommand.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoStatus;

/**
 * Mark a todo as not done.
 */
public class MarkTodoAsNotDoneCommand extends EditCommand<Todo> {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Mark a todo as not done.\n"
            + "Parameters: INDEX\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_TODO_ALREADY_NOT_DONE =
            "This todo is already marked as not done";
    public static final String MESSAGE_TODO_MARKED_NOT_DONE = "Marked as not done: %1$s";

    /**
     * Creates an {@code MarkTodoAsDoneCommand} to mark a todo as done at the specified {@code
     * index}.
     *
     * @throws NullPointerException if {@code index} is {@code null}.
     */
    public MarkTodoAsNotDoneCommand(Index index) {
        super(index, Model::getTodoManagerAndList);
    }

    @Override
    public Todo createEditedItem(Model model, Todo todoToEdit) throws CommandException {
        if (!todoToEdit.getStatus().isDone()) {
            throw new CommandException(MESSAGE_TODO_ALREADY_NOT_DONE);
        }

        return new Todo(
                todoToEdit.getName(),
                todoToEdit.getDeadline(),
                todoToEdit.getLocation(),
                new TodoStatus(false),
                todoToEdit.getContacts(),
                todoToEdit.getTags()
        );
    }

    @Override
    public String getInvalidIndexMessage() {
        return TodoMessages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX;
    }

    @Override
    public String getDuplicateMessage() {
        return TodoMessages.MESSAGE_DUPLICATE_TODO;
    }

    @Override
    public String getSuccessMessage(Todo todo) {
        return String.format(MESSAGE_TODO_MARKED_NOT_DONE, Messages.format(todo));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkTodoAsNotDoneCommand otherCommand)) {
            return false;
        }

        return index.equals(otherCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
