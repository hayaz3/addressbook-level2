package seedu.addressbook.data.tag;
import seedu.addressbook.data.person.Person;

public class Tagging {
	private Person person;
	private Tag tag;
	private enum AddOrDelete{ADD,DELETE};
	private AddOrDelete action;
	
	private final String ADD = "ADD";
	
	public Tagging(Person person, Tag tag, String status) {
		this.person = person;
		this.tag = tag;
		if(status.equals(ADD)) {
			this.action = AddOrDelete.ADD;
		}
		else
			this.action = AddOrDelete.DELETE;
		
	}
	
	public String toString() {
		if(this.action.equals(ADD))
			return "+ " + this.person.getName().toString() + " " + this.tag.tagName;
		else 
			return "- " + this.person.getName().toString() + " " + this.tag.tagName;
	}
}
