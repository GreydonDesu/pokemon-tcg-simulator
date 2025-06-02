package de.thro.packsimulator.backend.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    private val logger: Logger = LoggerFactory.getLogger(WebConfig::class.java)

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        logger.info("Registering resource handler for /images/")
        // Map /images/ to the directory where images will be downloaded
        registry.addResourceHandler("/images/")
            .addResourceLocations("file:/app/images/")
    }
}
