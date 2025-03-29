package seedu.address.model.event.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;

/**
 * Tests if an {@code Event}'s linked contacts contains the specified contacts based on the provided
 * {@code Operator}.
 */
public class EventContactPredicate implements Predicate<Event> {
    private final Operator operator;
    private final List<Contact> contacts;

    /**
     * Constructs an {@code EventContactPredicate} with the given operator and list of contacts.
     *
     * @param operator The operator to apply (e.g., AND, OR) to the contact matching logic.
     * @param contacts  The list of contacts to search for in the event's linked contacts.
     */
    public EventContactPredicate(Operator operator, List<Contact> contacts) {
        this.operator = operator;
        this.contacts = contacts;
    }

    @Override
    public boolean test(Event event) {
        return operator.apply(contacts.stream(), contact -> event.getContacts().contains(contact));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventContactPredicate otherPredicate)) {
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
