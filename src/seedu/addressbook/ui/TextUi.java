package seedu.addressbook.ui;

import static seedu.addressbook.common.Messages.*;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


class Formatter {
	
	/** A decorative prefix added to the beginning of lines printed by AddressBook */
    private static final String LINE_PREFIX = "|| ";

    /** A platform independent line separator. */
    private static final String LS = System.lineSeparator();

    private static final String DIVIDER = "===================================================";
    
    /** Format of indexed list item */
    private static final String MESSAGE_INDEXED_LIST_ITEM = "\t%1$d. %2$s";


    /** Offset required to convert between 1-indexing and 0-indexing.  */
    public static final int DISPLAYED_INDEX_OFFSET = 1;

	public String getUserCommand() {
		
		return LINE_PREFIX + "Enter command: ";
		
	}
	
	public String getFullUserCommand(String fullCommand){
		
		return "[Command entered:" + fullCommand + "]";
	}

	public String showToUser(String... message) {
		
		String intended = "";
		
		for(String m: message){
			intended += LINE_PREFIX + m.replace("\n", LS + LINE_PREFIX);
		}
		
		return intended;
		
	}
	
	public String showWelcomeMessage(String version,String storageFilePath) {
		String storageFileInfo = String.format(MESSAGE_USING_STORAGE_FILE, storageFilePath);
		return showToUser(DIVIDER, DIVIDER, MESSAGE_WELCOME, version, 
				MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE, storageFileInfo, DIVIDER);
	}

	public String getGoodByeMsg() {
		return showToUser(MESSAGE_GOODBYE, DIVIDER, DIVIDER);
		
	}

	public String getInitialFailMsg() {
		return showToUser(MESSAGE_INIT_FAILED, DIVIDER, DIVIDER);
	}

	public String getResultForUser(CommandResult result) {
		return showToUser(result.feedbackToUser, DIVIDER);
	}
	
	/** Formats a list of strings as a viewable indexed list. */
    public static String getIndexedListForViewing(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = 0 + DISPLAYED_INDEX_OFFSET;
        for (String listItem : listItems) {
            formatted.append(getIndexedListItem(displayIndex, listItem)).append("\n");
            displayIndex++;
        }
        return formatted.toString();
    }

    /**
     * Formats a string as a viewable indexed list item.
     *
     * @param visibleIndex visible index for this listing
     */
    private static String getIndexedListItem(int visibleIndex, String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }
	
}

/**
 * Text UI of the application.
 */
public class TextUi {


    /** Format of a comment input line. Comment lines are silently consumed when reading user input. */
    private static final String COMMENT_LINE_FORMAT_REGEX = "#.*";

    private Formatter format;
    
    private final Scanner in;
    private final PrintStream out;

    public TextUi() {
        this(System.in, System.out);
    }

    public TextUi(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
        this.format = new Formatter();
    }

    /**
     * Returns true if the user input line should be ignored.
     * Input should be ignored if it is parsed as a comment, is only whitespace, or is empty.
     *
     * @param rawInputLine full raw user input line.
     * @return true if the entire user input line should be ignored.
     */
    private boolean shouldIgnore(String rawInputLine) {
        return rawInputLine.trim().isEmpty() || isCommentLine(rawInputLine);
    }

    /**
     * Returns true if the user input line is a comment line.
     *
     * @param rawInputLine full raw user input line.
     * @return true if input line is a comment.
     */
    private boolean isCommentLine(String rawInputLine) {
        return rawInputLine.trim().matches(COMMENT_LINE_FORMAT_REGEX);
    }

    /**
     * Prompts for the command and reads the text entered by the user.
     * Ignores empty, pure whitespace, and comment lines.
     * Echos the command back to the user.
     * @return command (full line) entered by the user
     */
    public String getUserCommand() {
    	out.print(format.getUserCommand());
        String fullInputLine = in.nextLine();

        // silently consume all ignored lines
        while (shouldIgnore(fullInputLine)) {
            fullInputLine = in.nextLine();
        }
        
        out.println(format.showToUser(format.getFullUserCommand(fullInputLine)));
        return fullInputLine;
    }


    public void showWelcomeMessage(String version, String storageFilePath) {
        out.println(format.showToUser(version, storageFilePath));
    }

    public void showGoodbyeMessage() {
        out.println(format.getGoodByeMsg());
    }


    public void showInitFailedMessage() {
    	out.println(format.getInitialFailMsg());
    }

    /** Shows message(s) to the user */
//    public void showToUser(String... message) {
//        out.println(format.showToUser(message));
//    }

    /**
     * Shows the result of a command execution to the user. Includes additional formatting to demarcate different
     * command execution segments.
     */
    public void showResultToUser(CommandResult result) {
        final Optional<List<? extends ReadOnlyPerson>> resultPersons = result.getRelevantPersons();
        if (resultPersons.isPresent()) {
            showPersonListView(resultPersons.get());
        }
        out.println(format.getResultForUser(result));
    }

    /**
     * Shows a list of persons to the user, formatted as an indexed list.
     * Private contact details are hidden.
     */
    private void showPersonListView(List<? extends ReadOnlyPerson> persons) {
        final List<String> formattedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : persons) {
            formattedPersons.add(person.getAsTextHidePrivate());
        }
        showToUserAsIndexedList(formattedPersons);
    }

    /** Shows a list of strings to the user, formatted as an indexed list. */
    private void showToUserAsIndexedList(List<String> list) {
        out.println(format.showToUser(getIndexedListForViewing(list)));
    }

    public static String getIndexedListForViewing(List<String> listItems) {
    	return Formatter.getIndexedListForViewing(listItems);
    }

}
