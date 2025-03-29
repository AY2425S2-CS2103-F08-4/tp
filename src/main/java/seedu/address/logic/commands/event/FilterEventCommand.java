package seedu.address.logic.commands.event;

import static seedu.address.logic.EventMessages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_PERSON_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_START_LONG;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.FilterCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.predicate.EventEndTimePredicate;
import seedu.address.model.event.predicate.EventLocationPredicate;
import seedu.address.model.event.predicate.EventNamePredicate;
import seedu.address.model.event.predicate.EventPersonPredicate;
import seedu.address.model.event.predicate.EventStartTimePredicate;
import seedu.address.model.person.Person;

/**
 * Filters and lists all events based on specified criteria.
 * Filter criteria are formed with columns, operators, and values.
 */
public class FilterEventCommand extends FilterCommand<Event> {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Filters events based on specified criteria.\n"
            + "Parameters: COL/ [<OP>:] <VALUE(s)> [...]\n"

            + "- COL/ : Column to filter on (" + PREFIX_EVENT_NAME_LONG + ", " + PREFIX_EVENT_START_LONG
            + ", " + PREFIX_EVENT_END_LONG + ", " + PREFIX_EVENT_LOCATION_LONG + ", "
            + PREFIX_EVENT_LINKED_PERSON_LONG + ")\n"

            + "- <OP>: : Operator (and, or, nand, nor) to apply to the column criterion. Defaults"
            + " to 'and' if not specified.\n"

            + "- <VALUE(s)>: One or more values to filter by.\n"
            + "    + For name and location, use keywords separated by whitespaces.\n"
            + "    + For start and end time, use closed intervals separated by whitespaces. "
            + "Each interval is in the format \"(<INTERVAL_START>/<INTERVAL_END>)\". The "
            + "interval start and end can be in the format YY-MM-DD HH:MM, where HH is in 24-hour "
            + "format, or can be - to specify no lower bound or upper bound. At least one of the "
            + "two bounds must be specified.\n"
            + "    + For linked persons, use the indices in the currently displayed person list.\n"

            + "Examples:\n"

            + "1. " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_EVENT_NAME_LONG + " or: "
            + "Exam PRESENTATION\n"
            + "   Find todos whose name contains at least one of the keywords \"exam\" or "
            + "\"presentation.\"\n"

            + "2. " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_EVENT_NAME_LONG
            + "CS1010S eXAM " + PREFIX_EVENT_START_LONG + "or: (25-03-13 23:59/25-03-20 23:59) "
            + "(25-03-27 23:59/-)\n"
            + "   Find todos whose name contains both the keywords \"CS1010S\" and "
            + "\"exam\" and whose start time is between 25-03-13 23:59 and 25-03-20 23:59 "
            + "inclusive or not before 25-03-27 23:59.\n"

            + "3. " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_EVENT_LOCATION_LONG
            + "nand: NUS Home " + PREFIX_EVENT_LINKED_PERSON_LONG + "1 2 3\n"
            + "   Find todos whose location does not contain the keywords \"NUS\" or "
            + "\"home\" and which are is linked to the people currently at index 1, 2 and 3.\n";

    private final EventPredicate eventPredicate;

    /**
     * Creates a {@code FilterEventCommand} to filter items that match the given predicates.
     */
    public FilterEventCommand(EventPredicate eventPredicate) {
        super(Model::getEventManagerAndList);
        this.eventPredicate = eventPredicate;
    }


    @Override
    public Predicate<Event> createPredicate(Model model) throws CommandException {
        return eventPredicate.getPredicate(model);
    }

    @Override
    public String getResultOverviewMessage(int numberOfResults) {
        return String.format(Messages.MESSAGE_SEARCH_OVERVIEW, numberOfResults);
    }

    /**
     * Stores the details to filter the event with.
     */
    public static class EventPredicate {
        private EventNamePredicate namePredicate;
        private EventStartTimePredicate startTimePredicate;
        private EventEndTimePredicate endTimePredicate;
        private EventLocationPredicate locationPredicate;
        private Operator personOperator;
        private List<Index> personIndices;

        public EventPredicate() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EventPredicate(seedu.address.logic.commands.event.FilterEventCommand.EventPredicate toCopy) {
            setNamePredicate(toCopy.namePredicate);
            setStartTimePredicate(toCopy.startTimePredicate);
            setEndTimePredicate(toCopy.endTimePredicate);
            setLocationPredicate(toCopy.locationPredicate);
            setPersonOperator(toCopy.personOperator);
            setPersonIndices(toCopy.personIndices);
        }

        /**
         * Returns true if at least one field is non-null.
         */
        public boolean isAnyFieldNonNull() {
            return CollectionUtil.isAnyNonNull(namePredicate, startTimePredicate, endTimePredicate,
                    locationPredicate, personIndices);
        }

        public void setNamePredicate(EventNamePredicate namePredicate) {
            this.namePredicate = namePredicate;
        }

        public Optional<EventNamePredicate> getNamePredicate() {
            return Optional.ofNullable(namePredicate);
        }

        public void setStartTimePredicate(EventStartTimePredicate startTimePredicate) {
            this.startTimePredicate = startTimePredicate;
        }

        public Optional<EventStartTimePredicate> getStartTimePredicate() {
            return Optional.ofNullable(startTimePredicate);
        }

        public void setLocationPredicate(EventLocationPredicate locationPredicate) {
            this.locationPredicate = locationPredicate;
        }

        public void setEndTimePredicate(EventEndTimePredicate endTimePredicate) {
            this.endTimePredicate = endTimePredicate;
        }

        public Optional<EventEndTimePredicate> getEndTimePredicate() {
            return Optional.ofNullable(endTimePredicate);
        }

        public Optional<EventLocationPredicate> getLocationPredicate() {
            return Optional.ofNullable(locationPredicate);
        }

        public Optional<Operator> getPersonOperator() {
            return Optional.ofNullable(personOperator);
        }

        public void setPersonOperator(Operator personOperator) {
            this.personOperator = personOperator;
        }

        public Optional<List<Index>> getPersonIndices() {
            return (personIndices != null) ? Optional.of(List.copyOf(personIndices))
                    : Optional.empty();
        }

        public void setPersonIndices(List<Index> personIndices) {
            this.personIndices = (personIndices != null) ? List.copyOf(personIndices) : null;
        }

        public Predicate<Event> getPredicate(Model model) throws CommandException {
            Predicate<Event> personPredicate;
            if (personIndices != null) {
                List<Person> filteredPersons = model.getPersonManagerAndList()
                        .getFilteredItemsList();

                for (Index index : personIndices) {
                    if (index.getZeroBased() >= filteredPersons.size()) {
                        throw new CommandException(String.format(
                                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, index.getOneBased()));
                    }
                }

                List<Person> persons = personIndices.stream()
                        .map(index -> filteredPersons.get(index.getZeroBased())).toList();

                personPredicate =
                        event -> new EventPersonPredicate(personOperator, persons).test(event);
            } else {
                personPredicate = unused -> true;
            }

            return event -> (namePredicate == null || namePredicate.test(event))
                    && (startTimePredicate == null || startTimePredicate.test(event))
                    && (endTimePredicate == null || endTimePredicate.test(event))
                    && (locationPredicate == null || locationPredicate.test(event))
                    && (personPredicate.test(event));
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("namePredicate", namePredicate)
                    .add("startTimePredicate", startTimePredicate)
                    .add("endTimePredicate", endTimePredicate)
                    .add("locationPredicate", locationPredicate)
                    .add("personOperator", personOperator)
                    .add("personIndices", personIndices)
                    .toString();
        }

    }
}
