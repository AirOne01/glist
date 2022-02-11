package glist

import glist.model.MorphModel
import net.fabricmc.fabric.api.client.model.ModelProviderContext
import net.fabricmc.fabric.api.client.model.ModelProviderException
import net.fabricmc.fabric.api.client.model.ModelResourceProvider
import net.minecraft.client.render.model.UnbakedModel
import net.minecraft.util.Identifier
import kotlin.jvm.Throws

class ModelProvider : ModelResourceProvider {
    private val morphIdentifier: Identifier = Identifier("glist:block/morph")

    @Override
    @Throws(ModelProviderException::class)
    override fun loadModelResource(identifier: Identifier, modelProviderContext: ModelProviderContext) : UnbakedModel? {
        return if (identifier == morphIdentifier) {
            MorphModel()
        } else {
            null
        }
    }
}