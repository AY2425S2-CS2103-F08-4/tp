package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Contact's module in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidModule(String)}
 */
public class Course {

    public static final String MESSAGE_CONSTRAINTS = "modules should only contain alphanumeric "
                                                     + "characters and spaces, and it should not "
                                                     + "be " + "blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullModule;

    /**
     * Constructs a {@code module}.
     *
     * @param module A valid module.
     */
    public Course(String module) {
        requireNonNull(module);
        checkArgument(isValidModule(module), MESSAGE_CONSTRAINTS);
        fullModule = module;
    }

    /**
     * Returns true if a given string is a valid module.
     */
    public static boolean isValidModule(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return fullModule.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Course)) {
            return false;
        }

        Course otherCourse = (Course) other;
        return fullModule.equals(otherCourse.fullModule);
    }

    @Override
    public String toString() {
        return fullModule;
    }

}
