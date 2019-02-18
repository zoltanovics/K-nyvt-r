package konyvtar.Borrow.Controller;

import java.util.ArrayList;
import java.util.List;
import konyvtar.Book.Book;
import konyvtar.Book.BookCacheImpl;
import konyvtar.Borrow.Borrow;
import konyvtar.Common.ValidateType;
import konyvtar.Common.Validator;
import konyvtar.Copy.Copy;
import konyvtar.Copy.CopyCacheImpl;
import konyvtar.Member.Member;
import konyvtar.Member.MemberCacheImpl;

public class BorrowValidator implements Validator<Borrow> {

    @Override
    public List<String> validate(Borrow obj, ValidateType type) {
        List<String> errors = new ArrayList<>();
        if (type.equals(ValidateType.DELETE)) {
            ValidateInDateExist(obj,errors);
        }
        else {
            ValidateExist(obj,errors);
            ValidateReal(obj,errors);
            ValidateLock(obj, errors);
            ValidateBorrowed(obj, errors);
            ValidateMaxCopy(obj,errors);
        }
        return errors;
    }
    
    private void ValidateInDateExist(Borrow obj,List<String> errors) {
        if(obj.getInDate().equals("Got back") || obj.getInDate().equals("Gave out")) {
            errors.add(Validator.NO_INDATE);
        }
    }
    
    private void ValidateExist(Borrow obj,List<String> errors) {
        BookCacheImpl book = new BookCacheImpl();
        MemberCacheImpl member = new MemberCacheImpl();
        CopyCacheImpl copy = new CopyCacheImpl();
        if(book.getBookByID(obj.getBookID()) == null || member.getMemberById(obj.getTicketID())==null ||  copy.getCopyByID(obj.getStoreID())==null) {
            errors.add(Validator.NOT_REAL_DATA);
        }
    }
    
    private void ValidateReal(Borrow obj,List<String> errors) {
        BookCacheImpl book = new BookCacheImpl();
        MemberCacheImpl member = new MemberCacheImpl();
        CopyCacheImpl copy = new CopyCacheImpl();
        if(!obj.getMemberName().equals(member.getMemberById(obj.getTicketID()).getName()) ||
            !book.getBookById(obj.getBookID()).getCopies().contains(copy.getCopyByID(obj.getStoreID()))) {
            errors.add(Validator.NOT_REAL_DATA);
        }
    }
    
    private void ValidateLock(Borrow obj, List<String> errors) {
        BookCacheImpl book = new BookCacheImpl();
        Book books = book.getBookByID(obj.getBookID());
        if (books.isLock()==true) {
            errors.add(Validator.BOOK_IS_LOCKED);
        }
    }
    
    private void ValidateBorrowed(Borrow obj, List<String> errors){
        CopyCacheImpl copy  = new CopyCacheImpl();
        Copy copies = copy.getCopyByID(obj.getStoreID());
        if (copies.isBorrowed() == true) {
            errors.add(Validator.COPY_IS_TAKEN);
        }
    }
    
    private void ValidateMaxCopy(Borrow obj, List<String> errors){
        MemberCacheImpl memberCache = new MemberCacheImpl();
        Member member = memberCache.getMemberById(obj.getTicketID());
        if (member.getBorrowpcs()==6) {
            errors.add(Validator.MAX_COPYPCS_REACHED);
        }
    }
    
    
}
