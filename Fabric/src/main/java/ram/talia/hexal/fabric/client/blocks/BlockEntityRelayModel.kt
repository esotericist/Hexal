package ram.talia.hexal.fabric.client.blocks

import net.minecraft.resources.ResourceLocation
import ram.talia.hexal.api.HexalAPI
import ram.talia.hexal.common.blocks.entity.BlockEntityRelay
import software.bernie.geckolib3.model.AnimatedGeoModel

class BlockEntityRelayModel : AnimatedGeoModel<BlockEntityRelay>() {
    override fun getModelResource(relay: BlockEntityRelay): ResourceLocation = HexalAPI.modLoc("geo/relay.geo.json")

    override fun getTextureResource(relay: BlockEntityRelay): ResourceLocation = HexalAPI.modLoc("textures/blocks/relay.png")

    override fun getAnimationResource(relay: BlockEntityRelay): ResourceLocation = HexalAPI.modLoc("animations/relay.animation.json")
}