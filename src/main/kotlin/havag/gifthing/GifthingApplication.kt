package havag.gifthing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication//(scanBasePackages= ["havag.gifthing.security", "havag.gifthing.security.services"])
class GifthingApplication

fun main(args: Array<String>) {
	runApplication<GifthingApplication>(*args) {
		addInitializers(Startup())
	}
}
