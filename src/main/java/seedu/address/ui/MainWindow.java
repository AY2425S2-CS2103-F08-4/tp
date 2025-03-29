package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.read.ListContactCommand;
import seedu.address.logic.commands.read.ListEventCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.read.ListTodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private enum ViewMode {
        CONTACT, EVENT, TODO
    }

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ListPanel listPanel;
    private ViewMode currentViewMode = ViewMode.CONTACT;
    private ResultDisplay resultDisplay;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private Button contactButton;

    @FXML
    private Button eventButton;

    @FXML
    private Button todoButton;

    @FXML
    private Button resetButton;


    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        listPanel = new ListPanel();
        listPanelPlaceholder.getChildren().add(listPanel.getRoot());

        listPanel.setContactList(logic.getFilteredContactList());
        updateButtonStyles();

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }

    /**
     * Handles switching to contact view
     */
    @FXML
    private void handleContactButton() {
        currentViewMode = ViewMode.CONTACT;
        listPanel.setContactList(logic.getFilteredContactList());
        updateButtonStyles();
    }

    /**
     * Handles switching to event view
     */
    @FXML
    private void handleEventButton() {
        currentViewMode = ViewMode.EVENT;
        listPanel.setEventList(logic.getFilteredEventList());
        updateButtonStyles();
    }

    /**
     * Handles switching to todo view
     */
    @FXML
    private void handleTodoButton() {
        currentViewMode = ViewMode.TODO;
        listPanel.setTodoList(logic.getFilteredTodoList());
        updateButtonStyles();
    }

    /**
     * Handles resetting filter (shows all items in the current view)
     */
    @FXML
    private void handleResetButton() {
        logger.info("Reset button clicked, showing all items for current view");

        try {
            switch (currentViewMode) {
            case CONTACT:
                executeCommand(String.format("%s %s", CONTACT_COMMAND_WORD, ListContactCommand.COMMAND_WORD));
                break;
            case EVENT:
                executeCommand(String.format("%s %s", EVENT_COMMAND_WORD, ListEventCommand.COMMAND_WORD));
                break;
            case TODO:
                executeCommand(String.format("%s %s", TODO_COMMAND_WORD, ListTodoCommand.COMMAND_WORD));
                break;
            default:
                return;
            }

            logger.info("Reset successful");

        } catch (CommandException | ParseException e) {
            logger.info("Reset operation failed: " + e.getMessage());
            resultDisplay.setFeedbackToUser(e.getMessage());
        }
    }

    /**
     * Update the styling of buttons based on current view
     */
    private void updateButtonStyles() {
        // Remove active class from all buttons
        contactButton.getStyleClass().remove("active-button");
        eventButton.getStyleClass().remove("active-button");
        todoButton.getStyleClass().remove("active-button");

        // Add active class to currently selected button
        switch (currentViewMode) {
        case CONTACT:
            contactButton.getStyleClass().add("active-button");
            break;
        case EVENT:
            eventButton.getStyleClass().add("active-button");
            break;
        case TODO:
            todoButton.getStyleClass().add("active-button");
            break;
        default:
            break;
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isExit()) {
                handleExit();
            }

            refreshCurrentView();

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Refreshes the current view to reflect any changes in data
     */
    private void refreshCurrentView() {
        switch (currentViewMode) {
        case CONTACT:
            listPanel.setContactList(logic.getFilteredContactList());
            break;
        case EVENT:
            listPanel.setEventList(logic.getFilteredEventList());
            break;
        case TODO:
            listPanel.setTodoList(logic.getFilteredTodoList());
            break;
        default:
            break;
        }
    }
}
