package havag.gifthing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GifthingApplication

fun main(args: Array<String>) {
	runApplication<GifthingApplication>(*args)
}
