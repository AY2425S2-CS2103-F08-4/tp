package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.BYE_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EXIT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.HELP_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.KILL_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.QUIT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.contact.AddContactCommand;
import seedu.address.logic.commands.contact.AddTagToContactCommand;
import seedu.address.logic.commands.contact.ClearContactCommand;
import seedu.address.logic.commands.contact.DeleteContactCommand;
import seedu.address.logic.commands.contact.EditContactCommand;
import seedu.address.logic.commands.contact.FilterContactCommand;
import seedu.address.logic.commands.contact.InfoContactCommand;
import seedu.address.logic.commands.contact.ListContactCommand;
import seedu.address.logic.commands.contact.RemoveTagFromContactCommand;
import seedu.address.logic.commands.event.AddContactToEventCommand;
import seedu.address.logic.commands.event.AddContactToLogEventCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.event.AddTagToEventCommand;
import seedu.address.logic.commands.event.ClearEventCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.event.DisplayEventInformationCommand;
import seedu.address.logic.commands.event.FilterEventCommand;
import seedu.address.logic.commands.event.ListEventCommand;
import seedu.address.logic.commands.event.RemoveContactFromEventCommand;
import seedu.address.logic.commands.event.RemoveContactFromLogEventCommand;
import seedu.address.logic.commands.event.RemoveTagFromEventCommand;
import seedu.address.logic.commands.todo.AddContactToTodoCommand;
import seedu.address.logic.commands.todo.AddTagToTodoCommand;
import seedu.address.logic.commands.todo.AddTodoCommand;
import seedu.address.logic.commands.todo.ClearTodoCommand;
import seedu.address.logic.commands.todo.DeleteTodoCommand;
import seedu.address.logic.commands.todo.DisplayTodoInformationCommand;
import seedu.address.logic.commands.todo.FilterTodoCommand;
import seedu.address.logic.commands.todo.ListTodoCommand;
import seedu.address.logic.commands.todo.MarkTodoAsDoneCommand;
import seedu.address.logic.commands.todo.MarkTodoAsNotDoneCommand;
import seedu.address.logic.commands.todo.RemoveContactFromTodoCommand;
import seedu.address.logic.commands.todo.RemoveTagFromTodoCommand;

