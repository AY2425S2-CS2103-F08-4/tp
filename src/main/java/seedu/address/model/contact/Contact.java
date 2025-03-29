package seedu.address.model.contact;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.Item;

/**
 * Represents a Contact in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Contact implements Item {

    // Identity fields
    private final Id id;
    private final Name name;
    private final Email email;
    private final Course course;
    private final Group group;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Contact(Id id, Name name, Email email, Course course,
                   Group group, Set<Tag> tags) {
        requireAllNonNull(name, email, tags);
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
        this.group = group;
        this.tags.addAll(tags);
    }

    public Id getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public Course getCourse() {
        return course;
    }

    public Group getGroup() {
        return group;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both contacts have the same name.
     * This defines a weaker notion of equality between two contacts.
     */
    public boolean isSameContact(Contact otherContact) {
        if (otherContact == this) {
            return true;
        }

        return otherContact != null
                && otherContact.getId().equals(getId());
    }

    /**
     * Returns true if both contacts have the same identity and data fields.
     * This defines a stronger notion of equality between two contacts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Contact)) {
            return false;
        }

        Contact otherContact = (Contact) other;
        return id.equals(otherContact.id)
                && name.equals(otherContact.name)
                && email.equals(otherContact.email)
                && course.equals(otherContact.course)
                && group.equals(otherContact.group)
                && tags.equals(otherContact.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, name, email, course, group, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("name", name)
                .add("email", email)
                .add("course", course)
                .add("group", group)
                .add("tags", tags)
                .toString();
    }

}
