package seedu.address.testutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoDeadline;
import seedu.address.model.todo.TodoLocation;
import seedu.address.model.todo.TodoName;
import seedu.address.model.todo.TodoStatus;

/**
 * A utility class to help with building Todo objects.
 */
public class TodoBuilder {

    public static final String DEFAULT_NAME = "Todo stuff";
    public static final String DEFAULT_DEADLINE = "25-12-31 23:59";
    public static final String DEFAULT_LOCATION = "EARTH";
    public static final boolean DEFAULT_STATUS = false;
    public static final List<Person> DEFAULT_PERSONS = List.of();
    public static final Set<Tag> DEFAULT_TAGS = new HashSet<>();

    private TodoName name;
    private TodoDeadline deadline;
    private TodoLocation location;
    private TodoStatus status;
    private List<Person> persons;
    private Set<Tag> tags;


    /**
     * Creates a {@code TodoBuilder} with the default details.
     */
    public TodoBuilder() {
        name = new TodoName(DEFAULT_NAME);
        deadline = new TodoDeadline(DEFAULT_DEADLINE);
        location = new TodoLocation(DEFAULT_LOCATION);
        status = new TodoStatus(DEFAULT_STATUS);
        persons = List.copyOf(DEFAULT_PERSONS);
        tags = Set.copyOf(DEFAULT_TAGS);
    }

    /**
     * Initializes the TodoBuilder with the data of {@code todoToCopy}.
     */
    public TodoBuilder(Todo todoToCopy) {
        name = todoToCopy.getName();
        deadline = todoToCopy.getDeadline();
        location = todoToCopy.getLocation();
        status = todoToCopy.getStatus();
        persons = List.copyOf(todoToCopy.getPersons());
        tags = Set.copyOf(todoToCopy.getTags());
    }

    /**
     * Sets the {@code TodoName} of the {@code Todo} that we are building.
     */
    public TodoBuilder withName(String name) {
        this.name = new TodoName(name);
        return this;
    }

    /**
     * Sets the {@code TodoLocation} of the {@code Todo} that we are building.
     */
    public TodoBuilder withLocation(String location) {
        this.location = new TodoLocation(location);
        return this;
    }

    /**
     * Sets the {@code TodoDeadline} of the {@code Todo} that we are building.
     */
    public TodoBuilder withDeadline(String deadline) {
        this.deadline = new TodoDeadline(deadline);
        return this;
    }

    /**
     * Sets the {@code TodoStatus} of the {@code Todo} that we are building.
     */
    public TodoBuilder withStatus(boolean status) {
        this.status = new TodoStatus(status);
        return this;
    }

    /**
     * Sets the {@code List<Person>} of the {@code Todo} that we are building.
     */
    public TodoBuilder withPersons(List<Person> persons) {
        this.persons = List.copyOf(persons);
        return this;
    }

    public Todo build() {
        return new Todo(name, deadline, location, status, persons, tags);
    }

}
