package myworld.api.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BlockUtil {
    public static String getBlockName(Block block) {
        return GameRegistry.findUniqueIdentifierFor(block).toString().replace(":", ".");
    }
}
