package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {
	
	class Block {
		
		private int block;
		
		public Block(int block) {
			this.block = block;
		}

		public int getBlock() {
			return this.block;
		}

		public void setBlock(int block) {
			this.block = block;
		}
		
	}
	
	class Street {
		
		private String street;
		
		public Street(String street) {
			this.street = street;
		}

		public String getStreet() {
			return this.street;
		}

		public void setStreet(String street) {
			this.street = street;
		}
	}
	
	class Unit {
		
		private String unit;
		
		public Unit(String unit) {
			this.unit = unit;
		}

		public String getUnit() {
			return this.unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}
	}
	
	class PostalCode {
		
		private int postal;
		
		public PostalCode(int postal){
			this.postal = postal;
		}

		public int getPostal() {
			return this.postal;
		}

		public void setPostal(int postal) {
			this.postal = postal;
		}
	}

    public static final String EXAMPLE = "123, some street, #01-01, 123987";
    public static final String MESSAGE_ADDRESS_CONSTRAINTS = "Person addresses should be in this format: a/block, street, unit, postalcode";
    public static final String ADDRESS_VALIDATION_REGEX = ".+";

    private static final int BLOCK_INDEX = 0;
    private static final int STREET_INDEX = 1;
    private static final int UNIT_INDEX = 2;
    private static final int POSTAL_CODE_INDEX = 3;
    
    private Block block;
    private Street street;
    private Unit unit;
    private PostalCode postalCode;
    
    private boolean isPrivate;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Address(String address, boolean isPrivate) throws IllegalValueException {
    	String trimmedAddress = removePrefixOfAddress(address).trim();
        this.isPrivate = isPrivate;
        if (!isValidAddress(trimmedAddress)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        
        String[] splitAddress = address.split(", ");
        this.block = new Block(Integer.parseInt(splitAddress[BLOCK_INDEX]));
        this.street = new Street(splitAddress[STREET_INDEX]);
        this.unit = new Unit(splitAddress[UNIT_INDEX]);
        this.postalCode = new PostalCode(Integer.parseInt(splitAddress[POSTAL_CODE_INDEX]));
    }

    private String removePrefixOfAddress(String address) {
		if(address.startsWith("a/")) return address.replaceFirst("a/","");
		return address;
	}

	/**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.block.getBlock() + " " + this.street.getStreet() + " " +
    this.unit.getUnit() + " " + this.postalCode.getPostal();
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) // short circuit if same object
        	return true;
        else if(other instanceof Address) { // instanceof handles nulls
        	Address address2 = (Address) other;
        	return this.block.getBlock() == address2.block.getBlock() && 
        			this.street.getStreet().compareTo(address2.street.getStreet()) == 0 &&
        			this.unit.getUnit().compareTo(address2.unit.getUnit()) == 0 &&
        			this.postalCode.getPostal() == address2.postalCode.getPostal();
        }
        else
        	return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}


