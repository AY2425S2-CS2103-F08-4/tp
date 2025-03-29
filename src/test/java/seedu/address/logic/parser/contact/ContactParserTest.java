package seedu.address.logic.parser.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.contact.AddContactCommand;
import seedu.address.logic.commands.contact.ClearContactCommand;
import seedu.address.logic.commands.contact.DeleteContactCommand;
import seedu.address.logic.commands.contact.EditContactCommand;
import seedu.address.logic.commands.contact.EditContactCommand.EditContactDescriptor;
import seedu.address.logic.commands.contact.ListContactCommand;
import seedu.address.logic.parser.ParserImpl;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.ContactBuilder;
import seedu.address.testutil.ContactUtil;
import seedu.address.testutil.EditContactDescriptorBuilder;

public class ContactParserTest {

    private final ParserImpl parser = new ParserImpl();

    @Test
    public void parseCommand_add() throws Exception {
        Contact contact = new ContactBuilder().build();
        AddContactCommand command =
                (AddContactCommand) parser.parseCommand(ContactUtil.getAddCommand(contact));
        assertEquals(new AddContactCommand(contact), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(CONTACT_COMMAND_WORD + " "
                + ClearContactCommand.COMMAND_WORD) instanceof ClearContactCommand);
        assertTrue(parser.parseCommand(CONTACT_COMMAND_WORD + " "
                + ClearContactCommand.COMMAND_WORD + " 3") instanceof ClearContactCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteContactCommand command = (DeleteContactCommand) parser.parseCommand(
                CONTACT_COMMAND_WORD + " " + DeleteContactCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteContactCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Contact contact = new ContactBuilder().build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(contact).build();
        EditContactCommand command =
                (EditContactCommand) parser.parseCommand(CONTACT_COMMAND_WORD + " "
                        + EditContactCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + " "
                        + ContactUtil.getEditContactDescriptorDetails(descriptor));
        assertEquals(new EditContactCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(CONTACT_COMMAND_WORD + " "
                + ListContactCommand.COMMAND_WORD) instanceof ListContactCommand);
        assertTrue(parser.parseCommand(CONTACT_COMMAND_WORD + " "
                + ListContactCommand.COMMAND_WORD + " 3") instanceof ListContactCommand);
    }
}
