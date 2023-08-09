/*
 * AutoGG - Automatically say a selectable phrase at the end of a game on supported servers.
 * Copyright (C) 2020  Sk1er LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.catto.autogg;

import me.catto.autogg.command.AutoGGCommand;
import me.catto.autogg.command.AutoOBSCommand;
import me.catto.autogg.config.AutoGGConfig;
import me.catto.autogg.handlers.gg.AutoGGHandler;
import me.catto.autogg.handlers.patterns.PlaceholderAPI;
import me.catto.autogg.features.OBSImpl;
import me.catto.autogg.tasks.RetrieveTriggersTask;
import me.catto.autogg.tasks.data.TriggersSchema;
import gg.essential.api.EssentialAPI;
import gg.essential.api.utils.JsonHolder;
import gg.essential.api.utils.Multithreading;
import gg.essential.api.utils.WebUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.*;



/**
 * Contains the main class for AutoGG which handles trigger schema setting/getting and the main initialization code.
 *
 * @author ChachyDev
 */
@Mod(modid = "autogg", name = "AutoOBS", version = "1.0 SNAPSHOT")
public class AutoGG {

    @Mod.Instance
    public static AutoGG INSTANCE;

    public static OBSImpl obs = new OBSImpl();

    private final String[] primaryGGStrings = {"gg", "GG", "gf", "Good Game", "Good Fight", "Good Round! :D"};
    private final String[] secondaryGGStrings = {"Have a good day!", "<3", "AutoGG By Sk1er!", "gf", "Good Fight", "Good Round", ":D", "Well played!", "wp"};
    private TriggersSchema triggers;
    private AutoGGConfig autoGGConfig;
    private boolean usingEnglish;
    public static final Logger logger = LogManager.getLogger("AutoGG");
    public static final Minecraft mc = Minecraft.getMinecraft();

    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event) {
        Multithreading.runAsync(this::checkUserLanguage);


    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        autoGGConfig = new AutoGGConfig();
        autoGGConfig.preload();
        evalJVMArgs();
        Set<String> joined = new HashSet<>();
        joined.addAll(Arrays.asList(primaryGGStrings));
        joined.addAll(Arrays.asList(secondaryGGStrings));



        PlaceholderAPI.INSTANCE.registerPlaceHolder("antigg_strings", String.join("|", joined));

        Multithreading.runAsync(new RetrieveTriggersTask());
        MinecraftForge.EVENT_BUS.register(new AutoGGHandler());
        EssentialAPI.getCommandRegistry().registerCommand(new AutoGGCommand());
        EssentialAPI.getCommandRegistry().registerCommand(new AutoOBSCommand());
        // fix settings that were moved to seconds instead of ms
        // so users aren't waiting 5000 seconds to send GG
        if (autoGGConfig.getAutoGGDelay() > 5) autoGGConfig.setAutoGGDelay(1);
        if (autoGGConfig.getSecondaryDelay() > 5) autoGGConfig.setSecondaryDelay(1);
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        Multithreading.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            obs.startOBS();
        });

        if (!usingEnglish) {
            EssentialAPI.getNotifications().push(
                "AutoGG",
                "We've detected your Hypixel language isn't set to English! AutoGG will not work on other languages.\n" +
                    "If this is a mistake, feel free to ignore it.", 6
            );
        }
        /*if(!Loader.isModLoaded("rnss")) {
            EssentialAPI.getNotifications().push(
                    "AutoOBS",
                    "You do not have DuelsEssentials installed! \n" + "This will cause autoFileDeleteOnLoss to be automatically disabled!", 6
            );
        }*/
    }

    private void checkUserLanguage() {
        final String username = Minecraft.getMinecraft().getSession().getUsername();
        final JsonHolder json = WebUtil.fetchJSON("https://api.sk1er.club/player/" + username);
        final String language = json.optJSONObject("player").defaultOptString("userLanguage", "ENGLISH");
        this.usingEnglish = "ENGLISH".equals(language);
    }


    public TriggersSchema getTriggers() {
        return triggers;
    }

    public void setTriggers(TriggersSchema triggers) {
        this.triggers = triggers;
    }

    public AutoGGConfig getAutoGGConfig() {
        return autoGGConfig;
    }

    public String[] getPrimaryGGStrings() {
        return primaryGGStrings;
    }

    public String[] getSecondaryGGStrings() {
        return secondaryGGStrings;
    }

    public void evalJVMArgs() {RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> jvmArguments = runtimeMxBean.getInputArguments();

        // Check if the myCustomArg argument is present
        if (jvmArguments.stream().anyMatch(arg -> arg.startsWith("-DForceOBSToggle="))) {
            // Extract the value of the myCustomArg argument
            String ForceOBSJVMArg = jvmArguments.stream()
                    .filter(arg -> arg.startsWith("-DForceOBSToggle="))
                    .findFirst()
                    .map(arg -> arg.substring("-DForceOBSToggle=".length()))
                    .orElse(null);

            // Parse the value of the myCustomArg argument as a boolean
            boolean ForceOBSArg = Boolean.parseBoolean(ForceOBSJVMArg);

            // Do something based on the value of the myCustomArg argument
            if (ForceOBSArg) {
                getAutoGGConfig().setAutoOBS(false);
                if (AutoGG.obs.obsConnected) {
                    AutoGG.obs.endOBS();
                }
            }
        }
    }
}
