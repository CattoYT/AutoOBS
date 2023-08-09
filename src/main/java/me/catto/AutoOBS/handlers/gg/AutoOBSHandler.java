package me.catto.AutoOBS.handlers.gg;

import me.catto.AutoOBS.AutoOBS;
import me.catto.AutoOBS.tasks.data.Server;
import gg.essential.api.utils.Multithreading;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static me.catto.AutoOBS.AutoOBS.obs;

/**
 * Where the magic happens...
 * We handle which server's triggers should be used
 * and how to detect which server the player is currently
 * on.
 *
 * @author ChachyDev
 */
public class AutoOBSHandler {
    private volatile Server server;
    private long lastGG = 0;

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.entity == Minecraft.getMinecraft().thePlayer && AutoOBS.INSTANCE.getAutoOBSConfig().getAutoOBS()) {
            Multithreading.runAsync(() -> {
                for (Server s : AutoOBS.INSTANCE.getTriggers().getServers()) {
                    try {
                        if (s.getDetectionHandler().getDetector().detect(s.getData())) {
                            server = s;
                            return;
                        }
                    } catch (Throwable e) {
                        // Stop log spam
                    }
                }

                // In case if it's not null and we couldn't find the triggers for the current server.
                server = null;
            });
        }
    }

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        if (event.type == 2) return;
        String stripped = EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getUnformattedText());
        

        if(stripped.contains("Eliminate your opponents!")) {
            obs.startRecording();
            AutoOBS.logger.info("DONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONEDONE");
        }
    }

    private void stopRecording() throws InterruptedException {
        
        if (server != null) {
            String prefix = server.getMessagePrefix();

            if (System.currentTimeMillis() - lastGG < 10_000) return;
            lastGG = System.currentTimeMillis();
            
            
        }
        Multithreading.runAsync(() -> {
            try {
                Thread.sleep(AutoOBS.INSTANCE.getAutoOBSConfig().getAutoOBSDelay()*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            obs.stopRecording();
        });
    }
}
