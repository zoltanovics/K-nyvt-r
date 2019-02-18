package konyvtar.Common;

import java.util.List;

public interface Validator<T> {
    //Member
    static final String EMPTY_NAME = "Name is required";
    static final String EMPTY_ADDRESS = "Address is required";
    static final String EMPTY_TICKETID = "Library ticket ID is required.";
    static final String TICKETID_NOT_NUMBER = "ticket ID has to be a number.";
    static final String NAME_IS_NUMBER = "Name can not contain numbers";
    static final String MEMBER_HAS_COPY = "Member cant be deleted. They own a copy";
    static final String TICKET_ID_COLLISON = "This ticket ID already exists";
    
    //Borrow
    static final String BOOK_IS_LOCKED = "The book is locked";
    static final String COPY_IS_TAKEN = "Copy is taken by someone else";
    static final String MAX_COPYPCS_REACHED = "This member cant borrow more copies";
    static final String FILL_ALL_FIELDS = "Fill the required fields";
    static final String NOT_REAL_DATA = "The data you gave is incorrect";
    static final String NO_INDATE = "Retrieve date must be given";
    
    List<String> validate(T obj, ValidateType type); 
}
