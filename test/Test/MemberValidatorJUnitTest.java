package Test;

import static java.util.Arrays.asList;
import konyvtar.Common.ValidateType;
import konyvtar.Common.Validator;
import konyvtar.Member.Controller.MemberValidator;
import konyvtar.Member.Member;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MemberValidatorJUnitTest {
    protected MemberValidator validator; 
    
    @Before
    public void setUp() {
        validator = new MemberValidator();
    }
    
    
    @Test
    public void testAddingMemberRightData(){
        Member member = new Member("Dayka13", false, "Pali", "13", 0);
        assertTrue(validator.validate(member, ValidateType.SAVE).isEmpty());
    }
    
    
    
    @Test
    public void testAddingMemberEmptyName(){
        Member member = new Member("Dayka13", false, "", "13", 0);
        assertTrue(asList(Validator.EMPTY_NAME).equals(validator.validate(member, ValidateType.SAVE)));
    }
    
    @Test
    public void testAddingAddressEmpty(){
        Member member = new Member("", false, "Pali", "13", 0);
        assertTrue(asList(Validator.EMPTY_ADDRESS).equals(validator.validate(member, ValidateType.SAVE)));
    }
    
    @Test
    public void testDelete() {
        Member member = new Member("dAYKA13", true, "Pali", "13", 0);
        assertTrue(asList(Validator.MEMBER_HAS_COPY).equals(validator.validate(member, ValidateType.DELETE)));
    }
    
  
}
