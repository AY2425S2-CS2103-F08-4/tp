package seedu.address.logic.parser.event;

import seedu.address.logic.parser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple event commands.
 */
public class EventCliSyntax {

    /* Long prefix definitions */
    public static final Prefix PREFIX_EVENT_NAME_LONG = new Prefix("--name");
    public static final Prefix PREFIX_EVENT_START_LONG = new Prefix("--start");
    public static final Prefix PREFIX_EVENT_END_LONG = new Prefix("--end");
    public static final Prefix PREFIX_EVENT_LOCATION_LONG = new Prefix("--location");
    public static final Prefix PREFIX_EVENT_TAG_LONG = new Prefix("--tag");
    public static final Prefix PREFIX_EVENT_LINKED_PERSON_LONG = new Prefix("--person");

    /* Short prefix definitions */
    public static final Prefix PREFIX_EVENT_NAME_SHORT = new Prefix("-n");
    public static final Prefix PREFIX_EVENT_START_SHORT = new Prefix("-s");
    public static final Prefix PREFIX_EVENT_END_SHORT = new Prefix("-e");
    public static final Prefix PREFIX_EVENT_LOCATION_SHORT = new Prefix("-l");
    public static final Prefix PREFIX_EVENT_TAG_SHORT = new Prefix("-t");
    public static final Prefix PREFIX_EVENT_LINKED_PERSON_SHORT = new Prefix("-p");
}
