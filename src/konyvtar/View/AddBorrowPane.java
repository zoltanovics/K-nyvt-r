package konyvtar.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import konyvtar.Borrow.Borrow;
import konyvtar.Borrow.Controller.BorrowController;
import konyvtar.Borrow.Controller.BorrowValidator;
import konyvtar.Borrow.Exceptions.BorrowNotValidException;
import org.apache.commons.beanutils.BeanUtils;

public class AddBorrowPane extends JPanel{
    private final JTextField ticketID = new JTextField(50);
    private final JTextField name = new JTextField(50);
    private final JTextField bookID = new JTextField(50);
    private final JTextField storeID = new JTextField(50);
    private final JTextField inDate = new JTextField(50);
    private final JTextField outDate = new JTextField(50);
    
    private final String TICKETID_TEXT = "TICKETID";
    private final String NAME_TEXT = "Name";
    private final String BOOKID_TEXT = "BOOKID";
    private final String STOREID_TEXT = "STOREID";
    private final String OUTDATE_TEXT = "Gave out";
    private final String INDATE_TEXT = "Got back";
    
    
    private final JPanel buttons = new JPanel();
    private final JPanel fieldsFirstHalf = new JPanel(new GridLayout(3,1,0,10));
    private final JPanel fieldsSecondHalf = new JPanel(new GridLayout(4,1,0,10));
    private final JPanel fieldsThirdHalf = new JPanel(new GridLayout(4,1,0,10));
    
    private final Borrow tmp = new Borrow(TICKETID_TEXT, NAME_TEXT, 0, 0,OUTDATE_TEXT, INDATE_TEXT);
    private final BorrowController controller = new BorrowController(new BorrowValidator(), MainWindow.borrowCache);
    
    AddBorrowPane(){
        setLayout(new GridLayout(4,1));
        
        addFields();
        addButtons();
    }

