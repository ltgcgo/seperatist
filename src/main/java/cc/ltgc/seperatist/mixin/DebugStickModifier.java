package cc.ltgc.seperatist.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DebugStickItem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DebugStickItem.class)
public abstract class DebugStickModifier {
    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isCreativeLevelTwoOp()Z", opcode = Opcodes.GETFIELD))
    private boolean injectGameMode (PlayerEntity player) {
        return true;
    }
}
