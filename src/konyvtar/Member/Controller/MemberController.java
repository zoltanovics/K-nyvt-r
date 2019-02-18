package konyvtar.Member.Controller;

import java.util.List;
import konyvtar.Common.ValidateType;
import konyvtar.Member.Exceptions.MemberNotValidException;
import konyvtar.Member.Member;
import konyvtar.Member.MemberCache;

public class MemberController {
    private List<String> errors;
    private final MemberValidator validator;
    private final MemberCache cache;
   
    public MemberController(MemberValidator validator, MemberCache cache){
        this.validator = validator;
        this.cache = cache;
    }
    
    public List<Member> getMembers(){
        return cache.getDataList();
    }
            
            
    public void saveMember(Member member) throws MemberNotValidException{
        errors = validator.validate(member, ValidateType.SAVE);
        if(errors.isEmpty()){
            cache.save(member);
        } else {
            throw new MemberNotValidException(errors.toString());
        }
    }
    
     public void deleteMember(Member tmp) throws MemberNotValidException{
        errors = validator.validate(tmp, ValidateType.DELETE);
        if(errors.isEmpty()){
            cache.delete(tmp);
        } else {
            throw new MemberNotValidException(errors.toString());
        }
    }
     
    public void modifyMember(Member member) {
        cache.modify(member);
    }
}
    

