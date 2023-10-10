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
        if (pAttacker instanceof Player player) {
            int bonus = getArmorBuff(player);
            if (bonus > 0) {
                List<? extends LivingEntity> targets = getTargets(pTarget, player, bonus);
                for (LivingEntity entity : targets)
                    inject(entity, player, false);
            }
            inject(pTarget, player, true);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    private void inject(LivingEntity entity, Player player, boolean consume) {
        if (!(InventoryUtil.checkForPhial(player)))
            return;
        ItemStack phialStack = player.getInventory().getItem(InventoryUtil.getFirstPhial(player));

        if (phialStack.getItem() instanceof PotionPhialItem) {
            ((PotionPhialItem) phialStack.getItem()).UseOnEntity(entity, player);

            phialStack.shrink(consume ? 1 : 0);
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

    private List<? extends LivingEntity> getTargets(LivingEntity target, Player attacker, int cap) {
        Vec3 range = new Vec3(5,5,5);
        Vec3 center = target.getPosition(1f);
        AABB aoe = new AABB(center.subtract(range), center.add(range));
        List<? extends LivingEntity> possibleTargets = target.getLevel().getEntitiesOfClass(LivingEntity.class, aoe);
        possibleTargets.remove(target);
        possibleTargets.remove(attacker);
            return getClosestEntities(possibleTargets, center, cap);
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
