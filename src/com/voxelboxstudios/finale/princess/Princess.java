package com.voxelboxstudios.finale.princess;

import com.voxelboxstudios.finale.MTP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Princess {

    /** Texts **/

    private List<String> texts;


    /** Constructor **/

    public Princess() {
        /** Texts **/

        texts = new ArrayList<String>();

        texts.add("Ich bin beeindruckt. Aber das geht besser!");
        texts.add("Nicht schlecht, für den Anfang.");
        texts.add("Ihr seid wahrlich würdig, mein Gatte zu werden!");
        texts.add("Ob mein Vater das toleriert?");
        texts.add("Ein spannendes Spiel. Möge der Beste mein Gatte werden!");
    }


    /** Speak **/

    public void speak() {
        /** Texts **/

        List<String> newtexts = new ArrayList<String>(texts);


        /** Shuffle **/

        Collections.shuffle(newtexts);


        /** Message **/

        String message = newtexts.get(0);


        /** Broadcast **/

        Bukkit.broadcastMessage(ChatColor.GOLD + "Prinzessin " + ChatColor.YELLOW + "» " + ChatColor.GRAY + "§o" + message);
    }

}
