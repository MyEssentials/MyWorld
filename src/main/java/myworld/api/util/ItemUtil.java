package myworld.api.util;

import myessentials.utils.ItemUtils;
import net.minecraft.item.ItemStack;

public class ItemUtil {
    public static String getItemPermission(ItemStack itemStack) {
        return ItemUtils.nameFromItemStack(itemStack).replace(":", ".");
    }
}
