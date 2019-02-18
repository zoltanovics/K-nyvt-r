package konyvtar.View;

import java.awt.BorderLayout;
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
import konyvtar.Member.Controller.MemberController;
import konyvtar.Member.Controller.MemberValidator;
import konyvtar.Member.Member;
import org.apache.commons.beanutils.BeanUtils;
import konyvtar.Member.MemberCacheImpl;

public class ModifyMemberPane extends JPanel{
    private final JTextField address = new JTextField(50);
    private final JTextField name = new JTextField(50);
    
    private final String ADDRESS_TEXT = "Address";
    private final String NAME_TEXT = "Name";
    private final JComboBox items = new JComboBox();
    private final JComboBox itemNames = new JComboBox();
    JButton save = new JButton("Save");
    
    private final JPanel buttons = new JPanel();
    private final JPanel fieldsFirstHalf = new JPanel(new GridLayout(3,1,0,10));
    MemberCacheImpl member = new MemberCacheImpl();
    private final Member tmp = new Member(ADDRESS_TEXT,false,NAME_TEXT,String.valueOf(member.getLatestID()),0);
    private final MemberController controller = new MemberController(new MemberValidator(), MainWindow.memberCache);
    
    ModifyMemberPane(){
        MainWindow.refreshCache();
        setLayout(new GridLayout(4,1));
        List<Member> res = MainWindow.memberCache.getDataList();
        for(Member item : res){
            items.addItem(item);
            itemNames.addItem(item.getName());
        }
                       
        add(itemNames);
        
        addFields();
        addButtons();
                buttons.add(save, BorderLayout.WEST);

        saveButton();
    }

    
    
    private void addFields() {
        addAddressField();
        addNameField();
        
        //set borders
        fieldsFirstHalf.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 20));
        
        //add to main panel
        add(fieldsFirstHalf,BorderLayout.PAGE_START);
    }

    private void addButtons() {
        deleteButton();
        
        //add to main panel
        add(buttons,BorderLayout.SOUTH);
    }
    
    private void saveButton() {
        save.addActionListener(ActionEvent ->{
            Member obj = (Member) items.getItemAt(itemNames.getSelectedIndex());
            if (obj != null) {
                tmp.setBorrowed(obj.isBorrowed());
                tmp.setTicketID(obj.getTicketID());
                tmp.setBorrowpcs(obj.getBorrowpcs());
                
                
                controller.modifyMember(tmp);    
            } else {
                MainWindow.infoBox("Please select a Member", "Error");
            }
        });
    }
    
    private void deleteButton(){
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(ActionEvent -> {
            clearFields();
        });
        buttons.add(cancel, BorderLayout.EAST);
    }


    private void clearFields() {
        address.setText(ADDRESS_TEXT);
        name.setText(NAME_TEXT);
    }


    private void addAddressField() {
        address.setText(ADDRESS_TEXT);
        address.setForeground(Color.DARK_GRAY);
        address.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent fe)
            {
                address.setForeground(Color.BLACK);
                if (address.getText().equals(ADDRESS_TEXT)){
                    address.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent fe)
            {
                if (address.getText().equals("")){
                    address.setText(ADDRESS_TEXT);
                    address.setForeground(Color.DARK_GRAY);
                }  else {
                    try {
                        BeanUtils.setProperty(tmp, "address", address.getText());
                    } catch (IllegalAccessException | InvocationTargetException ex) {
                        Logger.getLogger(AddMemberPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        fieldsFirstHalf.add(address);
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
                        BeanUtils.setProperty(tmp, "name", name.getText());
                    } catch (IllegalAccessException | InvocationTargetException ex) {
                        Logger.getLogger(AddMemberPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        fieldsFirstHalf.add(name);
    }

}
