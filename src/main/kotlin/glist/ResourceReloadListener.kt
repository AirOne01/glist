package glist

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import java.io.InputStream

class ResourceReloadListener : SimpleSynchronousResourceReloadListener {
    @Override
    override fun getFabricId(): Identifier {
        return Identifier("glist", "resources")
    }

    @Override
    override fun reload(manager: ResourceManager?) {
        // Clear caches here

        manager?.findResources("my_resource_folder") { path -> path.endsWith(".json") }?.forEach { identifier ->
            try {
                val stream: InputStream = manager.getResource(identifier).inputStream
                // Consume the stream however you want, medium, rare, or well done.
            } catch (e: Exception) {
                println("Error occurred while loading resource \"$identifier\" : ${e.message}")
            }
        }
    }
}