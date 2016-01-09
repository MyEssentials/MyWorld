package myworld.handlers;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

public class ItemHandler extends AbstractHandler {
    @Override
    public String getName() {
        return "item";
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent ev) {
        if (!enabled() || ev.isCanceled()) return;

        if (!checkPermission(ev.entityPlayer, "items.pickup", ev.item)) {
            ev.setCanceled(true);
            ev.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void onItemToss(ItemTossEvent ev) {
        if (!enabled() || ev.isCanceled()) return;

        if (!checkPermission(ev.player, "items.toss", ev.entityItem)) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerUseItem(PlayerUseItemEvent.Start ev) {
        if (!enabled() || ev.isCanceled()) return;

        if (!checkPermission(ev.entityPlayer, "items.use", ev.item)) {
            ev.setCanceled(true);
        }
    }
}
