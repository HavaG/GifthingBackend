package HavaG.Gifthing

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/persons")
class PersonController(val personRepository: PersonRepository) {

    @GetMapping("/all")
    fun all(): MutableIterable<Person> = this.personRepository.findAll()

    @GetMapping("/{name}")
    fun byName(@PathVariable(value = "name") name : String) : List<Person> {
        return personRepository.findByName(name)
    }
}