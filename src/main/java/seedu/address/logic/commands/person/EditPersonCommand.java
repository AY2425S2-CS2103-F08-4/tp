package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PERSON_COMMAND_WORD;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_COURSE_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_EMAIL_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_GROUP_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_ID_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_NAME_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_PHONE_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_TAG_LONG;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.EditCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Group;
import seedu.address.model.person.Id;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditPersonCommand extends EditCommand<Person> {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = PERSON_COMMAND_WORD + " " + COMMAND_WORD + ": Edits the details of the "
        + "person identified by the index number used in the displayed person list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_PERSON_ID_LONG + " ID] "
        + "[" + PREFIX_PERSON_NAME_LONG + " NAME] "
        + "[" + PREFIX_PERSON_PHONE_LONG + " PHONE] "
        + "[" + PREFIX_PERSON_EMAIL_LONG + " EMAIL] "
        + "[" + PREFIX_PERSON_COURSE_LONG + " COURSE] "
        + "[" + PREFIX_PERSON_GROUP_LONG + " GROUP] "
        + "[" + PREFIX_PERSON_TAG_LONG + " TAG]...\n"
        + "Example: " + PERSON_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
        + PREFIX_PERSON_PHONE_LONG + " 91234567 "
        + PREFIX_PERSON_EMAIL_LONG + " johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON =
        "This person already exists in the address book.";

    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index                of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditPersonCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        super(index, Model::getPersonManagerAndList);
        requireNonNull(editPersonDescriptor);
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit} edited with
     * {@code editPersonDescriptor}.
     */
    public Person createEditedItem(Model model, Person personToEdit) {
        assert personToEdit != null;

        Id updatedId = editPersonDescriptor.getId().orElse(personToEdit.getId());
        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Course updatedCourse = editPersonDescriptor.getCourse().orElse(personToEdit.getCourse());
        Group updatedGroup = editPersonDescriptor.getGroup().orElse(personToEdit.getGroup());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedId, updatedName, updatedPhone, updatedEmail, updatedCourse,
            updatedGroup, updatedTags);
    }

    @Override
    public String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
    }

    @Override
    public String getDuplicateMessage() {
        return MESSAGE_DUPLICATE_PERSON;
    }

    @Override
    public String getSuccessMessage(Person editedItem) {
        return String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedItem));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPersonCommand otherEditCommand)) {
            return false;
        }

        return index.equals(otherEditCommand.index)
            && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", index)
            .add("editPersonDescriptor", editPersonDescriptor)
            .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Id id;
        private Name name;
        private Phone phone;
        private Email email;
        private Course course;
        private Group group;
        private Set<Tag> tags;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setId(toCopy.id);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setTags(toCopy.tags);
            setCourse(toCopy.course);
            setGroup(toCopy.group);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(id, name, phone, email, tags, course, group);
        }

        public Optional<Id> getId() {
            return Optional.ofNullable(id);
        }

        public void setId(Id id) {
            this.id = id;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Course> getCourse() {
            return Optional.ofNullable(course);
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public Optional<Group> getGroup() {
            return Optional.ofNullable(group);
        }

        public void setGroup(Group group) {
            this.group = group;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException} if
         * modification is attempted. Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags))
                : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}. A defensive copy of {@code tags} is used
         * internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor otherEditPersonDescriptor)) {
                return false;
            }

            return Objects.equals(id, otherEditPersonDescriptor.id)
                && Objects.equals(name, otherEditPersonDescriptor.name)
                && Objects.equals(phone, otherEditPersonDescriptor.phone)
                && Objects.equals(email, otherEditPersonDescriptor.email)
                && Objects.equals(course, otherEditPersonDescriptor.course)
                && Objects.equals(group, otherEditPersonDescriptor.group)
                && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .add("id", id)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("course", course)
                .add("group", group)
                .add("tags", tags)
                .toString();
        }

    }

}
