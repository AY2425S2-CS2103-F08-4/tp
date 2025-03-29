package seedu.address.logic.parser.person;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_COURSE_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_EMAIL_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_GROUP_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_ID_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_NAME_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_PHONE_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_TAG_LONG;

import java.util.Set;

import seedu.address.logic.commands.person.AddPersonCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Group;
import seedu.address.model.person.Id;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddPersonCommandParser implements Parser<AddPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON_ID_LONG, PREFIX_PERSON_NAME_LONG,
            PREFIX_PERSON_PHONE_LONG, PREFIX_PERSON_EMAIL_LONG, PREFIX_PERSON_TAG_LONG, PREFIX_PERSON_COURSE_LONG,
            PREFIX_PERSON_GROUP_LONG);

        if (!argMultimap.arePrefixesPresent(PREFIX_PERSON_ID_LONG, PREFIX_PERSON_NAME_LONG, PREFIX_PERSON_PHONE_LONG,
            PREFIX_PERSON_EMAIL_LONG, PREFIX_PERSON_COURSE_LONG, PREFIX_PERSON_GROUP_LONG)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON_ID_LONG, PREFIX_PERSON_NAME_LONG,
            PREFIX_PERSON_PHONE_LONG, PREFIX_PERSON_EMAIL_LONG, PREFIX_PERSON_COURSE_LONG, PREFIX_PERSON_GROUP_LONG);
        Id id = PersonParserUtil.parseId(argMultimap.getValue(PREFIX_PERSON_ID_LONG).get());
        Name name = PersonParserUtil.parseName(argMultimap.getValue(PREFIX_PERSON_NAME_LONG).get());
        Phone phone = PersonParserUtil.parsePhone(argMultimap.getValue(PREFIX_PERSON_PHONE_LONG).get());
        Email email = PersonParserUtil.parseEmail(argMultimap.getValue(PREFIX_PERSON_EMAIL_LONG).get());
        Course course = PersonParserUtil.parseModule(argMultimap.getValue(PREFIX_PERSON_COURSE_LONG).get());
        Group group = PersonParserUtil.parseGroup(argMultimap.getValue(PREFIX_PERSON_GROUP_LONG).get());
        Set<Tag> tagList = PersonParserUtil.parseTags(argMultimap.getAllValues(PREFIX_PERSON_TAG_LONG));

        Person person = new Person(id, name, phone, email, course, group, tagList);
        return new AddPersonCommand(person);
    }
}
