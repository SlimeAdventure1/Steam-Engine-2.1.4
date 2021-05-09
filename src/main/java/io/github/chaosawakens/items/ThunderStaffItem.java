package io.github.chaosawakens.items;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.config.CAConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ThunderStaffItem extends Item {

    public ThunderStaffItem(Properties builderIn) {
        super(builderIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
         if (!(worldIn instanceof ServerWorld))
             return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
         LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);

         RayTraceResult lookingAt = Minecraft.getInstance().objectMouseOver;
         if (lookingAt != null && lookingAt.getType() == RayTraceResult.Type.BLOCK) {

             ChaosAwakens.LOGGER.debug(lookingAt.getHitVec().getX());
             ChaosAwakens.LOGGER.debug(lookingAt.getHitVec().getY());
             ChaosAwakens.LOGGER.debug(lookingAt.getHitVec().getZ());

             lightning.setPosition(lookingAt.getHitVec().getX(), lookingAt.getHitVec().getY(), lookingAt.getHitVec().getZ());

             ItemStack held = playerIn.getHeldItem(handIn);

             if (!playerIn.isCreative()) {
                 held.damageItem(1, playerIn, (entity) -> {
                     entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                 });
             }

             ((ServerWorld) worldIn).summonEntity(lightning);
             if (CAConfig.COMMON.explosionType.get() == 0) {
                 ((ServerWorld) worldIn).createExplosion(playerIn, lookingAt.getHitVec().getX(), lookingAt.getHitVec().getY(), lookingAt.getHitVec().getZ(), CAConfig.COMMON.explosionSize.get(), CAConfig.COMMON.explosionFire.get(), Explosion.Mode.NONE);
             }
             if (CAConfig.COMMON.explosionType.get() == 1) {
                 ((ServerWorld) worldIn).createExplosion(playerIn, lookingAt.getHitVec().getX(), lookingAt.getHitVec().getY(), lookingAt.getHitVec().getZ(), CAConfig.COMMON.explosionSize.get(), CAConfig.COMMON.explosionFire.get(), Explosion.Mode.BREAK);
             }
             if (CAConfig.COMMON.explosionType.get() == 2) {
                 ((ServerWorld) worldIn).createExplosion(playerIn, lookingAt.getHitVec().getX(), lookingAt.getHitVec().getY(), lookingAt.getHitVec().getZ(), CAConfig.COMMON.explosionSize.get(), CAConfig.COMMON.explosionFire.get(), Explosion.Mode.DESTROY);
             }
         }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}