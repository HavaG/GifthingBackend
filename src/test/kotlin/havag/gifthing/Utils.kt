package havag.gifthing

import java.io.File

class Utils {
	fun readFileDirectlyAsText(fileName: String): String {
		val text = File(fileName).readText(Charsets.UTF_8)
		return text.filter { it.toByte().toInt() != 13 && it.toByte().toInt() != 10 && it.toByte().toInt() != 9 }
		//a.forEach { print("" + it.toByte() + " ") }
	}
}