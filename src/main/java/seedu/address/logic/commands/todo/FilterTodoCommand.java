package seedu.address.logic.commands.todo;

import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_CONTACT_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_STATUS_LONG;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.TodoMessages;
import seedu.address.logic.abstractcommand.FilterCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.predicate.TodoContactPredicate;
import seedu.address.model.todo.predicate.TodoDeadlinePredicate;
import seedu.address.model.todo.predicate.TodoLocationPredicate;
import seedu.address.model.todo.predicate.TodoNamePredicate;
import seedu.address.model.todo.predicate.TodoStatusPredicate;

/**
 * Filters and lists all events based on specified criteria.
 * Filter criteria are formed with columns, operators, and values.
 */
public class FilterTodoCommand extends FilterCommand<Todo> {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Filters todos based on specified criteria.\n"
            + "Parameters: COL/ [<OP>:] <VALUE(s)> [...]\n"

            + "- COL/ : Column to filter on (" + PREFIX_TODO_NAME_LONG + ", " + PREFIX_TODO_DEADLINE_LONG
            + ", " + PREFIX_TODO_LOCATION_LONG + ", " + PREFIX_TODO_STATUS_LONG + ", "
            + PREFIX_TODO_LINKED_CONTACT_LONG + ")\n"

            + "- <OP>: : Operator (and, or, nand, nor) to apply to the column criterion. Defaults"
            + " to 'and' if not specified. Cannot be applied to status criterion.\n"

            + "- <VALUE(s)>: One or more values to filter by.\n"
            + "    + For name and location, use keywords separated by whitespaces.\n"
            + "    + For deadline and location, use closed intervals separated by whitespaces. "
            + "Each interval is in the format \"(<INTERVAL_START>/<INTERVAL_END>)\". The "
            + "interval start and end can be in the format YY-MM-DD HH:MM, where HH is in 24-hour "
            + "format, or can be - to specify no lower bound or upper bound. At least one of the "
            + "two bounds must be specified.\n"
            + "    + For status, use \"true\" for done and \"false\" for not done.\n"
            + "    + For linked contacts, use the indices in the currently displayed contact list.\n"

            + "Examples:\n"

            + "1. " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_TODO_NAME_LONG + " or: "
            + "Grading REPORT\n"
            + "   Find todos whose name contains at least one of the keywords \"grading\" or "
            + "\"report.\"\n"

            + "2. " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_TODO_NAME_LONG
            + "CS1010S GrAdIng " + PREFIX_TODO_DEADLINE_LONG + "or: (25-03-13 23:59/25-03-20 23:59) "
            + "(25-03-27 23:59/-)\n"
            + "   Find todos whose name contains both the keywords \"CS1010S\" and "
            + "\"grading\" and whose deadline is between 25-03-13 23:59 and 25-03-20 23:59 "
            + "inclusive or not before 25-03-27 23:59.\n"

            + "3. " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_TODO_LOCATION_LONG
            + "nand: NUS Home " + PREFIX_TODO_STATUS_LONG + "false\n"
            + "   Find todos whose location does not contain the keywords \"NUS\" or "
            + "\"home\" and which are not done yet.\n";

    private final TodoPredicate todoPredicate;

    /**
     * Creates a {@code FilterTodoCommand} to filter items that match the given predicates.
     */
    public FilterTodoCommand(TodoPredicate todoPredicate) {
        super(Model::getTodoManagerAndList);
        this.todoPredicate = todoPredicate;
    }


    @Override
    public Predicate<Todo> createPredicate(Model model) throws CommandException {
        return todoPredicate.getPredicate(model);
    }

    @Override
    public String getResultOverviewMessage(int numberOfResults) {
        return String.format(Messages.MESSAGE_SEARCH_OVERVIEW, numberOfResults);
    }

