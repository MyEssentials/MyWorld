package myworld;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import myessentials.Localization;
import myworld.subsystem.Protections;
import org.apache.logging.log4j.Logger;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.MOD_VERSION, dependencies = Constants.DEPENDENCIES, acceptableRemoteVersions = "*")
public class MyWorld {
    @Instance
    public static MyWorld instance;

    public Logger LOG;
    public Localization LOCAL;

    @EventHandler
    public void preInit(FMLPreInitializationEvent ev) {
        // Setup Log
        LOG = ev.getModLog();

        // Setup Constants
        Constants.CONFIG_FOLDER = ev.getModConfigurationDirectory().getPath() + "/MyWorld/";

        // Init config
        Config.instance.init(Constants.CONFIG_FOLDER + "/MyWorld.cfg", Constants.MOD_ID);

        // Init Localization
        LOCAL = new Localization(Constants.CONFIG_FOLDER+"/localization/", Config.instance.localization.get(), "/mytown/localization/", MyWorld.class);
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent ev) {
        Protections.init();
    }

    @EventHandler
    public void serverStopping(FMLServerStoppingEvent ev) {
    }
}
