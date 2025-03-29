package seedu.address.model.todo;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Tag;
import seedu.address.model.item.Item;

/**
 * Represents a Todo.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Todo implements Item {

    // Identity fields
    private final TodoName name;

    // Data fields
    private final TodoDeadline deadline;
    private final TodoLocation location;
    private final TodoStatus status;
    private final List<Contact> contacts;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Todo(TodoName name, TodoDeadline deadline, TodoLocation location, TodoStatus status,
                List<Contact> contacts, Set<Tag> tags) {
        requireAllNonNull(name, deadline, status, location);
        this.name = name;
        this.deadline = deadline;
        this.location = location;
        this.status = status;
        this.contacts = contacts.stream().toList();
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     */
    public Todo(TodoName name, TodoDeadline deadline, TodoLocation location) {
        requireAllNonNull(name, deadline, location);
        this.name = name;
        this.deadline = deadline;
        this.location = location;
        this.status = new TodoStatus(false);
        this.contacts = List.of();
    }

    public TodoName getName() {
        return name;
    }

    public TodoDeadline getDeadline() {
        return deadline;
    }

    public TodoLocation getLocation() {
        return location;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }


    /**
     * Returns true if both todos have the same identity and data fields.
     * This defines a stronger notion of equality between two todos.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Todo otherTodo)) {
            return false;
        }

        return name.equals(otherTodo.name)
                && deadline.equals(otherTodo.deadline)
                && location.equals(otherTodo.location)
                && status.equals(otherTodo.status)
                && contacts.equals(otherTodo.contacts)
                && tags.equals(otherTodo.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, deadline, location, status, contacts);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("deadline", deadline)
                .add("location", location)
                .add("status", status)
                .add("contacts", contacts)
                .add("tags", tags)
                .toString();
    }
}
