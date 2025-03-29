package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_CONTACT_LONG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EventMessages;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds the log of given contacts via index.
 */
public class AddContactToLogEventCommand extends EditCommand<Event> {
    public static final String COMMAND_WORD = "log";

    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Logs some contacts with an event.\n"
            + "Parameters: INDEX "
            + PREFIX_EVENT_LINKED_CONTACT_LONG + " [CONTACT_INDEX]...\n"
            + "Example: " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_LINKED_CONTACT_LONG + " 1 2 3";
    public static final String MESSAGE_ADD_LOG_SUCCESS = "Added attendance from contacts to event: %1$s";
    public static final String MESSAGE_CONTACT_ALREADY_LOGGED =
            "The contact at the following index(es) are already logged: %s";

    private final List<Index> contactIndices;

    /**
     * Creates a RemoveContactFromEventCommand to remove the contacts at the specific {@code
     * contactIndices}.
     */
    public AddContactToLogEventCommand(Index index, List<Index> contactIndices) {
        super(index, Model::getEventManagerAndList);
        requireNonNull(contactIndices);
        this.contactIndices = contactIndices;
    }

    @Override
    public Event createEditedItem(Model model, Event eventToEdit) throws CommandException {
        for (Index index : contactIndices) {
            if (index.getZeroBased() >= eventToEdit.getContacts().size()) {
                throw new CommandException(String.format(EventMessages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX,
                        index.getOneBased()));
            }
        }
        List<Boolean> newMarkList = new ArrayList<>(eventToEdit.getMarkedList());
        List<Index> checkContactMarked = contactIndices.stream()
                .filter(x -> newMarkList.get(x.getZeroBased()))
                .toList();
        if (!checkContactMarked.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_CONTACT_ALREADY_LOGGED,
                    checkContactMarked.stream()
                            .map(x -> String.valueOf(x.getOneBased()))
                            .collect(Collectors.joining(", ")))
            );
        }
        contactIndices.stream()
                .map(Index::getZeroBased)
                .sorted(Comparator.reverseOrder())
                .forEach(index -> newMarkList.set(index, true));
        return new Event(
                eventToEdit.getName(),
                eventToEdit.getStartTime(),
                eventToEdit.getEndTime(),
                eventToEdit.getLocation(),
                eventToEdit.getContacts(),
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
        return String.format(MESSAGE_ADD_LOG_SUCCESS, Messages.format(editedItem));
    }
}
