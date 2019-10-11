package HavaG.Gifthing

import HavaG.Gifthing.controller.UserRepository
import HavaG.Gifthing.models.User
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DbSeeder(val UserRepository: UserRepository) : CommandLineRunner {
    override fun run(vararg p0: String?) {
        this.UserRepository.deleteAll()

        val p1 = User("Gabor", "jelszo")
        val p2 = User("Enci", "asd")

        val users = mutableListOf<User>()
        users.add(p1)
        users.add(p2)

        this.UserRepository.saveAll(users)
        println(" -- Database has been initialized")
    }
}