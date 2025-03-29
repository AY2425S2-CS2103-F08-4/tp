package seedu.address.storage.todo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Tag;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoDeadline;
import seedu.address.model.todo.TodoLocation;
import seedu.address.model.todo.TodoName;
import seedu.address.model.todo.TodoStatus;
import seedu.address.storage.contact.JsonAdaptedContact;
import seedu.address.storage.contact.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Contact}.
 */
public class JsonAdaptedTodo {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Contact's %s field is missing!";

    private final String name;
    private final String deadline;
    private final String location;
    private final boolean status;
    private final List<JsonAdaptedContact> contactList = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedContact} with the given contact details.
     */
    @JsonCreator
    public JsonAdaptedTodo(
            @JsonProperty("name") String name,
            @JsonProperty("deadline") String deadline,
            @JsonProperty("location") String location,
            @JsonProperty("status") boolean status,
            @JsonProperty("contacts") List<JsonAdaptedContact> contactList,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.deadline = deadline;
        this.location = location;
        this.status = status;
        if (contactList != null) {
            this.contactList.addAll(contactList);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Contact} into this class for Jackson use.
     */
    public JsonAdaptedTodo(Todo source) {
        name = source.getName().toString();
        deadline = source.getDeadline().toString();
        location = source.getLocation().toString();
        status = source.getStatus().isDone();
        contactList.addAll(source.getContacts().stream()
                .map(JsonAdaptedContact::new)
                .collect(Collectors.toList()));
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted contact object into the model's {@code Contact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact.
     */
    public Todo toModelType() throws IllegalValueException {
        final List<Contact> todoContacts = new ArrayList<>();
        for (JsonAdaptedContact contact : contactList) {
            todoContacts.add(contact.toModelType());
        }

        final Set<Tag> modelTags = new HashSet<>();
        for (JsonAdaptedTag tag : tags) {
            modelTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, TodoName.class.getSimpleName()));
        }
        if (!TodoName.isValid(name)) {
            throw new IllegalValueException(TodoName.MESSAGE_CONSTRAINTS);
        }
        final TodoName todoName = new TodoName(name);

        if (deadline == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, TodoDeadline.class.getSimpleName()));
        }
        if (!TodoDeadline.isValid(deadline)) {
            throw new IllegalValueException(TodoDeadline.MESSAGE_CONSTRAINTS);
        }
        final TodoDeadline todoDeadline = new TodoDeadline(deadline);

        if (location == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, TodoLocation.class.getSimpleName()));
        }
        if (!TodoLocation.isValid(location)) {
            throw new IllegalValueException(TodoLocation.MESSAGE_CONSTRAINTS);
        }
        final TodoLocation todoLocation = new TodoLocation(location);

        final TodoStatus todoStatus = new TodoStatus(status);

        return new Todo(todoName, todoDeadline, todoLocation,
                todoStatus, todoContacts, modelTags);
    }

}
