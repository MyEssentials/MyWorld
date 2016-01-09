package myworld.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import myessentials.event.BlockTrampleEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.world.BlockEvent;

public class BlockHandler extends AbstractHandler {
    @Override
    public String getName() {
        return "block";
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent ev) {
        if (!enabled() || ev.isCanceled()) return;

        if (!checkPermission(ev.getPlayer(), "blocks.break", ev.block)) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent ev) {
        if (!enabled() || ev.isCanceled()) return;

        if (!checkPermission(ev.player, "blocks.place", ev.block)) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onMultiBlockPlace(BlockEvent.MultiPlaceEvent ev) {
        if (!enabled() || ev.isCanceled()) return;

        for (BlockSnapshot snapshot : ev.getReplacedBlockSnapshots()) {
            if (!checkPermission(ev.player, "blocks.place", snapshot.getCurrentBlock())) {
                ev.setCanceled(true);
                return;
            }
        }
    }

    @SubscribeEvent
    public void onBlockTrample(BlockTrampleEvent ev) {
        if (!enabled() || ev.isCanceled()) return;

        if (ev.entity instanceof EntityPlayer && !checkPermission((EntityPlayer) ev.entity, "blocks.trample", ev.block)) {
            ev.setCanceled(true);
        }
    }
}
