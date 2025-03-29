package seedu.address.logic.commands.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_CONTACT_LONG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.TodoMessages;
import seedu.address.logic.abstractcommand.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.todo.Todo;

/**
 * Remove some associated contacts from a todo.
 */
public class RemoveContactFromTodoCommand extends EditCommand<Todo> {

    public static final String COMMAND_WORD = "unlink";

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Remove the association between a todo and some contacts.\n"
            + "Parameters: INDEX "
            + PREFIX_TODO_LINKED_CONTACT_LONG + " [CONTACT_INDEX]...\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TODO_LINKED_CONTACT_LONG + " 1 3 4";

    public static final String MESSAGE_REMOVE_CONTACT_SUCCESS = "Removed contacts from todo: %1$s";

    private final List<Index> contactIndices;

    /**
     * Creates an RemoveContactFromTodoCommand to remove the contacts at the specified {@code
     * contactIndices} from the list of contacts in the todo at the specified {@code index}.
     */
    public RemoveContactFromTodoCommand(Index index, List<Index> contactIndices) {
        super(index, Model::getTodoManagerAndList);
        requireNonNull(contactIndices);
        this.contactIndices = contactIndices;
    }

    @Override
    public Todo createEditedItem(Model model, Todo todoToEdit) throws CommandException {
        for (Index index : contactIndices) {
            if (index.getZeroBased() >= todoToEdit.getContacts().size()) {
                System.out.println();
                throw new CommandException(String.format(
                        TodoMessages.MESSAGE_INVALID_LINKED_CONTACT_INDEX, index.getOneBased()));
            }
        }

        List<Contact> newContacts = new ArrayList<>(todoToEdit.getContacts());
        contactIndices.stream()
                .map(Index::getZeroBased)
                .sorted(Comparator.reverseOrder())
                .forEach(index -> newContacts.remove((int) index));

        return new Todo(
                todoToEdit.getName(),
                todoToEdit.getDeadline(),
                todoToEdit.getLocation(),
                todoToEdit.getStatus(),
                newContacts.stream().toList(),
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
    public String getSuccessMessage(Todo editedItem) {
        return String.format(MESSAGE_REMOVE_CONTACT_SUCCESS, Messages.format(editedItem));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveContactFromTodoCommand otherCommand)) {
            return false;
        }

        return index.equals(otherCommand.index)
                && contactIndices.equals(otherCommand.contactIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("contactIndicies", contactIndices)
                .toString();
    }
}
