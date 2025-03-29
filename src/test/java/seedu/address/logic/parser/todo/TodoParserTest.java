package seedu.address.logic.parser.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.create.AddTodoCommand;
import seedu.address.logic.commands.delete.DeleteTodoCommand;
import seedu.address.logic.commands.read.InfoTodoCommand;
import seedu.address.logic.commands.read.ListTodoCommand;
import seedu.address.logic.commands.update.MarkTodoAsDoneCommand;
import seedu.address.logic.commands.update.MarkTodoAsNotDoneCommand;
import seedu.address.logic.parser.ParserImpl;
import seedu.address.model.todo.Todo;
import seedu.address.testutil.TodoBuilder;
import seedu.address.testutil.TodoUtil;

public class TodoParserTest {

    private final ParserImpl parser = new ParserImpl();

    @Test
    public void parseCommand_add() throws Exception {
        Todo todo = new TodoBuilder().build();
        AddTodoCommand command =
                (AddTodoCommand) parser.parseCommand(TodoUtil.getAddCommand(todo));
        assertEquals(new AddTodoCommand(todo), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteTodoCommand command = (DeleteTodoCommand) parser.parseCommand(
                TODO_COMMAND_WORD + " " + DeleteTodoCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteTodoCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_displayInfo() throws Exception {
        InfoTodoCommand command = (InfoTodoCommand) parser.parseCommand(
                TODO_COMMAND_WORD + " " + InfoTodoCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased());
        assertEquals(new InfoTodoCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_markAsDone() throws Exception {
        MarkTodoAsDoneCommand command = (MarkTodoAsDoneCommand) parser.parseCommand(
                TODO_COMMAND_WORD + " " + MarkTodoAsDoneCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased());
        assertEquals(new MarkTodoAsDoneCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_markAsNotDone() throws Exception {
        MarkTodoAsNotDoneCommand command = (MarkTodoAsNotDoneCommand) parser.parseCommand(
                TODO_COMMAND_WORD + " " + MarkTodoAsNotDoneCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased());
        assertEquals(new MarkTodoAsNotDoneCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(TODO_COMMAND_WORD + " "
                + ListTodoCommand.COMMAND_WORD) instanceof ListTodoCommand);
        assertTrue(parser.parseCommand(TODO_COMMAND_WORD + " "
                + ListTodoCommand.COMMAND_WORD + " 3") instanceof ListTodoCommand);
    }
}
