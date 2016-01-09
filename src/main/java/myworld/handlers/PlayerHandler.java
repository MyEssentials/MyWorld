package myworld.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import myessentials.utils.PlayerUtils;
import myworld.MyWorld;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class PlayerHandler extends AbstractHandler {
    @Override
    public String getName() {
        return "player";
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent ev) {
        if (!enabled()) return;

        EntityPlayerMP pl = (EntityPlayerMP) ev.player;

        if (!checkPermission(pl, "player.login") || (pl.dimension == 0 && !checkPermission(pl, "world.enter", 0))) {
            pl.playerNetServerHandler.kickPlayerFromServer(MyWorld.instance.LOCAL.getLocalization(BASE_PERMISSION + ".player.login"));
            return;
        }

        if (pl.dimension != 0 && !checkPermission(pl, "world.enter", pl.dimension)) {
            PlayerUtils.transferPlayerToDimension(pl, pl.worldObj.provider.getRespawnDimension(pl));
        }
    }

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent ev) {
        if (!enabled()) return;

        EntityPlayerMP pl = (EntityPlayerMP) ev.player;

        if (pl.dimension != 0 && !checkPermission(pl, "world.enter", pl.dimension)) {
            PlayerUtils.transferPlayerToDimension(pl, pl.worldObj.provider.getRespawnDimension(pl));
        }
    }

    @SubscribeEvent
    public void onPlayerChat(ServerChatEvent ev) {
        if (!enabled() || ev.isCanceled()) return;

        if (!checkPermission(ev.player, "player.chat")) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerCommand(CommandEvent ev) {
        if (!enabled() || ev.isCanceled() || !(ev.sender instanceof EntityPlayer)) return;

        if (!checkPermission((EntityPlayer) ev.sender, "player.command", ev.command.getCommandName())) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent ev) {
        if (!enabled() || ev.isCanceled()) return;

        Block block = ev.world.getBlock(ev.x, ev.y, ev.z);
        ItemStack item = ev.entityPlayer.getHeldItem();

        if (ev.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            if (!checkPermission(ev.entityPlayer, "blocks.break", block)) {
                ev.setCanceled(true);
            }
        } else if (ev.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            if (item != null && !checkPermission(ev.entityPlayer, "items.use", item, "on.block", block)) {
                ev.entityPlayer.stopUsingItem();
                ev.setCanceled(true);
            } else if (!checkPermission(ev.entityPlayer, "blocks.interact", block)) {
                ev.setCanceled(true);
            } else if (item != null) {
                Block toPlace = Block.getBlockFromItem(item.getItem());
                if (toPlace != null && !checkPermission(ev.entityPlayer, "blocks.place", toPlace)) {
                    ev.setCanceled(true);
                }
            }
        } else if (ev.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR && item != null) {
            ev.entityPlayer.stopUsingItem();
            ev.setCanceled(true);
        }
    }
}
