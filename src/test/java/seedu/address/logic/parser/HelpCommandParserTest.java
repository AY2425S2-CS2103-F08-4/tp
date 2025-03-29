package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.contact.AddContactCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.todo.AddTodoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandParserTest {
    private final HelpCommandParser parser = new HelpCommandParser();
    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();

    @Test
    public void parse_helpCommandWithoutArgs_success() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(parser.parse(""), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void parse_helpCommandContact_success() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommandParser.MESSAGE_CONTACT_COMMANDS, false,
            false);
        assertCommandSuccess(parser.parse(CONTACT_COMMAND_WORD), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void parse_helpCommandTodo_success() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommandParser.MESSAGE_TODO_COMMANDS, false, false);
        assertCommandSuccess(parser.parse(TODO_COMMAND_WORD), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void parse_helpCommandEvent_success() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommandParser.MESSAGE_EVENT_COMMANDS, false, false);
        assertCommandSuccess(parser.parse(EVENT_COMMAND_WORD), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void parse_helpCommandAddContact_success() {
        CommandResult expectedCommandResult = new CommandResult(AddContactCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(parser.parse(CONTACT_COMMAND_WORD + " " + AddContactCommand.COMMAND_WORD), model,
            expectedCommandResult, expectedModel);
    }

    @Test
    public void parse_helpCommandAddTodo_success() {
        CommandResult expectedCommandResult = new CommandResult(AddTodoCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(parser.parse(TODO_COMMAND_WORD + " " + AddTodoCommand.COMMAND_WORD), model,
            expectedCommandResult, expectedModel);
    }

    @Test
    public void parse_helpCommandAddEvent_success() {
        CommandResult expectedCommandResult = new CommandResult(AddEventCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(parser.parse(EVENT_COMMAND_WORD + " " + AddEventCommand.COMMAND_WORD), model,
            expectedCommandResult, expectedModel);
    }

    @Test
    public void parse_helpCommandInvalidFeature_fallbackToGeneralHelp() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommandParser.MESSAGE_UNKNOWN_COMMAND, false,
            false);
        assertCommandSuccess(parser.parse("invalid"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void parse_helpCommandInvalidSubcommand_fallbackToFeatureHelp() {
        CommandResult expectedCommandResult = new CommandResult(
            "Subcommand invalid not recognized.\n" + HelpCommandParser.MESSAGE_CONTACT_COMMANDS, false, false);
        assertCommandSuccess(parser.parse(CONTACT_COMMAND_WORD + " invalid"), model, expectedCommandResult,
            expectedModel);
    }
}
