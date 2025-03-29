package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PERSON_COMMAND_WORD;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_COURSE_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_EMAIL_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_GROUP_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_ID_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_NAME_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_PHONE_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_TAG_LONG;

import java.util.Set;

import seedu.address.logic.commands.person.AddPersonCommand;
import seedu.address.logic.commands.person.EditPersonCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return PERSON_COMMAND_WORD + " " + AddPersonCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_PERSON_ID_LONG).append(person.getId().fullId).append(" ")
                .append(PREFIX_PERSON_NAME_LONG).append(person.getName().fullName).append(" ")
                .append(PREFIX_PERSON_PHONE_LONG).append(person.getPhone().value).append(" ")
                .append(PREFIX_PERSON_EMAIL_LONG).append(person.getEmail().value).append(" ")
                .append(PREFIX_PERSON_COURSE_LONG).append(person.getCourse().fullModule).append(" ")
                .append(PREFIX_PERSON_GROUP_LONG).append(person.getGroup().fullGroup).append(" ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_PERSON_TAG_LONG).append(s.tagName).append(" ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getId().ifPresent(id -> sb.append(PREFIX_PERSON_ID_LONG).append(id.fullId).append(" "));
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_PERSON_NAME_LONG).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PERSON_PHONE_LONG).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_PERSON_EMAIL_LONG).append(email.value).append(" "));
        descriptor.getCourse().ifPresent(course -> sb.append(PREFIX_PERSON_COURSE_LONG).append(course.fullModule)
            .append(" "));
        descriptor.getGroup()
            .ifPresent(group -> sb.append(PREFIX_PERSON_GROUP_LONG).append(group.fullGroup).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_PERSON_TAG_LONG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_PERSON_TAG_LONG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
