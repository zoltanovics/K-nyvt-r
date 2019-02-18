package konyvtar.Borrow.Controller;

import java.util.List;
import konyvtar.Borrow.Borrow;
import konyvtar.Borrow.BorrowCache;
import konyvtar.Borrow.Exceptions.BorrowNotValidException;
import konyvtar.Common.ValidateType;

public class BorrowController {
    private List<String> errors;
    private final BorrowValidator validator;
    private final BorrowCache cache;
   
    public BorrowController(BorrowValidator validator, BorrowCache cache){
        this.validator = validator;
        this.cache = cache;
    }
    
    public List<Borrow> getBorrows(){
        return cache.getDataList();
    }
            
            
    public void saveBorrow(Borrow borrow) throws BorrowNotValidException{
        errors = validator.validate(borrow, ValidateType.SAVE);
        if(errors.isEmpty()){
            cache.save(borrow);
        } else {
            throw new BorrowNotValidException(errors.toString());
        }
    }
    
    public void deleteBorrow(Borrow borrow) throws BorrowNotValidException  {
        errors = validator.validate(borrow,ValidateType.DELETE);
        if (errors.isEmpty()) {
            cache.delete(borrow);
        }
        else {
            throw new BorrowNotValidException(errors.toString());
        }
    }
    
}
    

