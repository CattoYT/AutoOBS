package me.catto.AutoOBS.features;
import gg.essential.api.EssentialAPI;
import me.catto.AutoOBS.AutoOBS;
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
        if(AutoOBS.INSTANCE.getAutoOBSConfig().getAutoOBS()) {
            try {
                controller.connect();
            }
            catch(Throwable e) {
                AutoOBS.logger.info("[AutoOBS]: Failed To Connect To OBS!");
                EssentialAPI.getNotifications().push(
                        "AutoOBS",
                        "Failed To Connect To OBS Studio!", 6
                );

            }
            obsConnected = true;

            AutoOBS.logger.info("Connected to OBS");
            EssentialAPI.getNotifications().push(
                    "AutoOBS",
                    "Connected To OBS Studio!", 6
            );
        } else if (!AutoOBS.INSTANCE.getAutoOBSConfig().getAutoOBS()) {
            AutoOBS.logger.info("[AutoOBS]: Please Toggle AutoOBS To Connect To OBS!");
            EssentialAPI.getNotifications().push(
                    "AutoOBS",
                    "Please Toggle AutoOBS To OBS Studio!", 6
            );
        } else {
            AutoOBS.logger.info("[AutoOBS]: Failed To Connect To OBS!");
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
            AutoOBS.logger.info("[AutoOBS]: Failed To Disconnect To OBS!");
            EssentialAPI.getNotifications().push(
                    "AutoOBS",
                    "Failed To Disconnect From OBS Studio!", 6
            );
        }
        obsConnected = false;
        AutoOBS.logger.info("[AutoOBS]: Disconnected from OBS!");
        EssentialAPI.getNotifications().push(
                "AutoOBS",
                "Disconnected from OBS!", 6
        );

    }

    public void startRecording() {
        if(AutoOBS.INSTANCE.getAutoOBSConfig().getAutoOBS()) {
            controller.startRecord(5);
            AutoOBS.logger.info("Started Recording");
        }
    }
    public void stopRecording() {
        if(AutoOBS.INSTANCE.getAutoOBSConfig().getAutoOBS()) {
            controller.stopRecord(5);
            if(AutoOBS.INSTANCE.getAutoOBSConfig().getOBSPause()) {
                controller.pauseRecord(5);
            }

            AutoOBS.logger.info("stopped Recording");
        }
    }

    public OBSRemoteController getController() {
        return controller;
    }
}
