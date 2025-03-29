package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_PERSON_LONG;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EventMessages;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.item.ItemManagerWithFilteredList;
import seedu.address.model.person.Person;

/**
 * Associates a list of people to an event.
 */
public class AddPersonToEventCommand extends EditCommand<Event> {
    public static final String COMMAND_WORD = "link";
    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Associate an event with some contacts.\n"
            + "Parameters: INDEX "
            + PREFIX_EVENT_LINKED_PERSON_LONG + " [PERSON_INDEX]...\n"
            + "Example: " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_LINKED_PERSON_LONG + " 1 3 4";
    public static final String MESSAGE_ADD_PERSON_SUCCESS = "Added persons to event: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "Person %1$s is already assigned to this event.";

    private final List<Index> personIndices;
    private final Function<Model, ItemManagerWithFilteredList<Person>> personManagerAndListGetter =
            Model::getPersonManagerAndList;

    /**
     * Creates an AddPersonToEventCommand to add the persons at the specified {@code personIndices}
     * to the event at the specified {@code index}.
     */
    public AddPersonToEventCommand(Index index, List<Index> personIndices) {
        super(index, Model::getEventManagerAndList);
        requireNonNull(personIndices);
        this.personIndices = personIndices;
    }

    @Override
    public Event createEditedItem(Model model, Event itemToEdit) throws CommandException {
        List<Person> filteredPersons = personManagerAndListGetter.apply(model).getFilteredItemsList();
        for (Index index : this.personIndices) {
            if (index.getZeroBased() >= filteredPersons.size()) {
                throw new CommandException(String.format(EventMessages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        index.getOneBased()));
            }
        }
        // Getting people from the model to check for duplicates
        List<Person> addedPersons = personIndices.stream()
                .map(index -> filteredPersons.get(index.getZeroBased()))
                .toList();
        for (Person person : addedPersons) {
            if (itemToEdit.getPersons().contains(person)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_PERSON,
                        Messages.getSimplifiedFormat(person)));
            }
        }
        List<Person> combinedPersonList = Stream
                .concat(itemToEdit.getPersons().stream(), addedPersons.stream())
                .toList();
        // To add a mapping of newly added persons to a new position in markedList
        List<Boolean> combinedMarkedList = Stream
                .concat(itemToEdit.getMarkedList().stream(), addedPersons.stream().map(x -> false))
                .toList();
        return new Event(
                itemToEdit.getName(),
                itemToEdit.getStartTime(),
                itemToEdit.getEndTime(),
                itemToEdit.getLocation(),
                combinedPersonList,
                combinedMarkedList,
                itemToEdit.getTags()
        );
    }

    @Override
    public String getInvalidIndexMessage() {
        return EventMessages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
    }

    @Override
    public String getDuplicateMessage() {
        return EventMessages.MESSAGE_DUPLICATE_EVENT;
    }

    @Override
    public String getSuccessMessage(Event editedItem) {
        return String.format(MESSAGE_ADD_PERSON_SUCCESS, Messages.format(editedItem));
    }
}
