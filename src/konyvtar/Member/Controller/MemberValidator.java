package konyvtar.Member.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import konyvtar.Common.ValidateType;
import konyvtar.Common.Validator;
import konyvtar.Member.Member;

public class MemberValidator implements Validator<Member> {

    @Override
    public List<String> validate(Member obj, ValidateType type) {
        List<String> errors = new ArrayList<>();
        
        ValidateAddress(obj.getAddress(), errors);
        ValidateName(obj.getName(), errors);
        if (type.equals(ValidateType.DELETE)) {
            ValidateDeletable(obj,errors);
        }
        return errors;
    }
    
    private void ValidateDeletable(Member obj, List<String> errors) {
        if (obj.isBorrowed() == true) {
            errors.add(Validator.MEMBER_HAS_COPY);
        }
    }
    
    private void ValidateAddress(String address, List<String> errors){
        if("".equals(address)){
            errors.add(Validator.EMPTY_ADDRESS);
        }
        
    }
    
    private void ValidateName(String name, List<String> errors){
        if("".equals(name)){
            errors.add(Validator.EMPTY_NAME);
        }
        Pattern p = Pattern.compile("([0-9])");
        Matcher m = p.matcher(name);
        if(m.find()){
            errors.add(Validator.NAME_IS_NUMBER);
        }
    }
    
}
