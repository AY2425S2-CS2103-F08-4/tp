package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_CONTACT_LONG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EventMessages;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;

/**
 * Remove some associated people from an event class via index.
 */
public class RemoveContactFromEventCommand extends EditCommand<Event> {
    public static final String COMMAND_WORD = "unlink";

    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Remove the association between a event and some contacts.\n"
            + "Parameters: INDEX "
            + PREFIX_EVENT_LINKED_CONTACT_LONG + " [CONTACT_INDEX]...\n"
            + "Example: " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_LINKED_CONTACT_LONG + " 1 3 4";
    public static final String MESSAGE_REMOVE_CONTACT_SUCCESS = "Removed contacts from event: %1$s";

    private final List<Index> contactIndices;

    /**
     * Creates a RemoveContactFromEventCommand to remove the contacts at the specified {@code
     * contactIndices} from the list of contacts in the event at the specified {@code index}.
     */
    public RemoveContactFromEventCommand(Index index, List<Index> contactIndices) {
        super(index, Model::getEventManagerAndList);
        requireNonNull(contactIndices);
        this.contactIndices = contactIndices;
    }

    @Override
    public Event createEditedItem(Model model, Event eventToEdit) throws CommandException {
        for (Index index : contactIndices) {
            if (index.getZeroBased() >= eventToEdit.getContacts().size()) {
                // System.out.println();
                throw new CommandException(String.format(EventMessages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX,
                        index.getOneBased()));
            }
        }
        List<Contact> newContacts = new ArrayList<>(eventToEdit.getContacts());
        List<Boolean> newMarkList = new ArrayList<>(eventToEdit.getMarkedList());
        contactIndices.stream()
                .map(Index::getZeroBased)
                .sorted(Comparator.reverseOrder())
                .forEach(index -> newContacts.remove((int) index));
        contactIndices.stream()
                .map(Index::getZeroBased)
                .sorted(Comparator.reverseOrder())
                .forEach(index -> newMarkList.remove((int) index));
        return new Event(
            eventToEdit.getName(),
            eventToEdit.getStartTime(),
            eventToEdit.getEndTime(),
            eventToEdit.getLocation(),
            List.copyOf(newContacts),
            List.copyOf(newMarkList),
            eventToEdit.getTags()
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
        return String.format(MESSAGE_REMOVE_CONTACT_SUCCESS, Messages.format(editedItem));
    }
}
