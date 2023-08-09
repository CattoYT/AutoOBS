package me.catto.AutoOBS.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

@SuppressWarnings("FieldMayBeFinal")
public class AutoOBSConfig extends Vigilant {

    @Property(
            type = PropertyType.SWITCH, name = "OBS AutoRecord",
            description = "Toggles OBS, Mainly used for small sumo clips",
            category = "General", subcategory = "AutoOBS"

    )
    private boolean AutoOBS;

    @Property(
            type = PropertyType.SWITCH, name = "OBS Pause",
            description = "Idk it just pauses along with stopping recording",
            category = "General", subcategory = "AutoOBS"

    )
    private boolean OBSPause;

    @Property(
            type = PropertyType.SLIDER, name = "AutoRecord Delay",
            description = "Delay between stopping the recording after the game ended",
            category = "General", subcategory = "AutoOBS",
            max = 5
    )
    private int AutoOBSDelay = 2;



    public AutoOBSConfig() {
        super(new File("./config/AutoOBS.toml"));
        initialize();
    }

    public void setAutoOBS(boolean autoOBS) {
        AutoOBS = autoOBS;
    }

    public boolean getAutoOBS() {
        return AutoOBS;
    }
    public boolean getOBSPause() {
        return OBSPause;
    }

    public int getAutoOBSDelay() {
        return AutoOBSDelay;
    }
}
