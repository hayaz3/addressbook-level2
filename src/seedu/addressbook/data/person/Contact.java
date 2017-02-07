package seedu.addressbook.data.person;

public class Contact {
	
	public final String value;
    private boolean isPrivate;
    
    public Contact(String value, boolean isPrivate) {
    	this.value = value.trim();
    	this.isPrivate = isPrivate;
    }
    
    public String toString() {
    	return this.value;
    }
    
    public boolean isPrivate() {
    	return this.isPrivate;
    }
    
    public int hashCode() {
    	return value.hashCode();
    }
    
    public boolean equals(Object O) {
    	return O == this // short circuit if same object
    			|| (this.getClass().isInstance(O)) // instanceof handles nulls
    			&& (this.value.equals(getClass().cast(O).value)); // state check
    }
	
}
