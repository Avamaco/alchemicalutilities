package net.avamaco.alchemicalutilities.item.custom;

import net.avamaco.alchemicalutilities.item.ModArmorMaterials;
import net.avamaco.alchemicalutilities.util.InventoryUtil;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class AlchemistSwordItem extends SwordItem {
    public AlchemistSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pAttacker instanceof Player) {
            inject(pTarget, (Player)pAttacker);
            int bonus = getArmorBuff((Player) pAttacker);
            pAttacker.sendMessage(new TextComponent(Integer.toString(bonus)), pAttacker.getUUID());
            if (bonus > 0) {
                Vec3 range = new Vec3(5,5,5);
                Vec3 center = pTarget.getPosition(1f);
                AABB aoe = new AABB(center.subtract(range), center.add(range));
                List<? extends LivingEntity> possibleTargets = pTarget.getLevel().getEntitiesOfClass(LivingEntity.class, aoe);
                possibleTargets.remove(pTarget);
                possibleTargets.remove(pAttacker);
                List<? extends LivingEntity> targets = getClosestEntities(possibleTargets, center, bonus);
                pAttacker.sendMessage(new TextComponent(targets.toString()), pAttacker.getUUID());
                for (LivingEntity entity : targets) {
                    pAttacker.sendMessage(new TextComponent("Working..."), pAttacker.getUUID());
                    inject(entity, (Player)pAttacker);
                }
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    private void inject(LivingEntity entity, Player player) {
        if (!(InventoryUtil.checkForPhial(player)))
            return;
        ItemStack phialStack = player.getInventory().getItem(InventoryUtil.getFirstPhial(player));

        if (phialStack.getItem() instanceof PotionPhialItem) {
            ((PotionPhialItem) phialStack.getItem()).UseOnEntity(entity, player);

            phialStack.shrink(1);
            if (phialStack.isEmpty()) {
                (player).getInventory().removeItem(phialStack);
            }
        }
    }

    private int getArmorBuff(Player player) {
        int ret = 0;
        for (int i = 0; i < 4; i++) {
            ItemStack stack = player.getInventory().getArmor(i);
            if (!stack.isEmpty() && stack.getItem() instanceof ArmorItem piece)
                if (piece.getMaterial() == ModArmorMaterials.IRIDESCENT)
                    ret++;
        }
        return ret;
    }

    private List<? extends LivingEntity> getClosestEntities(List<? extends LivingEntity> entities, Vec3 pos, int limit) {
        List<LivingEntity> ret = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            double d0 = -1.0D;
            LivingEntity e = null;
            for(LivingEntity entity : entities) {
                double d1 = entity.distanceToSqr(pos);
                if (d0 == -1.0D || d1 < d0) {
                    d0 = d1;
                    e = entity;
                }
            }
            if (e != null) ret.add(e);
            entities.remove(e);
        }
        return ret;
    }
}
