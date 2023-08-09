package me.catto.autogg.command;

import gg.essential.api.commands.SubCommand;
import me.catto.autogg.AutoGG;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;

import static me.catto.autogg.utils.ChatUtil.sendLocalMessage;

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
        AutoGG.obs.startOBS();
    }

    @SubCommand(value = "disconnect", description = "Connects to OBS")
    public void disconnect() {
        AutoGG.obs.endOBS();
    }

    @SubCommand(value = "toggle", description = "Toggles AutoOBS")
    public void toggle() {
        if(AutoGG.INSTANCE.getAutoGGConfig().getAutoOBS())
            AutoGG.INSTANCE.getAutoGGConfig().setAutoOBS(false);
        else {
            AutoGG.INSTANCE.getAutoGGConfig().setAutoOBS(true);
        }
    }

}
