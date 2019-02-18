package Test;

import static java.util.Arrays.asList;
import konyvtar.Borrow.Borrow;
import konyvtar.Borrow.Controller.BorrowValidator;
import konyvtar.Common.ValidateType;
import konyvtar.Common.Validator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BorrowValidatorJUnitTest {
    protected BorrowValidator validator; 
    
    @Before
    public void setUp() {
        validator = new BorrowValidator();
        
    }
    
    
    @Test
    public void testLockedBook() {
        Borrow borrow = new Borrow("1", "Zoltán", 12348, 8, "","2014");
        assertTrue(asList(Validator.BOOK_IS_LOCKED).equals(validator.validate(borrow, ValidateType.SAVE)));
    }
    
    public void rightBorrow() {
        Borrow borrow = new Borrow("1","Zoltán",12349,9,"","2013");
        assertTrue(validator.validate(borrow, ValidateType.SAVE).isEmpty());
    }
    
    public void testHasCopy() {
        Borrow borrow = new Borrow("1", "Zoltán", 12345, 1, "","2014");
        assertTrue(asList(Validator.COPY_IS_TAKEN).equals(validator.validate(borrow, ValidateType.SAVE)));
    }
    
    public void testDelete() {
        Borrow borrow = new Borrow("1", "Zoltán", 12349, 9, "","2014");
        assertTrue(asList(Validator.NO_INDATE).equals(validator.validate(borrow, ValidateType.DELETE)));
    }
    
    
    
    
    
  
}
