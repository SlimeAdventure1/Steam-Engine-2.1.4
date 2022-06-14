package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.util.EnumUtils;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AttitudeAdjusterItem extends ExtendedHitWeaponItem implements IVanishable, IAnimatable {
    private static final float EXPLOSION_POWER = CAConfig.COMMON.attitudeAdjusterExplosionSize.get();
    public AnimationFactory factory = new AnimationFactory(this);

    public AttitudeAdjusterItem(EnumUtils.CAItemTier tier, int attackDamageIn, float attackSpeedIn, double reachDistance, double knockBack, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, reachDistance, knockBack, builderIn);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.level.isClientSide) {
            boolean hasFire = CAConfig.COMMON.attitudeAdjusterExplosionFire.get();
            switch (CAConfig.COMMON.attitudeAdjusterExplosionType.get()) {
                case 0: target.level.explode(null, target.position().x, target.position().y, target.position().z, EXPLOSION_POWER, hasFire, Explosion.Mode.NONE);
                case 1: target.level.explode(null, target.position().x, target.position().y, target.position().z, EXPLOSION_POWER, hasFire, Explosion.Mode.BREAK);
                case 2: target.level.explode(null, target.position().x, target.position().y, target.position().z, EXPLOSION_POWER, hasFire, Explosion.Mode.DESTROY);
            }
        }
        stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        return true;
    }

    @Override
    public void registerControllers(AnimationData data) {
        // insert controllers here
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }
}
