package me.catto.autogg.detectors.branding;

import me.catto.autogg.detectors.IDetector;
import me.catto.autogg.handlers.patterns.PatternHandler;
import net.minecraft.client.Minecraft;

public class ServerBrandingDetector implements IDetector {
    @Override
    public boolean detect(String data) {
        return Minecraft.getMinecraft().thePlayer != null && PatternHandler.INSTANCE.getOrRegisterPattern(data).matcher(Minecraft.getMinecraft().thePlayer.getClientBrand()).matches();
    }
}
