package seedu.address.model.event;

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
 * Represents an Event.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event implements Item {
    private final EventName name;
    private final EventDateTime startTime;
    private final EventDateTime endTime;
    private final EventLocation location;
    private final List<Contact> contacts;
    private final List<Boolean> markedList;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null, field values are validated, immutable.
     */
    public Event(EventName name, EventDateTime startTime, EventDateTime endTime,
                 EventLocation location, List<Contact> contacts, List<Boolean> markedList,
                 Set<Tag> tags) {
        requireAllNonNull(name, startTime, endTime, location, contacts, tags);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.contacts = contacts;
        this.markedList = markedList;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null, field values are validated, immutable.
     */
    public Event(EventName name, EventDateTime startTime,
            EventDateTime endTime, EventLocation location) {
        requireAllNonNull(name, startTime, endTime, location);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.contacts = List.of();
        this.markedList = List.of();
    }

    public EventName getName() {
        return this.name;
    }

    public EventDateTime getStartTime() {
        return this.startTime;
    }

    public EventDateTime getEndTime() {
        return this.endTime;
    }

    public EventLocation getLocation() {
        return this.location;
    }

    public List<Contact> getContacts() {
        return this.contacts;
    }

    public List<Boolean> getMarkedList() {
        return this.markedList;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("start_time", startTime)
                .add("end_time", endTime)
                .add("location", location)
                .add("contacts", contacts)
                .add("marked_contacts", markedList)
                .add("tags", tags)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Event otherEvent)) {
            return false;
        }
        return this.name.equals(otherEvent.name)
                && this.startTime.equals(otherEvent.startTime)
                && this.endTime.equals(otherEvent.endTime)
                && this.location.equals(otherEvent.location)
                && this.contacts.equals(otherEvent.contacts)
                && this.markedList.equals(otherEvent.markedList)
                && this.tags.equals(otherEvent.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startTime, endTime, location, contacts, markedList, tags);
    }
}
