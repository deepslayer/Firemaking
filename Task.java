package Firemaking;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;

import org.dreambot.api.wrappers.interactive.GameObject;


public class Task  {
    public static boolean hasTinderbox() {
        if (Inventory.contains("Tinderbox")) {
            return true;
        } else return false;
    }

    public static boolean hasLogs() {
        if (Inventory.contains("Logs")) {
            return true;
        } else return false;
    }

    private static boolean isLightingFire() {
        if (Players.localPlayer().isAnimating()) {
            return true;
        } else return false;
    }

    public static boolean isFireUnderPlayer() {
        GameObject Fire = GameObjects.closest("Fire");
        if (Fire != null && Fire.getTile().equals(Players.localPlayer().getTile())) {
            MethodProvider.log("True");
            return true;
        } else {

            MethodProvider.log("False");
            return false;
        }
    }

    public static boolean isInFireMakingArea() {
        if (Areas.FIRE_SPACE.contains(Players.localPlayer())) {
            return true;
        } else
            return false;
    }

    public static void burnLogs() {
            if (hasLogs() && hasTinderbox() && !Inventory.isItemSelected()) {
                MethodProvider.log("clicking use tinderbox");
                Inventory.interact("Tinderbox", "Use");
                MethodProvider.log("Waiting ..");
                MethodProvider.sleepUntil(() -> Inventory.isItemSelected(), 5000); //checks if item is selected
            }

            if (hasLogs() && hasTinderbox() && Inventory.isItemSelected()) {
                MethodProvider.log("clicking on logs");
                Inventory.interact("Logs", "Use");
                MethodProvider.sleep(2000);
                MethodProvider.sleepUntil(() -> !isLightingFire(), 60000);
            }
            if (Dialogues.canContinue()) {
                Dialogues.spaceToContinue();
                MethodProvider.sleep(1500, 2000);
            }


    }

    public static void withdrawLogs() {
        if (!Bank.isOpen()) {
            Bank.openClosest();
            MethodProvider.sleepUntil(()-> Bank.isOpen(),5000);
        }

        if (Bank.isOpen()) {
            if(Bank.contains("Logs")) {
                Bank.withdrawAll("Logs");
                MethodProvider.sleepUntil(()-> Inventory.contains("Logs"),5000);
            }else{
                MethodProvider.log("No logs");

            }
        }

        if(Inventory.contains("Logs") && Bank.isOpen()){
            Bank.close();
            MethodProvider.sleepUntil(()-> !Bank.isOpen(),5000);
        }




    }



}



