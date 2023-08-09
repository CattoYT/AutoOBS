package me.catto.autogg.detectors.ip;

import me.catto.autogg.detectors.IDetector;
import me.catto.autogg.handlers.patterns.PatternHandler;
import net.minecraft.client.Minecraft;

public class ServerIPDetector implements IDetector {
    @Override
    public boolean detect(String data) {
        return Minecraft.getMinecraft().thePlayer != null && PatternHandler.INSTANCE.getOrRegisterPattern(data).matcher(Minecraft.getMinecraft().getCurrentServerData().serverIP).matches();
    }
}
