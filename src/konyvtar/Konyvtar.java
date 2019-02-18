package konyvtar;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;
import konyvtar.View.MainWindow;

public class Konyvtar {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow();
        });
        
        System.out.println("Timer started, every 2 minutes cache will be refreshed");
        Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                  MainWindow.refreshCache();
                  System.out.println("Cache refreshed");
                }
            }, 2*60*1000, 2*60*1000);
    }
    
}
