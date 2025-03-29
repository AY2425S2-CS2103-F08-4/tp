package seedu.address.model.todo.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contact.Contact;
import seedu.address.model.todo.Todo;

/**
 * Tests if a {@code Todo}'s linked contacts contains the specified contacts based on the provided
 * {@code Operator}.
 */
public class TodoContactPredicate implements Predicate<Todo> {
    private final Operator operator;
    private final List<Contact> contacts;

    /**
     * Constructs a {@code TodoNamePredicate} with the given operator and list of contacts.
     *
     * @param operator The operator to apply (e.g., AND, OR) to the contacts matching logic.
     * @param contacts  The list of contacts to search for in the todo's linked contacts.
     */
    public TodoContactPredicate(Operator operator, List<Contact> contacts) {
        this.operator = operator;
        this.contacts = contacts;
    }

    @Override
    public boolean test(Todo todo) {
        return operator.apply(contacts.stream(), contact -> todo.getContacts().contains(contact));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TodoContactPredicate otherPredicate)) {
            return false;
        }

        return operator.equals(otherPredicate.operator)
                && contacts.equals(otherPredicate.contacts);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("operator", operator)
                .add("contacts", contacts).toString();
    }
}
