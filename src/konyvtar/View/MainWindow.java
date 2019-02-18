package konyvtar.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import konyvtar.Book.BookCache;
import konyvtar.Book.BookCacheImpl;
import konyvtar.Borrow.BorrowCache;
import konyvtar.Borrow.BorrowCacheImpl;
import konyvtar.Copy.CopyCache;
import konyvtar.Copy.CopyCacheImpl;
import konyvtar.Member.MemberCache;
import konyvtar.Member.MemberCacheImpl;


public class MainWindow extends JFrame{     
    public static MemberCache memberCache = new MemberCacheImpl();
    public static CopyCache copyCache = new CopyCacheImpl();
    public static BookCache bookCache = new BookCacheImpl();
    public static BorrowCache borrowCache = new BorrowCacheImpl();
    private final JPanel menuButtons = new JPanel(new GridLayout(6,1));
    
    private final JButton menuButtonKezdolap = new JButton("Main screen");
    private final JButton menuButtonTagFelvetel = new JButton("Add new member");
    private final JButton menuButtonTagTorlese = new JButton("Delete member");
    private final JButton menuButtonKolcsonzesFelvetel = new JButton("Add borrow");
    private final JButton menuButtonKolcsonzesVisszavetel = new JButton("Retrieve Copy");
    private final JButton menuButtonKonyvZarolas = new JButton("Lock book");
    private final JButton menuButtonKonyvFeloldas = new JButton("Unlock book");
    private final JButton menuButtonModifyMember = new JButton("Modify member");
    
    private JPanel contentPane = new JPanel();
    
    private final JMenuBar mb = new JMenuBar();
    private final JMenu mInfo = new JMenu("Info");
    private final JMenuItem miSugo = new JMenuItem("Help");
    private final JMenuItem miNevjegy = new JMenuItem("About");
    private final JMenu mFile = new JMenu("File");
    private final JMenuItem miRefresh = new JMenuItem("Refresh cache");
    private final JMenuItem miKilep = new JMenuItem("Exit");
    
