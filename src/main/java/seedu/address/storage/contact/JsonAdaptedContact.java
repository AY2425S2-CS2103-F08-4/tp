package seedu.address.storage.contact;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Course;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Group;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Tag;

/**
 * Jackson-friendly version of {@link Contact}.
 */
public class JsonAdaptedContact {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Contact's %s field is missing!";

    private final String id;
    private final String name;
    private final String email;
    private final String module;
    private final String group;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedContact} with the given contact details.
     */
    @JsonCreator
    public JsonAdaptedContact(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("email") String email, @JsonProperty("course") String module,
        @JsonProperty("group") String group,
        @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.module = module;
        this.group = group;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Contact} into this class for Jackson use.
     */
    public JsonAdaptedContact(Contact source) {
        id = source.getId().fullId;
        name = source.getName().fullName;
        email = source.getEmail().value;
        module = source.getCourse().fullModule;
        group = source.getGroup().fullGroup;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted contact object into the model's {@code Contact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact.
     */
    public Contact toModelType() throws IllegalValueException {
        final List<Tag> contactTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            contactTags.add(tag.toModelType());
        }

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Id.class.getSimpleName()));
        }
        final Id modelId = new Id(id);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (module == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Course.class.getSimpleName()));
        }
        if (!Course.isValidModule(module)) {
            throw new IllegalValueException(Course.MESSAGE_CONSTRAINTS);
        }
        final Course modelCourse = new Course(module);

        if (group == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Group.class.getSimpleName()));
        }
        if (!Group.isValidGroup(group)) {
            throw new IllegalValueException(Group.MESSAGE_CONSTRAINTS);
        }
        final Group modelGroup = new Group(group);

        final Set<Tag> modelTags = new HashSet<>(contactTags);
        return new Contact(modelId, modelName, modelEmail, modelCourse, modelGroup,
            modelTags);
    }

}
