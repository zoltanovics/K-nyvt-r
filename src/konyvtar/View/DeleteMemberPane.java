package konyvtar.View;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import konyvtar.Member.Controller.MemberController;
import konyvtar.Member.Controller.MemberValidator;
import konyvtar.Member.Exceptions.MemberNotValidException;
import konyvtar.Member.Member;


public class DeleteMemberPane extends JPanel{
    private final JComboBox items = new JComboBox();
    private final JComboBox itemNames = new JComboBox();
    private final JButton delete = new JButton("Delete");
    private final MemberController controller = new MemberController(new MemberValidator(), MainWindow.memberCache);
    
    public DeleteMemberPane(){
        setLayout(new GridLayout(2,1,0,400));
        
        List<Member> res = MainWindow.memberCache.getDataList();
        for(Member item : res){
            items.addItem(item);
            itemNames.addItem(item.getName());
        }
        
        deleteButton();               
        add(itemNames);
        add(delete);
        setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 200));
    }

    private void deleteButton() {
        delete.addActionListener(ActionEvent ->{
            Member obj = (Member) items.getItemAt(itemNames.getSelectedIndex());
            if (obj != null){
                if(MainWindow.delete()){
                    try {
                        controller.deleteMember(obj);
                        items.removeItem(obj);
                        itemNames.removeItem(obj.getName());
                    } catch (MemberNotValidException ex) {
                        MainWindow.infoBox(ex.getMessage(), "Error");
                    }
                }
            } else {
                MainWindow.infoBox("Please select a Member", "Error");
            }
        });
    }
}