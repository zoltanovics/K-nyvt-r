package konyvtar.Borrow;

import java.util.List;

public interface BorrowCache{
    List<Borrow> getDataList();
    void save(Borrow obj);
    void refreshData();
    void delete(Borrow obj);
}
