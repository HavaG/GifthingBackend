package HavaG.Gifthing

import HavaG.Gifthing.controller.Gift.GiftRepository
import HavaG.Gifthing.controller.User.UserRepository
import HavaG.Gifthing.models.Gift
import HavaG.Gifthing.models.User
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DbSeeder(val userRepository: UserRepository, val giftRepository: GiftRepository) : CommandLineRunner {
    override fun run(vararg p0: String?) {
        this.userRepository.deleteAll()
        this.giftRepository.deleteAll()

        val p1 = User("havag@gmail.com")
        val p2 = User("nemisemail@nincsilyen.hu")

        val g1 = Gift("megkapnam")

        p2.reserveGift(g1)
        p1.addGift(g1)

        val users = mutableListOf<User>()
        users.add(p1)
        users.add(p2)


        val gifts = mutableListOf<Gift>()
        gifts.add(g1)

        this.userRepository.saveAll(users)
        this.giftRepository.saveAll(gifts)
        println(" -- Database has been initialized")
    }
}