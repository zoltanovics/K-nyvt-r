package Test;

import java.util.ArrayList;
import static java.util.Arrays.asList;
import junit.framework.TestCase;
import konyvtar.Common.ValidateType;
import konyvtar.Common.Validator;
import konyvtar.Member.Controller.MemberController;
import konyvtar.Member.Controller.MemberValidator;
import konyvtar.Member.Exceptions.MemberNotValidException;
import konyvtar.Member.Member;
import konyvtar.Member.MemberCache;
import org.easymock.*;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class MemberControllerJUnitTest extends TestCase {
    protected MemberController controller;
    protected MemberValidator validator;
    protected MemberCache cache;
    
    
    @Before
    @Override
    public void setUp() {
        cache = EasyMock.createNiceMock(MemberCache.class);
        validator = EasyMock.createNiceMock(MemberValidator.class);
        controller = new MemberController(validator, cache);
    }
    
    @Test
    public void testAddingMemberRightData(){
        try{
            Member member = new Member("Dayka13", false, "Pali", "13", 0);
            EasyMock.expect(validator.validate(member, ValidateType.SAVE)).andReturn(new ArrayList<>());
            EasyMock.replay(validator);
            controller.saveMember(member);
        } catch(Exception e){
            assertTrue(false);
            System.out.println(e.toString());
        }
    }
    
    @Test(expected = MemberNotValidException.class)
    public void testAddingMemberWrongData(){
        try{
            Member member = new Member("Dayka13", false,"Pali13","14" ,0);
            EasyMock.expect(validator.validate(member, ValidateType.SAVE)).andReturn(asList(Validator.NAME_IS_NUMBER));
            EasyMock.replay(validator);
            controller.saveMember(member);
            assertTrue(false);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
