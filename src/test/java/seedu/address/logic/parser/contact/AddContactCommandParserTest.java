package seedu.address.logic.parser.contact;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_COURSE_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_LONG;
import static seedu.address.testutil.TypicalContacts.AMY;
import static seedu.address.testutil.TypicalContacts.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.create.AddContactCommand;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Tag;
import seedu.address.testutil.ContactBuilder;

public class AddContactCommandParserTest {
    private final AddContactCommandParser parser = new AddContactCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Contact expectedContact = new ContactBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + ID_DESC_BOB + NAME_DESC_BOB + EMAIL_DESC_BOB
                        + COURSE_DESC_BOB + GROUP_DESC_BOB + TAG_DESC_FRIEND,
                new AddContactCommand(expectedContact));


        // multiple tags - all accepted
        Contact expectedContactMultipleTags = new ContactBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                ID_DESC_BOB + NAME_DESC_BOB + EMAIL_DESC_BOB
                        + COURSE_DESC_BOB + GROUP_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddContactCommand(expectedContactMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedContactString = ID_DESC_BOB + NAME_DESC_BOB
                + EMAIL_DESC_BOB + COURSE_DESC_BOB + GROUP_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACT_NAME_LONG));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACT_EMAIL_LONG));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedContactString + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACT_NAME_LONG, PREFIX_CONTACT_ID_LONG,
                    PREFIX_CONTACT_EMAIL_LONG,
                    PREFIX_CONTACT_COURSE_LONG, PREFIX_CONTACT_GROUP_LONG));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACT_NAME_LONG));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACT_EMAIL_LONG));

        // invalid name
        assertParseFailure(parser, validExpectedContactString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACT_NAME_LONG));

        // invalid email
        assertParseFailure(parser, validExpectedContactString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACT_EMAIL_LONG));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Contact expectedContact = new ContactBuilder(AMY).withTags().build();
        assertParseSuccess(parser, ID_DESC_AMY + NAME_DESC_AMY
                        + EMAIL_DESC_AMY + COURSE_DESC_AMY + GROUP_DESC_AMY,
                new AddContactCommand(expectedContact));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_EMAIL_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, ID_DESC_BOB + INVALID_NAME_DESC
                + EMAIL_DESC_BOB + COURSE_DESC_BOB + GROUP_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, ID_DESC_BOB + NAME_DESC_BOB
                + INVALID_EMAIL_DESC + COURSE_DESC_BOB + GROUP_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, ID_DESC_BOB + NAME_DESC_BOB + EMAIL_DESC_BOB
                        + COURSE_DESC_BOB + GROUP_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, ID_DESC_BOB + INVALID_NAME_DESC
                        + EMAIL_DESC_BOB + COURSE_DESC_BOB + GROUP_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + ID_DESC_BOB + NAME_DESC_BOB
                        + EMAIL_DESC_BOB + GROUP_DESC_BOB + VALID_GROUP_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
    }
}
