package me.catto.autogg.tasks.data;

import me.catto.autogg.detectors.IDetector;
import me.catto.autogg.detectors.branding.ServerBrandingDetector;
import me.catto.autogg.detectors.ip.ServerIPDetector;

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
