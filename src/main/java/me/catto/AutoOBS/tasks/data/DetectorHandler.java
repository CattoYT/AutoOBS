package me.catto.AutoOBS.tasks.data;

import me.catto.AutoOBS.detectors.IDetector;
import me.catto.AutoOBS.detectors.branding.ServerBrandingDetector;
import me.catto.AutoOBS.detectors.ip.ServerIPDetector;

public enum DetectorHandler {
    SERVER_BRANDING(new ServerBrandingDetector()),
    SERVER_IP(new ServerIPDetector());

    private final IDetector detector;

    DetectorHandler(IDetector detector) {
        this.detector = detector;
    }

    public IDetector getDetector() {
        return detector;
    }
}
