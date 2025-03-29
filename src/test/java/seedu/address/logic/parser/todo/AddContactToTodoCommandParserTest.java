package seedu.address.logic.parser.todo;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.TodoMessages.MESSAGE_MISSING_CONTACT_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_CONTACT_LONG;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.todo.AddContactToTodoCommand;

public class AddContactToTodoCommandParserTest {

    private AddContactToTodoCommandParser parser = new AddContactToTodoCommandParser();

    @Test
    public void parse_validArgs1_returnsAddContactToTodoCommand() {
        assertParseSuccess(parser, "1 " + PREFIX_TODO_LINKED_CONTACT_LONG + "1 2",
                new AddContactToTodoCommand(INDEX_FIRST, List.of(INDEX_FIRST, INDEX_SECOND)));
    }

    @Test
    public void parse_validArgs2_returnsAddContactToTodoCommand() {
        assertParseSuccess(parser, "1 " + PREFIX_TODO_LINKED_CONTACT_LONG + "1",
                new AddContactToTodoCommand(INDEX_FIRST, List.of(INDEX_FIRST)));
    }

    @Test
    public void parse_missingTodoIndex_throwsParseException() {
        assertParseFailure(parser, PREFIX_TODO_LINKED_CONTACT_LONG + "1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddContactToTodoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddContactToTodoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noLinkedContacts_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_TODO_LINKED_CONTACT_LONG, MESSAGE_MISSING_CONTACT_INDEX);
    }

    @Test
    public void parse_invalidTodoIndex_throwsParseException() {
        assertParseFailure(parser, "a " + PREFIX_TODO_LINKED_CONTACT_LONG + "1 2",
                String.format(MESSAGE_INVALID_INDEX, "a"));
    }

    @Test
    public void parse_invalidContactIndex_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_TODO_LINKED_CONTACT_LONG + "a 2",
                String.format(MESSAGE_INVALID_INDEX, "a"));
    }
}
