package seedu.address.model.contact;

/**
 * Represents the different columns or attributes of a Contact that can be used for filtering.
 * Each column has an associated abbreviation for easy reference.
 */
public enum ContactColumn {
    NAME("n"),
    EMAIL("e"),
    ID("i"),
    COURSE("c"),
    GROUP("g"),
    TAG("t");

    private final String abbrev;

    /**
     * Constructs a Column with the given abbreviation.
     *
     * @param abbrev the abbreviation for the column
     */
    ContactColumn(String abbrev) {
        this.abbrev = abbrev;
    }

    /**
     * Gets the abbreviation for this column.
     *
     * @return the abbreviation
     */
    public String getAbbrev() {
        return abbrev;
    }
}