    public MainWindow(){
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }            
        });
        
        Dimension dimOfWindow = new Dimension(1000,800);
        Dimension screendim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screendim.width/2-getSize().width/2-dimOfWindow.width/2, 
                    screendim.height/2-getSize().height/2-dimOfWindow.height/2);
        setSize(dimOfWindow);
        setTitle("Könyvtár by Bíró Zoltán");
        
        addMenuBar();
        addMenuButtons();
        createKezdolapPane();
        
        setVisible(true);
        setResizable(false);
    }
    
    private void createKezdolapPane(){
        remove(contentPane);
        contentPane = new MainScreen();
        getContentPane().add(contentPane, BorderLayout.EAST);
        revalidate();
        repaint();
    }
    
    private void createModifyMemberPane() {
        remove(contentPane);
        contentPane = new ModifyMemberPane();
        getContentPane().add(contentPane, BorderLayout.EAST);
        revalidate();
        repaint();
    }
    
    private void createDeleteBorrowPane() {
        remove(contentPane);
        contentPane = new DeleteBorrowPane();
        getContentPane().add(contentPane,BorderLayout.EAST);
        revalidate();
        repaint();
    }
    
    private void createTagFelvetelPane(){
        remove(contentPane);
        contentPane = new AddMemberPane();
        getContentPane().add(contentPane, BorderLayout.EAST);
        revalidate();
        repaint();
    }
    
    private void createTagTorlesPane(){
        remove(contentPane);
        contentPane = new DeleteMemberPane();
        getContentPane().add(contentPane, BorderLayout.EAST);
        revalidate();
        repaint();
    }
    
    private void createKolcsonzesFelvetelPane(){
        remove(contentPane);
        contentPane = new AddBorrowPane();
        getContentPane().add(contentPane, BorderLayout.EAST);
        revalidate();
        repaint();
    }
    
    private void createKonyvZarolasPane(){
        remove(contentPane);
        contentPane = new LockBookPane();
        getContentPane().add(contentPane, BorderLayout.EAST);
        revalidate();
        repaint();
    }
    
    private void createKonyvFeloldasPane(){
        remove(contentPane);
        contentPane = new UnlockBookPane();
        getContentPane().add(contentPane, BorderLayout.EAST);
        revalidate();
        repaint();
    }
    
    private void addMenuButtons(){ 
        menuButtons.add(menuButtonKezdolap);
        menuButtons.add(menuButtonTagFelvetel);
        menuButtons.add(menuButtonTagTorlese);
        menuButtons.add(menuButtonKolcsonzesFelvetel);
        menuButtons.add(menuButtonKolcsonzesVisszavetel);
        menuButtons.add(menuButtonKonyvZarolas);
        menuButtons.add(menuButtonKonyvFeloldas);
        menuButtons.add(menuButtonModifyMember);
        
        menuButtons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        
        getContentPane().add(menuButtons,BorderLayout.WEST);
        
        setActionListenersForMenuButtons();
    }
    
    private void setActionListenersForMenuButtons(){
        menuButtonKezdolap.addActionListener(ActionEvent ->{
           createKezdolapPane(); 
        });
        menuButtonTagFelvetel.addActionListener(ActionEvent ->{
           createTagFelvetelPane(); 
        });
        
        menuButtonTagTorlese.addActionListener(ActionEvent -> {
            createTagTorlesPane();
        });
        
        menuButtonKolcsonzesFelvetel.addActionListener(ActionEvent -> {
            createKolcsonzesFelvetelPane();
        });
        
        menuButtonKolcsonzesVisszavetel.addActionListener(AcitonEvent -> {
            createDeleteBorrowPane();
        });
        
        
        menuButtonKonyvZarolas.addActionListener(ActionEvent -> {
            createKonyvZarolasPane();
        });
        
        menuButtonKonyvFeloldas.addActionListener(ActionEvent -> {
            createKonyvFeloldasPane();
        });
        
        menuButtonModifyMember.addActionListener(ActionEvent -> {
            createModifyMemberPane();
        });
    }
    
    private void addMenuBar(){
        setJMenuBar(mb);  
        
        mb.add(mFile);
        mFile.add(miRefresh);
        miRefresh.addActionListener(ActionEvent -> {
            refreshCache();
        });
        
        mFile.add(miKilep);
        miKilep.addActionListener(ActionEvent ->{
            exit();
        });
        
        mb.add(mInfo);
        mInfo.add(miNevjegy);
        miNevjegy.addActionListener(ActionEvent ->{
            infoBox("Name: Bíró Zoltán\nNeptun: DNHFMS\nCreate date: 2017.05.07.", "About");
        });
        
        mInfo.add(miSugo);
        miSugo.addActionListener(ActionEvent ->{
            infoBox("Contact the developers for help.", "Help");
        });
    }
    
    private static void exit(){
        int returnVal = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?","Confirmation panel",JOptionPane.YES_NO_OPTION);
        if (returnVal == JOptionPane.YES_OPTION) System.exit(0);
    }
    
    public static boolean save(){
        int returnVal = JOptionPane.showConfirmDialog(null,"Are you sure you want to modify","Confirmation panel",JOptionPane.YES_NO_OPTION);
        return returnVal == JOptionPane.YES_OPTION;
    }
    
    public static boolean delete(){
        int returnVal = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete?","Confirmation panel",JOptionPane.YES_NO_OPTION);
        return returnVal == JOptionPane.YES_OPTION;
    }
    
    public static boolean lock(){
        int returnVal = JOptionPane.showConfirmDialog(null,"Are you sure you want to lock?","Confirmation panel",JOptionPane.YES_NO_OPTION);
        return returnVal == JOptionPane.YES_OPTION;
    }
    
    public static boolean unlock(){
        int returnVal = JOptionPane.showConfirmDialog(null,"Are you sure you want to unlock?","Confirmation panel",JOptionPane.YES_NO_OPTION);
        return returnVal == JOptionPane.YES_OPTION;
    }
    
    public static void infoBox(String infoMessage, String titleBar){
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void refreshCache() {
        memberCache.refreshData();
        copyCache.refreshData();
        bookCache.refreshData();
        borrowCache.refreshData();
    }
}
