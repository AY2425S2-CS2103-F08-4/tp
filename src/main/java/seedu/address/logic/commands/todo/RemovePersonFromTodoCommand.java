package seedu.address.logic.commands.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_PERSON_LONG;

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
import seedu.address.model.person.Person;
import seedu.address.model.todo.Todo;

/**
 * Remove some associated persons from a todo.
 */
public class RemovePersonFromTodoCommand extends EditCommand<Todo> {

    public static final String COMMAND_WORD = "unlink";

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Remove the association between a todo and some contacts.\n"
            + "Parameters: INDEX "
            + PREFIX_TODO_LINKED_PERSON_LONG + " [PERSON_INDEX]...\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TODO_LINKED_PERSON_LONG + " 1 3 4";

    public static final String MESSAGE_REMOVE_PERSON_SUCCESS = "Removed persons from todo: %1$s";

    private final List<Index> personIndices;

    /**
     * Creates an RemovePersonFromTodoCommand to remove the persons at the specified {@code
     * personIndices} from the list of persons in the todo at the specified {@code index}.
     */
    public RemovePersonFromTodoCommand(Index index, List<Index> personIndices) {
        super(index, Model::getTodoManagerAndList);
        requireNonNull(personIndices);
        this.personIndices = personIndices;
    }

    @Override
    public Todo createEditedItem(Model model, Todo todoToEdit) throws CommandException {
        for (Index index : personIndices) {
            if (index.getZeroBased() >= todoToEdit.getPersons().size()) {
                System.out.println();
                throw new CommandException(String.format(
                        TodoMessages.MESSAGE_INVALID_LINKED_PERSON_INDEX, index.getOneBased()));
            }
        }

        List<Person> newPersons = new ArrayList<>(todoToEdit.getPersons());
        personIndices.stream()
                .map(Index::getZeroBased)
                .sorted(Comparator.reverseOrder())
                .forEach(index -> newPersons.remove((int) index));

        return new Todo(
                todoToEdit.getName(),
                todoToEdit.getDeadline(),
                todoToEdit.getLocation(),
                todoToEdit.getStatus(),
                newPersons.stream().toList(),
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
        return String.format(MESSAGE_REMOVE_PERSON_SUCCESS, Messages.format(editedItem));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemovePersonFromTodoCommand otherCommand)) {
            return false;
        }

        return index.equals(otherCommand.index)
                && personIndices.equals(otherCommand.personIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("personIndicies", personIndices)
                .toString();
    }
}
