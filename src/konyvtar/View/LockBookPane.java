package konyvtar.View;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import konyvtar.Book.Book;
import konyvtar.Book.BookCache;

public class LockBookPane extends JPanel{
    private final JComboBox items = new JComboBox();
    private final JComboBox itemNames = new JComboBox();
    private final JButton lock = new JButton("Lock");
    private final BookCache controller = MainWindow.bookCache;
    
    public LockBookPane(){
        setLayout(new GridLayout(2,1,0,400));
        
        List<Book> books = MainWindow.bookCache.getDataList();
        for(Book item : books){
            items.addItem(item);
            itemNames.addItem(item.getName());
        }
        lockButton(); 
        add(itemNames);
        add(lock);
        setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 250));
    }

    private void lockButton() {
        lock.addActionListener(ActionEvent ->{
            Book obj = (Book) items.getItemAt(itemNames.getSelectedIndex());
            if (obj != null){
                if(MainWindow.lock()){
                    controller.lock(obj);
                }
            } else {
                MainWindow.infoBox("Please select a book", "Error");
            }
        });
    }
}
