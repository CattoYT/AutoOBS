package me.catto.autogg.features;
import gg.essential.api.EssentialAPI;
import me.catto.autogg.AutoGG;
import io.obswebsocket.community.client.OBSRemoteController;

public class OBSImpl {


    private OBSRemoteController controller = OBSRemoteController.builder()
            .host("localhost")                  // Default host
            .port(4455)                         // Default port
            .password("OURHCxPHRS4Cm4dq")   // Provide your password here
            .connectionTimeout(5)               // Seconds the client will wait for OBS to respond
            .build();


    public boolean obsConnected;

    public void startOBS() {
        if(AutoGG.INSTANCE.getAutoGGConfig().getAutoOBS()) {
            try {
                controller.connect();
            }
            catch(Throwable e) {
                AutoGG.logger.info("[AutoOBS]: Failed To Connect To OBS!");
                EssentialAPI.getNotifications().push(
                        "AutoOBS",
                        "Failed To Connect To OBS Studio!", 6
                );

            }
            obsConnected = true;

            AutoGG.logger.info("Connected to OBS");
            EssentialAPI.getNotifications().push(
                    "AutoOBS",
                    "Connected To OBS Studio!", 6
            );
        } else if (!AutoGG.INSTANCE.getAutoGGConfig().getAutoOBS()) {
            AutoGG.logger.info("[AutoOBS]: Please Toggle AutoOBS To Connect To OBS!");
            EssentialAPI.getNotifications().push(
                    "AutoOBS",
                    "Please Toggle AutoOBS To OBS Studio!", 6
            );
        } else {
            AutoGG.logger.info("[AutoOBS]: Failed To Connect To OBS!");
            EssentialAPI.getNotifications().push(
                    "AutoOBS",
                    "Failed To Connect To OBS Studio!", 6
            );
        }
    }
    public void endOBS() {
        try {
            controller.disconnect();
        }
        catch(Throwable e) {
            AutoGG.logger.info("[AutoOBS]: Failed To Disconnect To OBS!");
            EssentialAPI.getNotifications().push(
                    "AutoOBS",
                    "Failed To Disconnect From OBS Studio!", 6
            );
        }
        obsConnected = false;
        AutoGG.logger.info("[AutoOBS]: Disconnected from OBS!");
        EssentialAPI.getNotifications().push(
                "AutoOBS",
                "Disconnected from OBS!", 6
        );

    }

    public void startRecording() {
        if(AutoGG.INSTANCE.getAutoGGConfig().getAutoOBS()) {
            controller.startRecord(5);
            AutoGG.logger.info("Started Recording");
        }
    }
    public void stopRecording() {
        if(AutoGG.INSTANCE.getAutoGGConfig().getAutoOBS()) {
            controller.stopRecord(5);
            if(AutoGG.INSTANCE.getAutoGGConfig().getOBSPause()) {
                controller.pauseRecord(5);
            }

            AutoGG.logger.info("stopped Recording");
        }
    }

    public OBSRemoteController getController() {
        return controller;
    }
}
