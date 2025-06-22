package net.theobl.createworldofcolor.config;

import com.simibubi.create.infrastructure.config.CKinetics;
import com.simibubi.create.infrastructure.config.CServer;
import net.createmod.catnip.config.ConfigBase;

public class ModCServer extends ConfigBase {
    public final ModCKinetics kinetics = nested(0, ModCKinetics::new, Comments.kinetics);

    @Override
    public String getName() {
        return "server";
    }

    private static class Comments {
        static String kinetics = "Parameters and abilities of Create: World of Color's kinetic mechanisms";
    }
}
