package konyvtar.Book;

import java.util.List;

public interface BookCache{
    List<Book> getDataList();
    void refreshData();
    void lock(Book book);
    void unlock(Book book);
}
