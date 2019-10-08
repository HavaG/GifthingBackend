package HavaG.Gifthing

import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URISyntaxException
import org.springframework.web.bind.annotation.PostMapping





@RestController
@RequestMapping("/user")
class UserController(val userRepository: UserRepository) {

    @GetMapping("/all")
    fun all(): MutableIterable<User> = this.userRepository.findAll()

    @GetMapping("/{name}")
    fun byName(@PathVariable(value = "name") name: String): List<User> {
        return userRepository.findByName(name)
    }

    @PostMapping("/create")
    fun create(@RequestBody newUser: User): User {
        return userRepository.save(newUser)
    }
}