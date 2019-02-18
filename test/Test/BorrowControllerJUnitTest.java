package Test;

import java.util.ArrayList;
import static java.util.Arrays.asList;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertTrue;
import konyvtar.Borrow.Borrow;
import konyvtar.Borrow.BorrowCache;
import konyvtar.Borrow.Controller.BorrowController;
import konyvtar.Borrow.Controller.BorrowValidator;
import konyvtar.Common.ValidateType;
import konyvtar.Common.Validator;
import konyvtar.Member.Exceptions.MemberNotValidException;
import org.easymock.*;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class BorrowControllerJUnitTest extends TestCase {
    protected BorrowController controller;
    protected BorrowValidator validator;
    protected BorrowCache cache;
    
    
    @Before
    @Override
    public void setUp() {
        cache = EasyMock.createNiceMock(BorrowCache.class);
        validator = EasyMock.createNiceMock(BorrowValidator.class);
        controller = new BorrowController(validator, cache);
    }
    
    @Test
    public void testAddingBorrowRightData(){
        try{
            Borrow borrow = new Borrow("1","Zoltán",12349,9,"","2013");            
            EasyMock.expect(validator.validate(borrow, ValidateType.SAVE)).andReturn(new ArrayList<>());
            EasyMock.replay(validator);
            controller.saveBorrow(borrow);
        } catch(Exception e){
            assertTrue(false);
            System.out.println(e.toString());
        }
    }
    
    @Test(expected = MemberNotValidException.class)
    public void testAddingBorrowWrongData(){
        try{
            Borrow borrow = new Borrow("2","Zoltán",12349,9,"","2013");
            EasyMock.expect(validator.validate(borrow, ValidateType.SAVE)).andReturn(asList(Validator.NOT_REAL_DATA));
            EasyMock.replay(validator);
            controller.saveBorrow(borrow);
            assertTrue(false);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
