package me.catto.AutoOBS.utils;

import gg.essential.api.EssentialAPI;
import gg.essential.universal.ChatColor;
import gg.essential.universal.wrappers.message.UTextComponent;

public class ChatUtil {

    public static void sendLocalMessage (String message) {
        EssentialAPI.getMinecraftUtil().sendMessage(new UTextComponent(ChatColor.GREEN + "[Auto" +
                ChatColor.AQUA + "OBS]: " + ChatColor.RESET + message));
    }

}
