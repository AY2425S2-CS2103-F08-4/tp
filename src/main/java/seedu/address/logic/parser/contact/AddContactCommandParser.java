package seedu.address.logic.parser.contact;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_COURSE_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;

import java.util.Set;

import seedu.address.logic.commands.create.AddContactCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Course;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Group;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddContactCommandParser implements Parser<AddContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddContactCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CONTACT_ID_LONG,
            PREFIX_CONTACT_NAME_LONG, PREFIX_CONTACT_EMAIL_LONG, PREFIX_CONTACT_TAG_LONG, PREFIX_CONTACT_COURSE_LONG,
            PREFIX_CONTACT_GROUP_LONG);

        if (!argMultimap.arePrefixesPresent(PREFIX_CONTACT_ID_LONG, PREFIX_CONTACT_NAME_LONG, PREFIX_CONTACT_EMAIL_LONG,
            PREFIX_CONTACT_COURSE_LONG, PREFIX_CONTACT_GROUP_LONG) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CONTACT_ID_LONG, PREFIX_CONTACT_NAME_LONG,
            PREFIX_CONTACT_EMAIL_LONG, PREFIX_CONTACT_COURSE_LONG, PREFIX_CONTACT_GROUP_LONG);
        Id id = ContactParserUtil.parseId(argMultimap.getValue(PREFIX_CONTACT_ID_LONG).get());
        Name name = ContactParserUtil.parseName(argMultimap.getValue(PREFIX_CONTACT_NAME_LONG).get());
        Email email = ContactParserUtil.parseEmail(argMultimap.getValue(PREFIX_CONTACT_EMAIL_LONG).get());
        Course course = ContactParserUtil.parseModule(argMultimap.getValue(PREFIX_CONTACT_COURSE_LONG).get());
        Group group = ContactParserUtil.parseGroup(argMultimap.getValue(PREFIX_CONTACT_GROUP_LONG).get());
        Set<Tag> tagList = ContactParserUtil.parseTags(argMultimap.getAllValues(PREFIX_CONTACT_TAG_LONG));

        Contact contact = new Contact(id, name, email, course, group, tagList);
        return new AddContactCommand(contact);
    }
}