    /**
     * Stores the details to filter the todo with.
     */
    public static class TodoPredicate {
        private TodoNamePredicate namePredicate;
        private TodoDeadlinePredicate deadlinePredicate;
        private TodoLocationPredicate locationPredicate;
        private TodoStatusPredicate statusPredicate;
        private Operator contactOperator;
        private List<Index> contactIndices;

        public TodoPredicate() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public TodoPredicate(TodoPredicate toCopy) {
            setNamePredicate(toCopy.namePredicate);
            setDeadlinePredicate(toCopy.deadlinePredicate);
            setLocationPredicate(toCopy.locationPredicate);
            setStatusPredicate(toCopy.statusPredicate);
            setContactOperator(toCopy.contactOperator);
            setContactIndices(toCopy.contactIndices);
        }

        /**
         * Returns true if at least one field is non-null.
         */
        public boolean isAnyFieldNonNull() {
            return CollectionUtil.isAnyNonNull(namePredicate, deadlinePredicate,
                    locationPredicate, statusPredicate, contactIndices);
        }

        public void setNamePredicate(TodoNamePredicate namePredicate) {
            this.namePredicate = namePredicate;
        }

        public Optional<TodoNamePredicate> getNamePredicate() {
            return Optional.ofNullable(namePredicate);
        }

        public void setDeadlinePredicate(TodoDeadlinePredicate deadlinePredicate) {
            this.deadlinePredicate = deadlinePredicate;
        }

        public Optional<TodoDeadlinePredicate> getDeadlinePredicate() {
            return Optional.ofNullable(deadlinePredicate);
        }

        public void setLocationPredicate(TodoLocationPredicate locationPredicate) {
            this.locationPredicate = locationPredicate;
        }

        public Optional<TodoLocationPredicate> getLocationPredicate() {
            return Optional.ofNullable(locationPredicate);
        }

        public void setStatusPredicate(TodoStatusPredicate statusPredicate) {
            this.statusPredicate = statusPredicate;
        }

        public Optional<TodoStatusPredicate> getStatusPredicate() {
            return Optional.ofNullable(statusPredicate);
        }

        public Optional<Operator> getContactOperator() {
            return Optional.ofNullable(contactOperator);
        }

        public void setContactOperator(Operator contactOperator) {
            this.contactOperator = contactOperator;
        }

        public Optional<List<Index>> getContactIndices() {
            return (contactIndices != null) ? Optional.of(List.copyOf(contactIndices))
                    : Optional.empty();
        }

        public void setContactIndices(List<Index> contactIndices) {
            this.contactIndices = (contactIndices != null) ? List.copyOf(contactIndices) : null;
        }

        public Predicate<Todo> getPredicate(Model model) throws CommandException {
            Predicate<Todo> contactPredicate;
            if (contactIndices != null) {
                List<Contact> filteredContacts = model.getContactManagerAndList()
                        .getFilteredItemsList();

                for (Index index : contactIndices) {
                    if (index.getZeroBased() >= filteredContacts.size()) {
                        throw new CommandException(String.format(
                                TodoMessages.MESSAGE_INVALID_LINKED_CONTACT_INDEX, index.getOneBased()));
                    }
                }

                List<Contact> contacts = contactIndices.stream()
                        .map(index -> filteredContacts.get(index.getZeroBased())).toList();

                contactPredicate =
                        todo -> new TodoContactPredicate(contactOperator, contacts).test(todo);
            } else {
                contactPredicate = unused -> true;
            }

            return todo -> (namePredicate == null || namePredicate.test(todo))
                    && (deadlinePredicate == null || deadlinePredicate.test(todo))
                    && (locationPredicate == null || locationPredicate.test(todo))
                    && (statusPredicate == null || statusPredicate.test(todo))
                    && (contactPredicate.test(todo));
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("namePredicate", namePredicate)
                    .add("deadlinePredicate", deadlinePredicate)
                    .add("locationPredicate", locationPredicate)
                    .add("statusPredicate", statusPredicate)
                    .add("contactOperator", contactOperator)
                    .add("contactIndices", contactIndices)
                    .toString();
        }

    }
}
