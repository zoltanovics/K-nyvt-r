package konyvtar.View;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import konyvtar.Borrow.Borrow;
import konyvtar.Borrow.Controller.BorrowController;
import konyvtar.Borrow.Controller.BorrowValidator;
import konyvtar.Borrow.Exceptions.BorrowNotValidException;
import org.apache.commons.beanutils.BeanUtils;


public class DeleteBorrowPane extends JPanel{
    private final JComboBox items = new JComboBox();
    private final JComboBox itemNames = new JComboBox();
    private final JButton delete = new JButton("Delete");
    private final BorrowController controller = new BorrowController(new BorrowValidator(), MainWindow.borrowCache);
    private final JTextField inDate = new JTextField(30);
    private final String INDATE_TEXT = "Got back";
    private final JPanel fieldsThirdHalf = new JPanel(new GridLayout(5,1));

    
    public DeleteBorrowPane(){
        setLayout(new GridLayout(6,50));
        add(fieldsThirdHalf);
        addInDateField();
        

        
        List<Borrow> res = MainWindow.borrowCache.getDataList();
        for(Borrow item : res){
            if (item.getInDate().equals("Gave out")) {
            items.addItem(item);
            itemNames.addItem(item.getStoreID());
            }
        }
        
        deleteButton();               
        add(itemNames);
        add(delete);
        setBorder(BorderFactory.createEmptyBorder(100, 10, 100, 200));
    }

    private void deleteButton() {
        delete.addActionListener(ActionEvent ->{
            Borrow obj = (Borrow) items.getItemAt(itemNames.getSelectedIndex());
            if (obj != null){
                if(MainWindow.delete()){
                    try {                        
                        controller.deleteBorrow(obj);
                        items.removeItem(obj);
                        itemNames.removeItem(obj.getStoreID());
                    } catch (BorrowNotValidException ex) {
                        MainWindow.infoBox(ex.getMessage(), "Errors");
                    }
                }
            } else {
                MainWindow.infoBox("Please select a Borrow", "Error");
            }
        });
    }
    
    private void addInDateField() {
        inDate.setText(INDATE_TEXT);
        inDate.setForeground(Color.DARK_GRAY);
        inDate.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent fe)
            {
                inDate.setForeground(Color.BLACK);
                if (inDate.getText().equals(INDATE_TEXT)){
                    inDate.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent fe)
            {
                Borrow obj = (Borrow) items.getItemAt(itemNames.getSelectedIndex());
                if (inDate.getText().equals("")){
                    inDate.setText(INDATE_TEXT);
                    inDate.setForeground(Color.DARK_GRAY);
                } else {
                    try {
                        BeanUtils.setProperty(obj, "inDate", inDate.getText());
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(DeleteBorrowPane.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(DeleteBorrowPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        fieldsThirdHalf.add(inDate);
    }
}