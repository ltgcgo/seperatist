package cc.ltgc.seperatist.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PotionItem.class)
public abstract class ObsidianModifier {
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();
        BlockState blockState = world.getBlockState(blockPos);
        if (context.getSide() != Direction.DOWN && blockState.isIn(BlockTags.CONVERTABLE_TO_MUD) && PotionUtil.getPotion(itemStack) == Potions.WATER) {
            world.playSound((PlayerEntity)null, blockPos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.PLAYERS, 1.0F, 1.0F);
            playerEntity.setStackInHand(context.getHand(), ItemUsage.exchangeStack(itemStack, playerEntity, new ItemStack(Items.GLASS_BOTTLE)));
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
            if (!world.isClient) {
                ServerWorld serverWorld = (ServerWorld)world;

                for(int i = 0; i < 5; ++i) {
                    serverWorld.spawnParticles(ParticleTypes.SPLASH, (double)blockPos.getX() + world.random.nextDouble(), (double)(blockPos.getY() + 1), (double)blockPos.getZ() + world.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
                }
            }

            world.playSound((PlayerEntity)null, blockPos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent((Entity)null, GameEvent.FLUID_PLACE, blockPos);
            world.setBlockState(blockPos, Blocks.MUD.getDefaultState());
            return ActionResult.success(world.isClient);
        } else if (blockState.getBlock().equals(Blocks.OBSIDIAN) && PotionUtil.getPotion(itemStack) == Potions.WATER) {
            world.playSound((PlayerEntity)null, blockPos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.PLAYERS, 1.0F, 1.0F);
            playerEntity.setStackInHand(context.getHand(), ItemUsage.exchangeStack(itemStack, playerEntity, new ItemStack(Items.GLASS_BOTTLE)));
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
            if (!world.isClient) {
                ServerWorld serverWorld = (ServerWorld)world;

                for(int i = 0; i < 5; ++i) {
                    serverWorld.spawnParticles(ParticleTypes.SPLASH, (double)blockPos.getX() + world.random.nextDouble(), (double)(blockPos.getY() + 1), (double)blockPos.getZ() + world.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
                }
            }

            world.playSound((PlayerEntity)null, blockPos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent((Entity)null, GameEvent.FLUID_PLACE, blockPos);
            world.setBlockState(blockPos, Blocks.CRYING_OBSIDIAN.getDefaultState());
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }
    /*
    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(L/net/minecraft/util/math/BlockPos;)L/net/minecraft/block/BlockState;", shift = At.Shift.AFTER))
    public void injectedCryingObsidian(ItemUsageContext context, CallbackInfoReturnable info) {
        Seperatist.LOGGER.info("Hello World");
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isIn(BlockTags.CONVERTABLE_TO_MUD) && PotionUtil.getPotion(itemStack) == Potions.WATER) {
            world.playSound((PlayerEntity)null, blockPos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.PLAYERS, 1.0F, 1.0F);
            playerEntity.setStackInHand(context.getHand(), ItemUsage.exchangeStack(itemStack, playerEntity, new ItemStack(Items.GLASS_BOTTLE)));
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
            if (!world.isClient) {
                ServerWorld serverWorld = (ServerWorld)world;

                for(int i = 0; i < 5; ++i) {
                    serverWorld.spawnParticles(ParticleTypes.SPLASH, (double)blockPos.getX() + world.random.nextDouble(), (double)(blockPos.getY() + 1), (double)blockPos.getZ() + world.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
                }
            }

            world.playSound((PlayerEntity)null, blockPos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent((Entity)null, GameEvent.FLUID_PLACE, blockPos);
            world.setBlockState(blockPos, Blocks.CRYING_OBSIDIAN.getDefaultState());
            // return ActionResult.success(world.isClient);
        }
    }
    */
    /*
    @Inject(method = "useOnBlock", at = @At("HEAD"))
    protected void injectedCryingObsidian(ItemUsageContext context, CallbackInfoReturnable info) {
        context.getPlayer().sendMessage(Text.literal("Hello World"));
    }
    */
}
