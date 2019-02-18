package konyvtar.Member;

import java.util.List;
import konyvtar.Member.Member;

public interface MemberCache{
    List<Member> getDataList();
    void save(Member obj);
    void refreshData();
    void delete(Member obj);
    void modify(Member obj);
}
