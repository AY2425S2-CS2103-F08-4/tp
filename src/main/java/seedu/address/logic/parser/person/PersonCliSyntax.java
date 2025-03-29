package seedu.address.logic.parser.person;

import seedu.address.logic.parser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple todo commands.
 */
public class PersonCliSyntax {

    /* Long Prefix definitions */
    public static final Prefix PREFIX_PERSON_NAME_LONG = new Prefix("--name");
    public static final Prefix PREFIX_PERSON_PHONE_LONG = new Prefix("--phone");
    public static final Prefix PREFIX_PERSON_EMAIL_LONG = new Prefix("--email");
    public static final Prefix PREFIX_PERSON_ID_LONG = new Prefix("--id");
    public static final Prefix PREFIX_PERSON_COURSE_LONG = new Prefix("--course");
    public static final Prefix PREFIX_PERSON_GROUP_LONG = new Prefix("--group");
    public static final Prefix PREFIX_PERSON_TAG_LONG = new Prefix("--tag");

    /* Short Prefix definitions */
    public static final Prefix PREFIX_PERSON_NAME_SHORT = new Prefix("-n");
    public static final Prefix PREFIX_PERSON_PHONE_SHORT = new Prefix("-p");
    public static final Prefix PREFIX_PERSON_EMAIL_SHORT = new Prefix("-e");
    public static final Prefix PREFIX_PERSON_ID_SHORT = new Prefix("-i");
    public static final Prefix PREFIX_PERSON_COURSE_SHORT = new Prefix("-c");
    public static final Prefix PREFIX_PERSON_GROUP_SHORT = new Prefix("-g");
    public static final Prefix PREFIX_PERSON_TAG_SHORT = new Prefix("-t");

}
