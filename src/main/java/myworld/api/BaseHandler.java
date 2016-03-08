package myworld.api;

import myessentials.utils.ItemUtils;
import mypermissions.permission.api.proxy.PermissionProxy;
import myessentials.utils.BlockUtils;
import myessentials.utils.EntityUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public abstract class BaseHandler {
    protected static final String BASE_PERMISSION = "myworld.protections";

    public abstract String getName();

    public abstract boolean enabled();

    protected static boolean checkPermission(EntityPlayer player, Object... args) {
        String perm = assemblePermission(args);
        boolean result = PermissionProxy.getPermissionManager().hasPermission(player.getUniqueID(), perm);

        if (!result)  {
            // TODO Tell player they can't do it
        }

        return result;
    }

    protected static String assemblePermission(Object... args) {
        StringBuilder builder = new StringBuilder(BASE_PERMISSION);

        if (args != null) {
            for (Object arg : args) {
                if (arg == null) continue;

                builder.append(".");
                builder.append(getObjectPermission(arg));
            }
        }

        return builder.toString();
    }

    private static String getObjectPermission(Object obj) {
        if (obj instanceof EntityItem) {
            return getObjectPermission(((EntityItem) obj).getEntityItem());
        }

        if (obj instanceof Entity) {
            return EntityUtils.getEntityPermission((Entity) obj);
        } else if (obj instanceof ItemStack) {
            return ItemUtils.nameFromItemStack((ItemStack) obj).replace(":", ".");
        } else if (obj instanceof Block) {
            return BlockUtils.getBlockName((Block) obj);
        } else if (obj instanceof IInventory) {
            return ((IInventory) obj).getInventoryName();
        } else if (obj instanceof DamageSource) {
            return ((DamageSource) obj).getDamageType().toLowerCase().replace("_", "");
        }

        return obj.toString();
    }
}
