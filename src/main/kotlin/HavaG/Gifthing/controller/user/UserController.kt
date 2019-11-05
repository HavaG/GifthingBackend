package HavaG.Gifthing.controller.user

import HavaG.Gifthing.models.User
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import java.util.*


@RestController
@RequestMapping("/user")
class UserController (val iUserService: IUserService){

    @GetMapping("/all")
    fun all(): MutableIterable<User> {
        return iUserService.getAllUser()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable(value = "id") id: Long): Optional<User> {
        return iUserService.getUserById(id)
    }

    @GetMapping("/email/{email}")
    fun findByEmail(@PathVariable(value = "email") email: String): Optional<User> {
        return iUserService.getUserByEmail(email)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long): Boolean{
        return iUserService.deleteUser(id)
    }

    @PutMapping("/update")
    fun update(@RequestBody editUser: User): Boolean {
        return iUserService.updateUser(editUser)
    }

    @PostMapping("/create")
    fun create(@RequestBody newUser: User): Boolean {
        return iUserService.addUser(newUser)
    }
}