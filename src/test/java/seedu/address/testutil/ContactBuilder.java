package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Course;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Group;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Contact objects.
 */
public class ContactBuilder {

    public static final String DEFAULT_ID = "A01234567";
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_COURSE = "CS50";
    public static final String DEFAULT_GROUP = "T24";

    private Id id;
    private Name name;
    private Email email;
    private Course course;
    private Group group;
    private Set<Tag> tags;

    /**
     * Creates a {@code ContactBuilder} with the default details.
     */
    public ContactBuilder() {
        id = new Id(DEFAULT_ID);
        name = new Name(DEFAULT_NAME);
        email = new Email(DEFAULT_EMAIL);
        course = new Course(DEFAULT_COURSE);
        group = new Group(DEFAULT_GROUP);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ContactBuilder with the data of {@code contactToCopy}.
     */
    public ContactBuilder(Contact contactToCopy) {
        id = contactToCopy.getId();
        name = contactToCopy.getName();
        email = contactToCopy.getEmail();
        course = contactToCopy.getCourse();
        group = contactToCopy.getGroup();
        tags = new HashSet<>(contactToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Contact} that we are building.
     */
    public ContactBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Contact} that we are building.
     */
    public ContactBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Contact} that we are building.
     */
    public ContactBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Id} of the {@code Contact} that we are building.
     */
    public ContactBuilder withId(String id) {
        this.id = new Id(id);
        return this;
    }

    /**
     * Sets the {@code Course} of the {@code Contact} that we are building.
     */
    public ContactBuilder withCourse(String course) {
        this.course = new Course(course);
        return this;
    }

    /**
     * Sets the {@code Group} of the {@code Contact} that we are building.
     */
    public ContactBuilder withGroup(String group) {
        this.group = new Group(group);
        return this;
    }

    public Contact build() {
        return new Contact(id, name, email, course, group, tags);
    }

}
