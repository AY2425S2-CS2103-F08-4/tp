package seedu.address.logic.parser.todo;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.TodoMessages.MESSAGE_MISSING_PERSON_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_PERSON_LONG;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.todo.RemovePersonFromTodoCommand;

public class RemovePersonFromTodoCommandParserTest {

    private RemovePersonFromTodoCommandParser parser = new RemovePersonFromTodoCommandParser();

    @Test
    public void parse_validArgs1_returnsRemovePersonFromTodoCommand() {
        assertParseSuccess(parser, "1 " + PREFIX_TODO_LINKED_PERSON_LONG + "1 2",
                new RemovePersonFromTodoCommand(INDEX_FIRST, List.of(INDEX_FIRST, INDEX_SECOND)));
    }

    @Test
    public void parse_validArgs2_returnsRemovePersonFromTodoCommand() {
        assertParseSuccess(parser, "1 " + PREFIX_TODO_LINKED_PERSON_LONG + "1",
                new RemovePersonFromTodoCommand(INDEX_FIRST, List.of(INDEX_FIRST)));
    }

    @Test
    public void parse_missingTodoIndex_throwsParseException() {
        assertParseFailure(parser, PREFIX_TODO_LINKED_PERSON_LONG + "1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        RemovePersonFromTodoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemovePersonFromTodoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noLinkedPersons_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_TODO_LINKED_PERSON_LONG, MESSAGE_MISSING_PERSON_INDEX);
    }

    @Test
    public void parse_invalidTodoIndex_throwsParseException() {
        assertParseFailure(parser, "a " + PREFIX_TODO_LINKED_PERSON_LONG + "1 2",
                String.format(MESSAGE_INVALID_INDEX, "a"));
    }

    @Test
    public void parse_invalidPersonIndex_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_TODO_LINKED_PERSON_LONG + "a 2",
                String.format(MESSAGE_INVALID_INDEX, "a"));
    }
}
