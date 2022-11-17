package ram.talia.hexal.common.casting.actions.spells.link

import at.petrak.hexcasting.api.misc.ManaConstants
import at.petrak.hexcasting.api.spell.*
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.mishaps.MishapInvalidIota
import net.minecraft.network.chat.TranslatableComponent
import ram.talia.hexal.api.linkable.ILinkable
import ram.talia.hexal.api.spell.casting.IMixinCastingContext
import ram.talia.hexal.xplat.IXplatAbstractions
import kotlin.math.max

object OpUnlink : SpellOperator {
	const val UNLINK_COST = 2 * ManaConstants.DUST_UNIT

	override val argc = 1

	@Suppress("CAST_NEVER_SUCCEEDS")
	override fun execute(args: List<SpellDatum<*>>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>> {
		val mCast = ctx as? IMixinCastingContext

		val linkThis: ILinkable<*> = when (val wisp = mCast?.wisp) {
			null -> IXplatAbstractions.INSTANCE.getLinkstore(ctx.caster)
			else -> wisp
		}

		val otherIndex = max(args.getChecked<Double>(0, OpSendIota.argc).toInt(), 0)

		if (otherIndex >= linkThis.numLinked())
			throw MishapInvalidIota(
				SpellDatum.make(otherIndex),
				0,
				TranslatableComponent("hexcasting.mishap.invalid_value.int.between", 0, linkThis.numLinked())
			)

		val other = linkThis.getLinked(otherIndex)

		return Triple(
			Spell(linkThis, other),
			UNLINK_COST,
			listOf(ParticleSpray.burst(other.getPos(), 1.5))
		)
	}

	private data class Spell(val linkThis: ILinkable<*>, val other: ILinkable<*>) : RenderedSpell {
		override fun cast(ctx: CastingContext) = linkThis.unlink(other)
	}
}