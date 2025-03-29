package seedu.address.logic.commands.update;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_COURSE_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Course;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Group;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Tag;

/**
 * Edits the details of an existing contact in the address book.
 */
public class EditContactCommand extends EditCommand<Contact> {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD + ": Edits the details of the "
        + "contact identified by the index number used in the displayed contact list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_CONTACT_ID_LONG + " ID] "
        + "[" + PREFIX_CONTACT_NAME_LONG + " NAME] "
        + "[" + PREFIX_CONTACT_EMAIL_LONG + " EMAIL] "
        + "[" + PREFIX_CONTACT_COURSE_LONG + " COURSE] "
        + "[" + PREFIX_CONTACT_GROUP_LONG + " GROUP] "
        + "[" + PREFIX_CONTACT_TAG_LONG + " TAG]...\n"
        + "Example: " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
        + PREFIX_CONTACT_EMAIL_LONG + " johndoe@example.com";

    public static final String MESSAGE_EDIT_CONTACT_SUCCESS = "Edited Contact: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CONTACT =
        "This contact already exists in the address book.";

    private final EditContactDescriptor editContactDescriptor;

    /**
     * @param index                of the contact in the filtered contact list to edit
     * @param editContactDescriptor details to edit the contact with
     */
    public EditContactCommand(Index index, EditContactDescriptor editContactDescriptor) {
        super(index, Model::getContactManagerAndList);
        requireNonNull(editContactDescriptor);
        this.editContactDescriptor = new EditContactDescriptor(editContactDescriptor);
    }

    /**
     * Creates and returns a {@code Contact} with the details of {@code contactToEdit} edited with
     * {@code editContactDescriptor}.
     */
    public Contact createEditedItem(Model model, Contact contactToEdit) {
        assert contactToEdit != null;

        Id updatedId = editContactDescriptor.getId().orElse(contactToEdit.getId());
        Name updatedName = editContactDescriptor.getName().orElse(contactToEdit.getName());
        Email updatedEmail = editContactDescriptor.getEmail().orElse(contactToEdit.getEmail());
        Course updatedCourse = editContactDescriptor.getCourse().orElse(contactToEdit.getCourse());
        Group updatedGroup = editContactDescriptor.getGroup().orElse(contactToEdit.getGroup());
        Set<Tag> updatedTags = editContactDescriptor.getTags().orElse(contactToEdit.getTags());

        return new Contact(updatedId, updatedName, updatedEmail, updatedCourse,
            updatedGroup, updatedTags);
    }

    @Override
    public String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX;
    }

    @Override
    public String getDuplicateMessage() {
        return MESSAGE_DUPLICATE_CONTACT;
    }

    @Override
    public String getSuccessMessage(Contact editedItem) {
        return String.format(MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedItem));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditContactCommand otherEditCommand)) {
            return false;
        }

        return index.equals(otherEditCommand.index)
            && editContactDescriptor.equals(otherEditCommand.editContactDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", index)
            .add("editContactDescriptor", editContactDescriptor)
            .toString();
    }

    /**
     * Stores the details to edit the contact with. Each non-empty field value will replace the
     * corresponding field value of the contact.
     */
    public static class EditContactDescriptor {
        private Id id;
        private Name name;
        private Email email;
        private Course course;
        private Group group;
        private Set<Tag> tags;

        public EditContactDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditContactDescriptor(EditContactDescriptor toCopy) {
            setId(toCopy.id);
            setName(toCopy.name);
            setEmail(toCopy.email);
            setTags(toCopy.tags);
            setCourse(toCopy.course);
            setGroup(toCopy.group);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(id, name, email, tags, course, group);
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
            if (!(other instanceof EditContactDescriptor otherEditContactDescriptor)) {
                return false;
            }

            return Objects.equals(id, otherEditContactDescriptor.id)
                && Objects.equals(name, otherEditContactDescriptor.name)
                && Objects.equals(email, otherEditContactDescriptor.email)
                && Objects.equals(course, otherEditContactDescriptor.course)
                && Objects.equals(group, otherEditContactDescriptor.group)
                && Objects.equals(tags, otherEditContactDescriptor.tags);
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

}
