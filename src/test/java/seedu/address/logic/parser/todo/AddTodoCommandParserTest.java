package seedu.address.logic.parser.todo;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ContactCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.ContactCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.TodoCommandTestUtil.DEADLINE_DESC_GRADING;
import static seedu.address.logic.commands.TodoCommandTestUtil.DEADLINE_DESC_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_DEADLINE_DESC_INCORRECT_FORMAT;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_DEADLINE_DESC_NOT_DATETIME;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_LOCATION_DESC;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_NAME_DESC;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_TAG_DESC;
import static seedu.address.logic.commands.TodoCommandTestUtil.LOCATION_DESC_GRADING;
import static seedu.address.logic.commands.TodoCommandTestUtil.LOCATION_DESC_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.NAME_DESC_GRADING;
import static seedu.address.logic.commands.TodoCommandTestUtil.NAME_DESC_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.TAG_DESC_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.TAG_DESC_REPORT_MULTIPLE;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_DEADLINE_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_LOCATION_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_NAME_REPORT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_TAG_LONG;
import static seedu.address.testutil.TypicalTodos.GRADING;
import static seedu.address.testutil.TypicalTodos.REPORT_WITH_MULTIPLE_TAGS;
import static seedu.address.testutil.TypicalTodos.REPORT_WITH_TAG;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.create.AddTodoCommand;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;

public class AddTodoCommandParserTest {
    private final AddTodoCommandParser parser = new AddTodoCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_GRADING + DEADLINE_DESC_GRADING
                        + LOCATION_DESC_GRADING,
                new AddTodoCommand(GRADING));

        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_REPORT + DEADLINE_DESC_REPORT
                        + LOCATION_DESC_REPORT + TAG_DESC_REPORT,
                new AddTodoCommand(REPORT_WITH_TAG));

        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_REPORT + DEADLINE_DESC_REPORT
                        + LOCATION_DESC_REPORT + TAG_DESC_REPORT_MULTIPLE,
                new AddTodoCommand(REPORT_WITH_MULTIPLE_TAGS));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedTodoString = NAME_DESC_GRADING + DEADLINE_DESC_GRADING
                + LOCATION_DESC_GRADING + TAG_DESC_REPORT_MULTIPLE;

        // multiple names
        assertParseFailure(parser, NAME_DESC_REPORT + validExpectedTodoString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_NAME_LONG));

        // multiple deadlines
        assertParseFailure(parser, DEADLINE_DESC_REPORT + validExpectedTodoString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_DEADLINE_LONG));

        // multiple locations
        assertParseFailure(parser, LOCATION_DESC_GRADING + validExpectedTodoString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_LOCATION_LONG));

        // multiple tags
        assertParseFailure(parser, TAG_DESC_REPORT + validExpectedTodoString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_TAG_LONG));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedTodoString + NAME_DESC_REPORT + DEADLINE_DESC_REPORT
                        + LOCATION_DESC_GRADING + TAG_DESC_REPORT_MULTIPLE + validExpectedTodoString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_NAME_LONG,
                        PREFIX_TODO_DEADLINE_LONG,
                        PREFIX_TODO_LOCATION_LONG, PREFIX_TODO_TAG_LONG));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_TODO_NAME_DESC + validExpectedTodoString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_NAME_LONG));

        // invalid deadline
        assertParseFailure(parser, INVALID_TODO_DEADLINE_DESC_INCORRECT_FORMAT
                        + validExpectedTodoString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_DEADLINE_LONG));

        // invalid location
        assertParseFailure(parser, INVALID_TODO_LOCATION_DESC
                        + validExpectedTodoString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_LOCATION_LONG));

        // invalid tag
        assertParseFailure(parser, INVALID_TODO_TAG_DESC
                        + validExpectedTodoString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_TAG_LONG));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedTodoString + INVALID_TODO_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_NAME_LONG));

        // invalid deadline
        assertParseFailure(parser, validExpectedTodoString
                        + INVALID_TODO_DEADLINE_DESC_INCORRECT_FORMAT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_DEADLINE_LONG));

        // invalid location
        assertParseFailure(parser, validExpectedTodoString
                        + INVALID_TODO_LOCATION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_LOCATION_LONG));

        // invalid tag
        assertParseFailure(parser, validExpectedTodoString
                        + INVALID_TODO_TAG_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_TAG_LONG));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTodoCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, LOCATION_DESC_REPORT + DEADLINE_DESC_REPORT,
                expectedMessage);

        // missing deadline prefix
        assertParseFailure(parser, NAME_DESC_REPORT + LOCATION_DESC_REPORT,
                expectedMessage);

        // missing location prefix
        assertParseFailure(parser, NAME_DESC_REPORT + DEADLINE_DESC_REPORT,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_REPORT + VALID_LOCATION_REPORT
                        + VALID_DEADLINE_REPORT,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_TODO_NAME_DESC + DEADLINE_DESC_GRADING
                + LOCATION_DESC_GRADING, Name.MESSAGE_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, NAME_DESC_GRADING + INVALID_TODO_DEADLINE_DESC_INCORRECT_FORMAT
                + LOCATION_DESC_GRADING, Datetime.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_GRADING + INVALID_TODO_DEADLINE_DESC_NOT_DATETIME
                + LOCATION_DESC_GRADING, Datetime.MESSAGE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, NAME_DESC_GRADING + DEADLINE_DESC_GRADING
                + INVALID_TODO_LOCATION_DESC, Location.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_GRADING + DEADLINE_DESC_GRADING
                + LOCATION_DESC_GRADING + INVALID_TODO_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_GRADING + DEADLINE_DESC_GRADING
                        + LOCATION_DESC_GRADING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTodoCommand.MESSAGE_USAGE));
    }
}
