package seedu.address.logic.commands.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_PERSON_LONG;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.TodoMessages;
import seedu.address.logic.abstractcommand.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.ItemManagerWithFilteredList;
import seedu.address.model.person.Person;
import seedu.address.model.todo.Todo;

/**
 * Associate a list of persons to a todo.
 */
public class AddPersonToTodoCommand extends EditCommand<Todo> {

    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Associate a todo with some contacts.\n"
            + "Parameters: INDEX "
            + PREFIX_TODO_LINKED_PERSON_LONG + " [PERSON_INDEX]...\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TODO_LINKED_PERSON_LONG + " 1 3 4";

    public static final String MESSAGE_ADD_PERSON_SUCCESS = "Added persons to todo: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "Person %1$s is already assigned to this todo.";

    private final List<Index> personIndices;
    private final Function<Model, ItemManagerWithFilteredList<Person>>
            personManagerAndListGetter = Model::getPersonManagerAndList;

    /**
     * Creates an AddPersonToTodoCommand to add the persons at the specified {@code personIndices}
     * to the todo at the specified {@code index}.
     */
    public AddPersonToTodoCommand(Index index, List<Index> personIndices) {
        super(index, Model::getTodoManagerAndList);
        requireNonNull(personIndices);
        this.personIndices = personIndices;
    }

    @Override
    public Todo createEditedItem(Model model, Todo itemToEdit) throws CommandException {
        List<Person> filteredPersons = personManagerAndListGetter.apply(model)
                .getFilteredItemsList();

        for (Index index : personIndices) {
            if (index.getZeroBased() >= filteredPersons.size()) {
                throw new CommandException(String.format(
                        TodoMessages.MESSAGE_INVALID_LINKED_PERSON_INDEX, index.getOneBased()));
            }
        }

        List<Person> addedPersons = personIndices.stream()
                .map(index -> filteredPersons.get(index.getZeroBased())).toList();

        for (Person person : addedPersons) {
            if (itemToEdit.getPersons().contains(person)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_PERSON,
                        Messages.getSimplifiedFormat(person)));
            }
        }

        List<Person> combinedPersons =
                Stream.concat(itemToEdit.getPersons().stream(), addedPersons.stream()).toList();

        return new Todo(
                itemToEdit.getName(),
                itemToEdit.getDeadline(),
                itemToEdit.getLocation(),
                itemToEdit.getStatus(),
                combinedPersons,
                itemToEdit.getTags()
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
        return String.format(MESSAGE_ADD_PERSON_SUCCESS, Messages.format(editedItem));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPersonToTodoCommand otherCommand)) {
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
