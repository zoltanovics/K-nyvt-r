package konyvtar.View;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import konyvtar.Book.Book;
import konyvtar.Borrow.Borrow;
import konyvtar.Copy.Copy;
import konyvtar.Member.Member;

public class MainScreen extends JPanel{
    private final JPanel memberArea = new JPanel(new GridLayout(2,2));
    private final JPanel bookArea = new JPanel(new GridLayout(2,2));
    private final JPanel borrowArea = new JPanel(new GridLayout(2,2));
    private final JPanel copyArea = new JPanel(new GridLayout(2,2));
    
    private final JButton memberButton = new JButton("Members");
    private final JButton bookButton = new JButton("Books");
    private final JButton borrowButton = new JButton("Borrows");
    private final JButton copyButton = new JButton("Copies");
   
    private JFrame dataTable = new JFrame();
    private JScrollPane scrollPane = new JScrollPane();
    
    public MainScreen(){
        setLayout(new GridLayout(4,1,0,10));
        
        MainWindow.memberCache.refreshData();
        List<Member> members = MainWindow.memberCache.getDataList();
        addMembers(members);
        
        MainWindow.bookCache.refreshData();
        List<Book> books = MainWindow.bookCache.getDataList();      
        addBooks(books);
        
        MainWindow.borrowCache.refreshData();
        List<Borrow> borrows = MainWindow.borrowCache.getDataList();
        addBorrows(borrows);
        
        MainWindow.copyCache.refreshData();
        List<Copy> copies = MainWindow.copyCache.getDataList();
        addCopies(copies);
    }

    private void addCopies(List<Copy> copies) {
        JPanel copyButtonArea = new JPanel(new GridBagLayout());
        copyButton.addActionListener(ActionEvent ->{
           showCopies(copies); 
        });
        GridBagConstraints cons = new GridBagConstraints();
        cons.anchor = GridBagConstraints.FIRST_LINE_END;
        cons.insets = new Insets(0,0,0,120);
        copyButtonArea.add(copyButton,cons);
        copyArea.add(copyButtonArea);
        add(copyArea);
    }
    
    private void addMembers(List<Member> members) {
        JPanel memberButtonArea = new JPanel(new GridBagLayout());
        memberButton.addActionListener(ActionEvent ->{
           showMembers(members); 
        });
        GridBagConstraints cons = new GridBagConstraints();
        cons.anchor = GridBagConstraints.FIRST_LINE_END;
        cons.insets = new Insets(0,0,0,120);
        memberButtonArea.add(memberButton,cons);
        memberArea.add(memberButtonArea);
        add(memberArea);
    }
    
    private void addBorrows(List<Borrow> borrows) {
        JPanel borrowButtonArea = new JPanel(new GridBagLayout());
        borrowButton.addActionListener(ActionEvent ->{
           showBorrows(borrows); 
        });
        GridBagConstraints cons = new GridBagConstraints();
        cons.anchor = GridBagConstraints.FIRST_LINE_END;
        cons.insets = new Insets(0,0,0,420);
        borrowButtonArea.add(borrowButton,cons);
        borrowArea.add(borrowButtonArea);
        
        
        add(borrowArea);
    }

    private void addBooks(List<Book> books) {
        JPanel bookButtonArea = new JPanel(new GridBagLayout());
        bookButton.addActionListener(ActionEvent ->{
           showBooks(books); 
        });
        GridBagConstraints cons = new GridBagConstraints();
        cons.anchor = GridBagConstraints.FIRST_LINE_END;
        cons.insets = new Insets(0,0,0,420);
        bookButtonArea.add(bookButton,cons);
        bookArea.add(bookButtonArea);
        
      
        
        add(bookArea);
    }

    private void showMembers(List<Member> members) {
        createJFrame();
        dataTable.setTitle("Members");
        dataTable.remove(scrollPane);

        DefaultTableModel model = new DefaultTableModel();
        setMemberTableColumnNames(model);
        setMemberTableRowData(model, members);
        
        JTable data = new JTable(model);
        data.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(data);
        
        dataTable.getContentPane().add(scrollPane);
        dataTable.revalidate();
        dataTable.repaint();
    }
    
