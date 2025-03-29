package seedu.address.logic.parser.todo;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.update.MarkTodoAsDoneCommand;

public class MarkTodoAsDoneCommandParserTest {

    private MarkTodoAsDoneCommandParser parser = new MarkTodoAsDoneCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new MarkTodoAsDoneCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_INDEX, "a"));
    }

}