    private void addFields() {
        addTicketIDField();
        addNameField();
        addBookIDField();
        addStoreIDField();
        addInDateField();
        addOutDateField();
        
        //set borders
        fieldsFirstHalf.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 20));
        fieldsSecondHalf.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        fieldsThirdHalf.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 20));
        
        //add to main panel
        add(fieldsFirstHalf,BorderLayout.PAGE_START);
        add(fieldsSecondHalf);
        add(fieldsThirdHalf);
    }

    private void addButtons() {
        saveButton();
        deleteButton();
        
        //add to main panel
        add(buttons,BorderLayout.SOUTH);
    }
    
    private void saveButton(){
        JButton save = new JButton("Save");
        save.addActionListener(ActionEvent -> {
            trySaveBorrow();
        });
        buttons.add(save, BorderLayout.WEST);
    }
    
    private void deleteButton(){
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(ActionEvent -> {
            clearFields();
        });
        buttons.add(cancel, BorderLayout.EAST);
    }

    private void clearFields() {
        ticketID.setText(TICKETID_TEXT);
        name.setText(NAME_TEXT);
        storeID.setText(STOREID_TEXT);
        bookID.setText(BOOKID_TEXT);
        inDate.setText(INDATE_TEXT);
        outDate.setText(OUTDATE_TEXT);
    }

    private void trySaveBorrow() {
        if(!FieldsAreFilled()){
            MainWindow.infoBox("All the fields are required to be filled.", "Errors");
        } else {
            try{
            controller.saveBorrow(tmp);
            }catch (BorrowNotValidException e){
                MainWindow.infoBox(e.getMessage(), "Errors");
            }
        }
    }

    private boolean FieldsAreFilled() {
        if(tmp.getBookID()==0){
            return false;
        }
        if(tmp.getStoreID()==0){
            return false;
        }
        if(tmp.getTicketID().equals(TICKETID_TEXT)){
            return false;
        }
        if(tmp.getMemberName().equals(NAME_TEXT)){
            return false;
        }
        if (tmp.getOutDate().equals(OUTDATE_TEXT)) {
            return false;
        }
        return true;
    }

    private void addTicketIDField() {
        ticketID.setText(TICKETID_TEXT);
        ticketID.setForeground(Color.DARK_GRAY);
        ticketID.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent fe)
            {
                ticketID.setForeground(Color.BLACK);
                if (ticketID.getText().equals(TICKETID_TEXT)){
                    ticketID.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent fe)
            {
                if (ticketID.getText().equals("")){
                    ticketID.setText(TICKETID_TEXT);
                    ticketID.setForeground(Color.DARK_GRAY);
                }  else {
                    try {
                        BeanUtils.setProperty(tmp, "ticketID", ticketID.getText());
                    } catch (IllegalAccessException | InvocationTargetException ex) {
                        Logger.getLogger(AddBorrowPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        fieldsFirstHalf.add(ticketID);
    }

    private void addNameField() {
        name.setText(NAME_TEXT);
        name.setForeground(Color.DARK_GRAY);
        name.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent fe)
            {
                name.setForeground(Color.BLACK);
                if (name.getText().equals(NAME_TEXT)){
                    name.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent fe)
            {
                if (name.getText().equals("")){
                    name.setText(NAME_TEXT);
                    name.setForeground(Color.DARK_GRAY);
                } else {
                    try {
                        BeanUtils.setProperty(tmp, "memberName", name.getText());
                    } catch (IllegalAccessException | InvocationTargetException ex) {
                        Logger.getLogger(AddBorrowPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        fieldsFirstHalf.add(name);
    }

    private void addBookIDField() {
        bookID.setText(BOOKID_TEXT);
        bookID.setForeground(Color.DARK_GRAY);
        bookID.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent fe)
            {
                bookID.setForeground(Color.BLACK);
                if (bookID.getText().equals(BOOKID_TEXT)){
                    bookID.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent fe)
            {
                if (bookID.getText().equals("")){
                    bookID.setText(BOOKID_TEXT);
                    bookID.setForeground(Color.DARK_GRAY);
                } else {
                    try {
                        BeanUtils.setProperty(tmp, "bookID", bookID.getText());
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(AddBorrowPane.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(AddBorrowPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        fieldsFirstHalf.add(bookID);
    }

    

    private void addStoreIDField() {
        storeID.setText(STOREID_TEXT);
        storeID.setForeground(Color.DARK_GRAY);
        storeID.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent fe)
            {
                storeID.setForeground(Color.BLACK);
                if (storeID.getText().equals(STOREID_TEXT)){
                    storeID.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent fe)
            {
                if (storeID.getText().equals("")){
                    storeID.setText(STOREID_TEXT);
                    storeID.setForeground(Color.DARK_GRAY);
                } else {
                    try {
                        BeanUtils.setProperty(tmp, "storeID", storeID.getText());
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(AddBorrowPane.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(AddBorrowPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        fieldsSecondHalf.add(storeID);
    }

   

    private void addOutDateField() {
        outDate.setText(OUTDATE_TEXT);
        outDate.setForeground(Color.DARK_GRAY);
        outDate.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent fe)
            {
                outDate.setForeground(Color.BLACK);
                if (outDate.getText().equals(OUTDATE_TEXT)){
                    outDate.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent fe)
            {
                if (outDate.getText().equals("")){
                    outDate.setText(OUTDATE_TEXT);
                    outDate.setForeground(Color.DARK_GRAY);
                } else {
                    try {
                        BeanUtils.setProperty(tmp, "outDate", outDate.getText());
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(AddBorrowPane.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(AddBorrowPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        fieldsThirdHalf.add(outDate);
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
                if (inDate.getText().equals("")){
                    inDate.setText(INDATE_TEXT);
                    inDate.setForeground(Color.DARK_GRAY);
                } else {
                    try {
                        BeanUtils.setProperty(tmp, "inDate", inDate.getText());
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(AddBorrowPane.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(AddBorrowPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        fieldsThirdHalf.add(inDate);
    }

   
}
