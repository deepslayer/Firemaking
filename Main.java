package Firemaking;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;

import java.awt.*;


@ScriptManifest(name = "Fire making", description = "Burns logs @ the Grand Exchange", author = "Deep Slayer",
        version = 1.0, category = Category.FIREMAKING, image = "")


public class Main extends AbstractScript {
    Timer timer = new Timer();
    @Override
    public void onPaint(Graphics g){
        g.setColor(Color.cyan);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Time Elapsed: " + timer.formatTime(),20,20);
    }

    @Override
    public int onLoop() {

           if(!Inventory.contains("Logs")){
               Task.withdrawLogs();
           }
           if(Inventory.contains("Logs") && !Task.isInFireMakingArea()){
               Walking.walk(Areas.FIRE_SPACE.getRandomTile());
               sleep(1000,2000);
               sleepUntil(()-> Walking.getDestinationDistance() < 3,5000);
           }
           if(Inventory.contains("logs") && Task.isInFireMakingArea()){
               if(Task.isFireUnderPlayer()){
                   Walking.walk(Areas.FIRE_SPACE.getRandomTile());
               }else {
                   Task.burnLogs();
               }
           }



        return 745;
    }
}