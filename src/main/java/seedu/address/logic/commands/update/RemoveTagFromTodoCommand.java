package seedu.address.logic.commands.update;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_TAG_LONG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.TodoMessages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Tag;
import seedu.address.model.todo.Todo;

/**
 * Remove a tag from a specified todo
 */
public class RemoveTagFromTodoCommand extends EditCommand<Todo> {
    public static final String COMMAND_WORD = "untag";
    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Removes a tag from a specified todo.\n"
            + "Parameters: INDEX "
            + PREFIX_TODO_TAG_LONG + " <tag>\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TODO_TAG_LONG + " important ";
    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed tag from todo: %1$s";
    public static final String MESSAGE_NO_TAG_PRESENT = "The tag is already removed from this todo.";

    private final Tag tag;

    /**
     * Creates a RemoveTagFromTodoCommand to remove tags from a todo at a specified index.
     */
    public RemoveTagFromTodoCommand(Index index, Tag tag) {
        super(index, Model::getTodoManagerAndList);
        requireNonNull(tag);
        this.tag = tag;
    }

    @Override
    public Todo createEditedItem(Model model, Todo todoToEdit) throws CommandException {
        if (!todoToEdit.getTags().contains(tag)) {
            throw new CommandException(String.format(MESSAGE_NO_TAG_PRESENT));
        }
        Set<Tag> newTags = new HashSet<>(todoToEdit.getTags());
        newTags.remove(this.tag);
        return new Todo(
                todoToEdit.getName(),
                todoToEdit.getDeadline(),
                todoToEdit.getLocation(),
                todoToEdit.getStatus(),
                todoToEdit.getContacts(),
                Set.copyOf(newTags)
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
    public String getSuccessMessage(Todo editedItem) {
        return String.format(MESSAGE_REMOVE_TAG_SUCCESS, Messages.format(editedItem));
    }
}
