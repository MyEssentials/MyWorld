package myworld.subsystem;

import cpw.mods.fml.common.FMLCommonHandler;
import myworld.api.BaseHandler;
import myworld.handlers.*;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public final class Protections {
    private static List<BaseHandler> handlers = new ArrayList<BaseHandler>();

    public static void init() {
        // TODO Replace this with an auto-discovery mechanism?
        handlers.add(new BlockHandler());
        handlers.add(new EntityHandler());
        handlers.add(new ItemHandler());
        handlers.add(new PlayerHandler());

        // Register handlers
        for (BaseHandler handler : handlers) {
            registerEventHandler(handler);
        }
    }

    private static void registerEventHandler(BaseHandler handler) {
        MinecraftForge.EVENT_BUS.register(handler);
        FMLCommonHandler.instance().bus().register(handler);
    }
}
