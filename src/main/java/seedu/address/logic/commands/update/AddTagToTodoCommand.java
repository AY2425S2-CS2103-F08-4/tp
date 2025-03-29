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
 * Adds a tag to a specified todo
 */
public class AddTagToTodoCommand extends EditCommand<Todo> {
    public static final String COMMAND_WORD = "tag";
    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a tag to a specified todo.\n"
            + "Parameters: INDEX "
            + PREFIX_TODO_TAG_LONG + " <tag>\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TODO_TAG_LONG + " important ";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag to todo: %1$s";
    public static final String MESSAGE_DUPLICATE_TAGS = "The tag are already assigned to this todo";

    private final Tag tag;

    /**
     * Creates a AddTagToTodoCommand to add tags to a todo at a specific index.
     */
    public AddTagToTodoCommand(Index index, Tag tag) {
        super(index, Model::getTodoManagerAndList);
        requireNonNull(tag);
        this.tag = tag;
    }

    @Override
    public Todo createEditedItem(Model model, Todo todoToEdit) throws CommandException {
        if (todoToEdit.getTags().contains(tag)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TAGS));
        }
        Set<Tag> newTags = new HashSet<>(todoToEdit.getTags());
        newTags.add(this.tag);
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
        return String.format(MESSAGE_ADD_TAG_SUCCESS, Messages.format(editedItem));
    }
}
