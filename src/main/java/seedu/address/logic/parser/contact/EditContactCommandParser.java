package seedu.address.logic.parser.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_COURSE_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.EditContactCommand;
import seedu.address.logic.commands.contact.EditContactCommand.EditContactDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditContactCommandParser implements Parser<EditContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CONTACT_ID_LONG,
            PREFIX_CONTACT_NAME_LONG, PREFIX_CONTACT_EMAIL_LONG, PREFIX_CONTACT_TAG_LONG, PREFIX_CONTACT_COURSE_LONG,
            PREFIX_CONTACT_GROUP_LONG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditContactCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CONTACT_ID_LONG, PREFIX_CONTACT_NAME_LONG,
            PREFIX_CONTACT_EMAIL_LONG, PREFIX_CONTACT_COURSE_LONG, PREFIX_CONTACT_GROUP_LONG);

        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();

        if (argMultimap.getValue(PREFIX_CONTACT_ID_LONG).isPresent()) {
            editContactDescriptor.setId(ContactParserUtil.parseId(argMultimap.getValue(PREFIX_CONTACT_ID_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTACT_NAME_LONG).isPresent()) {
            editContactDescriptor.setName(
                ContactParserUtil.parseName(argMultimap.getValue(PREFIX_CONTACT_NAME_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTACT_EMAIL_LONG).isPresent()) {
            editContactDescriptor.setEmail(
                ContactParserUtil.parseEmail(argMultimap.getValue(PREFIX_CONTACT_EMAIL_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTACT_COURSE_LONG).isPresent()) {
            editContactDescriptor.setCourse(ContactParserUtil
                .parseModule(argMultimap.getValue(PREFIX_CONTACT_COURSE_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTACT_GROUP_LONG).isPresent()) {
            editContactDescriptor.setGroup(
                ContactParserUtil.parseGroup(argMultimap.getValue(PREFIX_CONTACT_GROUP_LONG).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_CONTACT_TAG_LONG)).ifPresent(editContactDescriptor::setTags);

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditContactCommand.MESSAGE_NOT_EDITED);
        }

        return new EditContactCommand(index, editContactDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet()
            : tags;
        return Optional.of(ContactParserUtil.parseTags(tagSet));
    }

}