    private void showCopies(List<Copy> copies) {
        createJFrame();
        dataTable.setTitle("Copies");
        dataTable.remove(scrollPane);

        DefaultTableModel model = new DefaultTableModel();
        setCopyTableColumnNames(model);
        setCopyTableRowData(model, copies);
        
        JTable data = new JTable(model);
        data.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(data);
        
        dataTable.getContentPane().add(scrollPane);
        dataTable.revalidate();
        dataTable.repaint();
    }

    private void showBooks(List<Book> books) {
        createJFrame();
        dataTable.setTitle("Books");
        dataTable.remove(scrollPane);
        
        DefaultTableModel model = new DefaultTableModel();
        setBookTableColumnNames(model);
        setBookTableRowData(model, books);
        
        JTable data = new JTable(model);
        data.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(data);
        
        dataTable.getContentPane().add(scrollPane);
        dataTable.revalidate();
        dataTable.repaint();
    }
    
    private void showBorrows(List<Borrow> borrows) {
        createJFrame();
        dataTable.setTitle("Borrows");
        dataTable.remove(scrollPane);
        
        DefaultTableModel model = new DefaultTableModel();
        setBorrowTableColumnNames(model);
        setBorrowTableRowData(model, borrows);
        
        JTable data = new JTable(model);
        data.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(data);
        
        dataTable.getContentPane().add(scrollPane);
        dataTable.revalidate();
        dataTable.repaint();
    }
    
    private void createJFrame() {
        dataTable.dispose();
        Dimension dimOfWindow = new Dimension(500,200);
        Dimension screendim = Toolkit.getDefaultToolkit().getScreenSize();
        dataTable.setLocation(screendim.width/2-getSize().width/2-dimOfWindow.width/2, 
                    screendim.height/2-getSize().height/2-dimOfWindow.height/2);
        dataTable.setSize(dimOfWindow);
        dataTable.setResizable(false);
        dataTable.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                dataTable.dispose();
            }            
        });
        dataTable.getContentPane().add(scrollPane);
        dataTable.setVisible(true);
    }

    
    private void setCopyTableColumnNames(DefaultTableModel model) {
        model.addColumn("Book ID");
        model.addColumn("Store ID");
        model.addColumn("Borrowed");
        model.fireTableDataChanged();
    }

    private void setCopyTableRowData(DefaultTableModel model, List<Copy> copies) {
        for(Copy item : copies){
            model.addRow(new Object[]{item.getBookID(), item.getStoreID(), item.isBorrowed()});
        }
        model.fireTableDataChanged();
    }
    
    private void setBookTableColumnNames(DefaultTableModel model) {
        model.addColumn("Book ID");
        model.addColumn("Name");
        model.addColumn("PCS");
        model.addColumn("Author");
        model.addColumn("Date of publicity");
        model.addColumn("Locked");
        model.fireTableDataChanged();
    }

    private void setBookTableRowData(DefaultTableModel model, List<Book> books) {
        for(Book item : books){
            model.addRow(new Object[]{item.getBookID(), item.getName(), item.getPcs(), item.getAuthor(), item.getOutYear(),item.isLock()});
        }
        model.fireTableDataChanged();
    }
    
    private void setBorrowTableColumnNames(DefaultTableModel model) {
        model.addColumn("Ticket ID");
        model.addColumn("Name");
        model.addColumn("Book ID");
        model.addColumn("Store ID");
        model.addColumn("Retrieved");
        model.addColumn("Given out");
        model.fireTableDataChanged();
    }

    private void setBorrowTableRowData(DefaultTableModel model, List<Borrow> borrows) {
        for(Borrow item : borrows){
            model.addRow(new Object[]{item.getTicketID(), item.getMemberName(), item.getBookID(),item.getStoreID(),item.getInDate(),item.getOutDate()});
        }
        model.fireTableDataChanged();
    }

    private void setMemberTableColumnNames(DefaultTableModel model) {
        model.addColumn("Ticket ID");
        model.addColumn("Name");
        model.addColumn("Address");
        model.addColumn("Borrowed copies");
        model.fireTableDataChanged();
    }

    private void setMemberTableRowData(DefaultTableModel model, List<Member> members) {
        for(Member item : members){
            model.addRow(new Object[]{item.getTicketID(), item.getName(), item.getAddress(),item.getBorrowpcs()});
        }
        model.fireTableDataChanged();
    }
}