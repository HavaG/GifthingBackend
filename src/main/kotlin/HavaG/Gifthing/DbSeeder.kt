package HavaG.Gifthing

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DbSeeder(val userRepository: UserRepository) : CommandLineRunner {
    override fun run(vararg p0: String?) {
        this.userRepository.deleteAll()

        val p1 = User("Gabor", "jelszo")
        val p2 = User("Enci", "asd")

        val users = mutableListOf<User>()
        users.add(p1)
        users.add(p2)

        this.userRepository.saveAll(users)
        println(" -- Database has been initialized")
    }
}