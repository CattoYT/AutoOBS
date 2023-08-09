package me.catto.AutoOBS.command;

import gg.essential.api.commands.SubCommand;
import me.catto.AutoOBS.AutoOBS;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;

import static me.catto.AutoOBS.utils.ChatUtil.sendLocalMessage;

public class AutoOBSCommand extends Command {
    public AutoOBSCommand() {
        super("autoobs");
    }

    @DefaultHandler
    public void handle() {
        sendLocalMessage("OBS Command. Syntax: /autoobs [connect/disconnect]");

    }

    @SubCommand(value = "connect", description = "Connects to OBS")
    public void connect() {
        AutoOBS.obs.startOBS();
    }

    @SubCommand(value = "disconnect", description = "Connects to OBS")
    public void disconnect() {
        AutoOBS.obs.endOBS();
    }

    @SubCommand(value = "toggle", description = "Toggles AutoOBS")
    public void toggle() {
        if(AutoOBS.INSTANCE.getAutoOBSConfig().getAutoOBS())
            AutoOBS.INSTANCE.getAutoOBSConfig().setAutoOBS(false);
        else {
            AutoOBS.INSTANCE.getAutoOBSConfig().setAutoOBS(true);
        }
    }

}
