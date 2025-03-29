package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_PERSON_LONG;

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
 * Removes the log of the given contacts via index.
 */
public class RemovePersonFromLogEventCommand extends EditCommand<Event> {
    public static final String COMMAND_WORD = "unlog";

    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Remove the logs for some contacts with an event.\n"
            + "Parameter: INDEX "
            + PREFIX_EVENT_LINKED_PERSON_LONG + " [PERSON_INDEX] ...\n"
            + "Example: " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_LINKED_PERSON_LONG + " 1 2 3";
    public static final String MESSAGE_REMOVE_LOG_PERSON_SUCCESS = "Removed attendance from person: %1$s";
    public static final String MESSAGE_PERSON_ALREADY_UNLOGGED =
            "The person at the following index(es) are already unlogged: %s";

    private final List<Index> personIndices;

    /**
     * Creates a RemovePersonFromLogEventCommand to remove the log of the
     * persons at the specified {@code personIndices}
     */
    public RemovePersonFromLogEventCommand(Index index, List<Index> personIndices) {
        super(index, Model::getEventManagerAndList);
        requireNonNull(personIndices);
        this.personIndices = personIndices;
    }

    @Override
    public Event createEditedItem(Model model, Event eventToEdit) throws CommandException {
        for (Index index : personIndices) {
            if (index.getZeroBased() >= eventToEdit.getPersons().size()) {
                throw new CommandException(String.format(EventMessages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        index.getOneBased()));
            }
        }
        List<Boolean> newMarkList = new ArrayList<>(eventToEdit.getMarkedList());
        List<Index> checkPersonMarked = personIndices.stream()
                .filter(x -> !newMarkList.get(x.getZeroBased()))
                .toList();
        if (!checkPersonMarked.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_PERSON_ALREADY_UNLOGGED,
                    checkPersonMarked.stream()
                            .map(x -> String.valueOf(x.getOneBased()))
                            .collect(Collectors.joining(", ")))
            );
        }
        personIndices.stream()
                .map(Index::getZeroBased)
                .sorted(Comparator.reverseOrder())
                .forEach(index -> newMarkList.set(index, false));
        return new Event(
                eventToEdit.getName(),
                eventToEdit.getStartTime(),
                eventToEdit.getEndTime(),
                eventToEdit.getLocation(),
                eventToEdit.getPersons(),
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
        return String.format(MESSAGE_REMOVE_LOG_PERSON_SUCCESS, Messages.format(editedItem));
    }
}
