package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;
import seedu.address.model.contact.Course;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Group;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Tag;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDateTime;
import seedu.address.model.event.EventLocation;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.EventName;
import seedu.address.model.item.ItemManager;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoDeadline;
import seedu.address.model.todo.TodoLocation;
import seedu.address.model.todo.TodoManager;
import seedu.address.model.todo.TodoName;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Contact[] getSampleContacts() {
        return new Contact[] {
            new Contact(new Id("A0123456X"), new Name("Alex Yeoh"),
                new Email("alexyeoh@example.com"),
                new Course("CS1010S"), new Group("T01"),
                getTagSet("friends")),
            new Contact(new Id("A1234567Y"), new Name("Bernice Yu"),
                new Email("berniceyu@example.com"),
                new Course("CS1010S"), new Group("R01"),
                getTagSet("colleagues", "friends")),
            new Contact(new Id("A2345678Z"), new Name("Charlotte Oliveiro"),
                new Email("charlotte@example.com"),
                new Course("CS2103"), new Group("F08"),
                getTagSet("neighbours")),
            new Contact(new Id("A3456789G"), new Name("David Li"),
                new Email("lidavid@example.com"),
                new Course("CS2030"), new Group("F10"),
                getTagSet("family")),
            new Contact(new Id("A4567890H"), new Name("Irfan Ibrahim"),
                new Email("irfan@example.com"),
                new Course("CS2040"), new Group("T10"),
                getTagSet("classmates")),
            new Contact(new Id("A5678901K"), new Name("Roy Balakrishnan"),
                new Email("royb@example.com"),
                new Course("CS1010S"), new Group("T28"),
                getTagSet("colleagues"))
        };
    }

    public static Todo[] getSampleTodos() {
        return new Todo[] {
            new Todo(new TodoName("Grading"), new TodoDeadline("25-03-23 17:00"),
                new TodoLocation("NUS Science")),
            new Todo(new TodoName("Lab 1"), new TodoDeadline("27-03-23 17:00"),
                new TodoLocation("NUS Com1")),
            new Todo(new TodoName("Project Meeting"), new TodoDeadline("28-03-23 19:00"),
                new TodoLocation("NUS Engineering")),
            new Todo(new TodoName("CS2103 Lecture"), new TodoDeadline("29-03-23 14:00"),
                new TodoLocation("NUS Business")),
            new Todo(new TodoName("CS2030 Tutorial"), new TodoDeadline("30-03-23 10:00"),
                new TodoLocation("NUS SoC")),
            new Todo(new TodoName("CS2040 Lab"), new TodoDeadline("31-03-23 17:00"),
                new TodoLocation("NUS Com2")),
            new Todo(new TodoName("CS1010S Project"), new TodoDeadline("01-04-23 23:59"),
                new TodoLocation("NUS Computing"))
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new EventName("Hackathon"), new EventDateTime("25-03-23 09:00"),
                new EventDateTime("25-03-23 17:00"), new EventLocation("NUS UTown")),
            new Event(new EventName("Workshop"), new EventDateTime("27-03-23 13:00"),
                new EventDateTime("27-03-23 17:00"), new EventLocation("NUS Com2")),
            new Event(new EventName("Seminar"), new EventDateTime("28-03-23 15:00"),
                new EventDateTime("28-03-23 17:00"), new EventLocation("NUS LT27")),
            new Event(new EventName("Meeting"), new EventDateTime("29-03-23 11:00"),
                new EventDateTime("29-03-23 13:00"), new EventLocation("NUS AS6")),
            new Event(new EventName("Conference"), new EventDateTime("30-03-23 09:00"),
                new EventDateTime("30-03-23 17:00"), new EventLocation("NUS LT19"))
        };
    }

    public static ItemManager<Contact> getSampleAddressBook() {
        ContactManager sampleAb = new ContactManager();
        for (Contact sampleContact : getSampleContacts()) {
            sampleAb.addItem(sampleContact);
        }
        return sampleAb;
    }

    public static ItemManager<Todo> getSampleTodoList() {
        TodoManager sampleTd = new TodoManager();
        for (Todo sampleTodo : getSampleTodos()) {
            sampleTd.addItem(sampleTodo);
        }
        return sampleTd;
    }

    public static ItemManager<Event> getSampleEventList() {
        EventManager sampleEv = new EventManager();
        for (Event sampleEvent : getSampleEvents()) {
            sampleEv.addItem(sampleEvent);
        }
        return sampleEv;
    }


    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

}
