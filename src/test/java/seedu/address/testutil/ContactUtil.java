package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_COURSE_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;

import java.util.Set;

import seedu.address.logic.commands.create.AddContactCommand;
import seedu.address.logic.commands.update.EditContactCommand.EditContactDescriptor;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Tag;

/**
 * A utility class for Contact.
 */
public class ContactUtil {

    /**
     * Returns an add command string for adding the {@code contact}.
     */
    public static String getAddCommand(Contact contact) {
        return CONTACT_COMMAND_WORD + " " + AddContactCommand.COMMAND_WORD + " " + getContactDetails(contact);
    }

    /**
     * Returns the part of command string for the given {@code contact}'s details.
     */
    public static String getContactDetails(Contact contact) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CONTACT_ID_LONG).append(contact.getId().fullId).append(" ")
                .append(PREFIX_CONTACT_NAME_LONG).append(contact.getName().fullName).append(" ")
                .append(PREFIX_CONTACT_EMAIL_LONG).append(contact.getEmail().value).append(" ")
                .append(PREFIX_CONTACT_COURSE_LONG).append(contact.getCourse().fullModule).append(" ")
                .append(PREFIX_CONTACT_GROUP_LONG).append(contact.getGroup().fullGroup).append(" ");
        contact.getTags().stream().forEach(
            s -> sb.append(PREFIX_CONTACT_TAG_LONG).append(s.tagName).append(" ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditContactDescriptor}'s details.
     */
    public static String getEditContactDescriptorDetails(EditContactDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getId().ifPresent(id -> sb.append(PREFIX_CONTACT_ID_LONG).append(id.fullId).append(" "));
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_CONTACT_NAME_LONG).append(name.fullName).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_CONTACT_EMAIL_LONG).append(email.value).append(" "));
        descriptor.getCourse().ifPresent(course -> sb.append(PREFIX_CONTACT_COURSE_LONG).append(course.fullModule)
            .append(" "));
        descriptor.getGroup()
            .ifPresent(group -> sb.append(PREFIX_CONTACT_GROUP_LONG).append(group.fullGroup).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_CONTACT_TAG_LONG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_CONTACT_TAG_LONG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
