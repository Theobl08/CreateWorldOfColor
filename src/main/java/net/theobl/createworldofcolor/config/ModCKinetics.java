package net.theobl.createworldofcolor.config;

import net.createmod.catnip.config.ConfigBase;

public class ModCKinetics extends ConfigBase {
    public final ModCStress stressValues = nested(1, ModCStress::new, Comments.stress);

    @Override
    public String getName() {
        return "kinetics";
    }

    private static class Comments {
        static String stress = "Fine tune the kinetic stats of individual components";
    }
}
