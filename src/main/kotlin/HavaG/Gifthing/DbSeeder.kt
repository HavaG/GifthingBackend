package HavaG.Gifthing

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DbSeeder(val personRepository: PersonRepository) : CommandLineRunner {
    override fun run(vararg p0: String?) {
        this.personRepository.deleteAll()

        val p1 = Person("Gabor", "jelszo")
        val p2 = Person("Enci", "asd")

        val persons = mutableListOf<Person>()
        persons.add(p1)
        persons.add(p2)

        this.personRepository.saveAll(persons)
        println(" -- Database has been initialized")
    }
}