/**
 * Parses input arguments and creates a new HelpCommand object.
 *
 * <p>
 * This parser handles the "help" command and its subcommands for different feature categories:
 * - Contact: Manages your contacts.
 * - Todo: Manages your todos.
 * - Event: Manages your events.
 *
 * <p>For each category, specific subcommands are supported, and their usage instructions can
 * be displayed using the help command with the appropriate arguments.
 *
 * <p>For invalid inputs, the closest valid command usage is returned. All trailing arguments
 * that are not supported are immediately dropped without errors.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Feature not recognized.\n" + HelpCommand.MESSAGE_USAGE;
    public static final String MESSAGE_CONTACT_COMMANDS = CONTACT_COMMAND_WORD
        + ": Manages your contacts. Supported subcommands:\n"
        + AddContactCommand.COMMAND_WORD + ", "
        + EditContactCommand.COMMAND_WORD + ", "
        + DeleteContactCommand.COMMAND_WORD + ", "
        + ClearContactCommand.COMMAND_WORD + ", "
        + FilterContactCommand.COMMAND_WORD + ", "
        + ListContactCommand.COMMAND_WORD + ", "
        + InfoContactCommand.COMMAND_WORD + ", "
        + AddTagToContactCommand.COMMAND_WORD + ", "
        + RemoveTagFromContactCommand.COMMAND_WORD;

    public static final String MESSAGE_TODO_COMMANDS = TODO_COMMAND_WORD
        + ": Manages your todos. Supported subcommands:\n"
        + AddTodoCommand.COMMAND_WORD + ", "
        + DeleteTodoCommand.COMMAND_WORD + ", "
        + DisplayTodoInformationCommand.COMMAND_WORD + ", "
        + ListTodoCommand.COMMAND_WORD + ", "
        + AddContactToTodoCommand.COMMAND_WORD + ", "
        + RemoveContactFromTodoCommand.COMMAND_WORD + ", "
        + MarkTodoAsDoneCommand.COMMAND_WORD + ", "
        + MarkTodoAsNotDoneCommand.COMMAND_WORD + ", "
        + AddTagToTodoCommand.COMMAND_WORD + ", "
        + RemoveTagFromTodoCommand.COMMAND_WORD + ", "
        + FilterTodoCommand.COMMAND_WORD + ", "
        + ClearTodoCommand.COMMAND_WORD;

    public static final String MESSAGE_EVENT_COMMANDS = EVENT_COMMAND_WORD
        + ": Manages your events. Supported subcommands:\n"
        + AddEventCommand.COMMAND_WORD + ", "
        + DeleteEventCommand.COMMAND_WORD + ", "
        + DisplayEventInformationCommand.COMMAND_WORD + ", "
        + ListEventCommand.COMMAND_WORD + ", "
        + FilterEventCommand.COMMAND_WORD + ", "
        + AddContactToEventCommand.COMMAND_WORD + ", "
        + RemoveContactFromEventCommand.COMMAND_WORD + ", "
        + AddTagToEventCommand.COMMAND_WORD + ", "
        + RemoveTagFromEventCommand.COMMAND_WORD + ", "
        + AddContactToLogEventCommand.COMMAND_WORD + ", "
        + RemoveContactFromLogEventCommand.COMMAND_WORD + ", "
        + ClearEventCommand.COMMAND_WORD;

    /**
     * Parses the given {@code String} of arguments into a HelpCommand.
     *
     * <p>Valid argument lengths:
     * - 0: "help" -> ""
     * - 1: "help contact" -> "contact"
     * - 2: "help contact add" -> "contact add"
     *
     * <p>For invalid cases, defaults to the closest valid command:
     * - "help abcd" -> "help"
     * - "help contact abcd" -> "help contact"
     * - "help contact add abcd" -> "help contact add"
     *
     * <p>All trailing arguments that are not supported are immediately dropped without errors.
     *
     * @param args The input arguments.
     * @return HelpCommand that contains the message to display.
     */
    @Override
    public HelpCommand parse(String args) {
        // Handle empty inputs -> help
        if (args.trim().isEmpty()) {
            return new HelpCommand(HelpCommand.MESSAGE_USAGE);
        }

        // Split command
        String[] argSplit = args.trim().split("\\s+");
        String command = argSplit[0];
        String subcommand = argSplit.length > 1 ? argSplit[1] : "";

        // Check that top level command is valid
        switch (command) {
        case CONTACT_COMMAND_WORD:
            return parseContactHelp(subcommand);
        case TODO_COMMAND_WORD:
            return parseTodoHelp(subcommand);
        case EVENT_COMMAND_WORD:
            return parseEventHelp(subcommand);
        case EXIT_COMMAND_WORD:
        case BYE_COMMAND_WORD:
        case QUIT_COMMAND_WORD:
        case KILL_COMMAND_WORD:
            // Ignore all trailing arguments
            return new HelpCommand(ExitCommand.MESSAGE_USAGE);
        case HELP_COMMAND_WORD:
            return new HelpCommand("Very funny. Here you go.\n" + HelpCommand.MESSAGE_USAGE);
        default:
            // No match, default to HelpCommand
            return new HelpCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private HelpCommand parseContactHelp(String subcommand) {
        if (subcommand.trim().isEmpty()) {
            return new HelpCommand(MESSAGE_CONTACT_COMMANDS);
        }

        switch (subcommand) {
        case AddContactCommand.COMMAND_WORD:
            return new HelpCommand(AddContactCommand.MESSAGE_USAGE);
        case EditContactCommand.COMMAND_WORD:
            return new HelpCommand(EditContactCommand.MESSAGE_USAGE);
        case DeleteContactCommand.COMMAND_WORD:
            return new HelpCommand(DeleteContactCommand.MESSAGE_USAGE);
        case ClearContactCommand.COMMAND_WORD:
            return new HelpCommand(ClearContactCommand.MESSAGE_USAGE);
        case FilterContactCommand.COMMAND_WORD:
            return new HelpCommand(FilterContactCommand.MESSAGE_USAGE);
        case ListContactCommand.COMMAND_WORD:
            return new HelpCommand(ListContactCommand.MESSAGE_USAGE);
        case InfoContactCommand.COMMAND_WORD:
            return new HelpCommand(InfoContactCommand.MESSAGE_USAGE);
        case AddTagToContactCommand.COMMAND_WORD:
            return new HelpCommand(AddTagToContactCommand.MESSAGE_USAGE);
        case RemoveTagFromContactCommand.COMMAND_WORD:
            return new HelpCommand(RemoveTagFromContactCommand.MESSAGE_USAGE);
        default:
            return new HelpCommand("Subcommand " + subcommand + " not recognized.\n" + MESSAGE_CONTACT_COMMANDS);
        }
    }


    private HelpCommand parseTodoHelp(String subcommand) {
        if (subcommand.trim().isEmpty()) {
            return new HelpCommand(MESSAGE_TODO_COMMANDS);
        }

        switch (subcommand) {
        case AddTodoCommand.COMMAND_WORD:
            return new HelpCommand(AddTodoCommand.MESSAGE_USAGE);
        case DeleteTodoCommand.COMMAND_WORD:
            return new HelpCommand(DeleteTodoCommand.MESSAGE_USAGE);
        case DisplayTodoInformationCommand.COMMAND_WORD:
            return new HelpCommand(DisplayTodoInformationCommand.MESSAGE_USAGE);
        case ListTodoCommand.COMMAND_WORD:
            return new HelpCommand(ListTodoCommand.MESSAGE_USAGE);
        case AddContactToTodoCommand.COMMAND_WORD:
            return new HelpCommand(AddContactToTodoCommand.MESSAGE_USAGE);
        case RemoveContactFromTodoCommand.COMMAND_WORD:
            return new HelpCommand(RemoveContactFromTodoCommand.MESSAGE_USAGE);
        case MarkTodoAsDoneCommand.COMMAND_WORD:
            return new HelpCommand(MarkTodoAsDoneCommand.MESSAGE_USAGE);
        case MarkTodoAsNotDoneCommand.COMMAND_WORD:
            return new HelpCommand(MarkTodoAsNotDoneCommand.MESSAGE_USAGE);
        case AddTagToTodoCommand.COMMAND_WORD:
            return new HelpCommand(AddTagToTodoCommand.MESSAGE_USAGE);
        case RemoveTagFromTodoCommand.COMMAND_WORD:
            return new HelpCommand(RemoveTagFromTodoCommand.MESSAGE_USAGE);
        case ClearTodoCommand.COMMAND_WORD:
            return new HelpCommand(ClearTodoCommand.MESSAGE_USAGE);
        case FilterTodoCommand.COMMAND_WORD:
            return new HelpCommand(FilterTodoCommand.MESSAGE_USAGE);
        default:
            return new HelpCommand("Subcommand " + subcommand + " not recognized.\n" + MESSAGE_TODO_COMMANDS);
        }
    }


    private HelpCommand parseEventHelp(String subcommand) {
        if (subcommand.trim().isEmpty()) {
            return new HelpCommand(MESSAGE_EVENT_COMMANDS);
        }

        switch (subcommand) {
        case AddEventCommand.COMMAND_WORD:
            return new HelpCommand(AddEventCommand.MESSAGE_USAGE);
        case DeleteEventCommand.COMMAND_WORD:
            return new HelpCommand(DeleteEventCommand.MESSAGE_USAGE);
        case DisplayEventInformationCommand.COMMAND_WORD:
            return new HelpCommand(DisplayEventInformationCommand.MESSAGE_USAGE);
        case ListEventCommand.COMMAND_WORD:
            return new HelpCommand(ListEventCommand.MESSAGE_USAGE);
        case AddContactToEventCommand.COMMAND_WORD:
            return new HelpCommand(AddContactToEventCommand.MESSAGE_USAGE);
        case RemoveContactFromEventCommand.COMMAND_WORD:
            return new HelpCommand(RemoveContactFromEventCommand.MESSAGE_USAGE);
        case AddContactToLogEventCommand.COMMAND_WORD:
            return new HelpCommand(AddContactToLogEventCommand.MESSAGE_USAGE);
        case RemoveContactFromLogEventCommand.COMMAND_WORD:
            return new HelpCommand(RemoveContactFromLogEventCommand.MESSAGE_USAGE);
        case FilterEventCommand.COMMAND_WORD:
            return new HelpCommand(FilterEventCommand.MESSAGE_USAGE);
        case AddTagToEventCommand.COMMAND_WORD:
            return new HelpCommand(AddTagToEventCommand.MESSAGE_USAGE);
        case RemoveTagFromEventCommand.COMMAND_WORD:
            return new HelpCommand(RemoveTagFromEventCommand.MESSAGE_USAGE);
        case ClearEventCommand.COMMAND_WORD:
            return new HelpCommand(ClearEventCommand.MESSAGE_USAGE);
        default:
            return new HelpCommand("Subcommand " + subcommand + " not recognized.\n" + MESSAGE_EVENT_COMMANDS);
        }
    }
}
