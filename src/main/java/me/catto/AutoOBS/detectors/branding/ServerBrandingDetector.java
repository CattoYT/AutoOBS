package me.catto.AutoOBS.detectors.branding;

import me.catto.AutoOBS.detectors.IDetector;
import me.catto.AutoOBS.handlers.patterns.PatternHandler;
import net.minecraft.client.Minecraft;

public class ServerBrandingDetector implements IDetector {
    @Override
    public boolean detect(String data) {
        return Minecraft.getMinecraft().thePlayer != null && PatternHandler.INSTANCE.getOrRegisterPattern(data).matcher(Minecraft.getMinecraft().thePlayer.getClientBrand()).matches();
    }
}
