package net.avamaco.alchemicalutilities.item.custom;

import net.avamaco.alchemicalutilities.util.InventoryUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class AlchemistSwordItem extends SwordItem {
    public AlchemistSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pAttacker instanceof Player)
            inject(pTarget, (Player)pAttacker);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    private void inject(LivingEntity entity, Player player) {
        if (!(InventoryUtil.checkForPhial(player)))
            return;
        ItemStack phialStack = player.getInventory().getItem(InventoryUtil.getFirstPhial(player));

        if (phialStack.getItem() instanceof AlchemicalUtilityItem) {
            ((AlchemicalUtilityItem) phialStack.getItem()).UseOnEntity(entity, player);

            phialStack.shrink(1);
            if (phialStack.isEmpty()) {
                (player).getInventory().removeItem(phialStack);
            }
        }
    }
}
