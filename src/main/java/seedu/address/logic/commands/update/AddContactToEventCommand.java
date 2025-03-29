package seedu.address.logic.commands.update;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_CONTACT_LONG;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EventMessages;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Associates a list of people to an event.
 */
public class AddContactToEventCommand extends EditCommand<Event> {
    public static final String COMMAND_WORD = "link";
    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Associate an event with some contacts.\n"
            + "Parameters: INDEX "
            + PREFIX_EVENT_LINKED_CONTACT_LONG + " [CONTACT_INDEX]...\n"
            + "Example: " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_LINKED_CONTACT_LONG + " 1 3 4";
    public static final String MESSAGE_ADD_CONTACT_SUCCESS = "Added contacts to event: %1$s";
    public static final String MESSAGE_DUPLICATE_CONTACT = "Contact %1$s is already assigned to this event.";

    private final List<Index> contactIndices;
    private final Function<Model, ItemManagerWithFilteredList<Contact>> contactManagerAndListGetter =
            Model::getContactManagerAndList;

    /**
     * Creates an AddContactToEventCommand to add the contacts at the specified {@code contactIndices}
     * to the event at the specified {@code index}.
     */
    public AddContactToEventCommand(Index index, List<Index> contactIndices) {
        super(index, Model::getEventManagerAndList);
        requireNonNull(contactIndices);
        this.contactIndices = contactIndices;
    }

    @Override
    public Event createEditedItem(Model model, Event itemToEdit) throws CommandException {
        List<Contact> filteredContacts = contactManagerAndListGetter.apply(model).getFilteredItemsList();
        for (Index index : this.contactIndices) {
            if (index.getZeroBased() >= filteredContacts.size()) {
                throw new CommandException(String.format(EventMessages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX,
                        index.getOneBased()));
            }
        }
        // Getting people from the model to check for duplicates
        List<Contact> addedContacts = contactIndices.stream()
                .map(index -> filteredContacts.get(index.getZeroBased()))
                .toList();
        for (Contact contact : addedContacts) {
            if (itemToEdit.getContacts().contains(contact)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_CONTACT,
                        Messages.getSimplifiedFormat(contact)));
            }
        }
        List<Contact> combinedContactList = Stream
                .concat(itemToEdit.getContacts().stream(), addedContacts.stream())
                .toList();
        // To add a mapping of newly added contacts to a new position in markedList
        List<Boolean> combinedMarkedList = Stream
                .concat(itemToEdit.getMarkedList().stream(), addedContacts.stream().map(x -> false))
                .toList();
        return new Event(
                itemToEdit.getName(),
                itemToEdit.getStartTime(),
                itemToEdit.getEndTime(),
                itemToEdit.getLocation(),
            combinedContactList,
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
        return String.format(MESSAGE_ADD_CONTACT_SUCCESS, Messages.format(editedItem));
    }
}
