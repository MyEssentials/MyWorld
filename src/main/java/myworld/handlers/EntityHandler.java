package myworld.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class EntityHandler extends AbstractHandler {
    @Override
    public String getName() {
        return "entity";
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent ev) {
        if (!enabled() || ev.isCanceled()) return;

        if (ev.entityLiving instanceof EntityPlayer && !checkPermission((EntityPlayer) ev.entityLiving, "damage.take", ev.source)) {
            ev.setCanceled(true);

            // TODO Respawn player when taking "outOfWorld" damage
        }
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent ev) {
        if (!enabled() || ev.isCanceled()) return;

        if (!checkPermission(ev.entityPlayer, "damage.deal", ev.target)) {
            ev.setCanceled(true);
        } else if (ev.target instanceof EntityPlayer && !checkPermission((EntityPlayer) ev.target, "damage.take")) {
            ev.setCanceled(true);
        }
    }
}
