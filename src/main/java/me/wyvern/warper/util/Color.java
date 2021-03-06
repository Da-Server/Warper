package me.wyvern.warper.util;

import org.bukkit.ChatColor;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color {

    public static String colorize(String in){
        String finalIn = in;
        finalIn = translateHexColorCodes(finalIn);
        return ChatColor.translateAlternateColorCodes('&', finalIn);
    }

    //I Did not write this bit;
    public static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    public static final char COLOR_CHAR = ChatColor.COLOR_CHAR;

    public static String translateHexColorCodes(String message) {
        //Sourced from this post by imDaniX: https://github.com/SpigotMC/BungeeCord/pull/2883#issuecomment-653955600
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }

}
