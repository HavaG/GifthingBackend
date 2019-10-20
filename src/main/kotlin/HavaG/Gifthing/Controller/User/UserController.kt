package HavaG.Gifthing.Controller.User

import HavaG.Gifthing.Models.User
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

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long){
        iUserService.deleteUser(id)
    }

    @PutMapping("/modify")
    fun update(@RequestBody editUser: User): Boolean {
        return iUserService.updateUser(editUser)
    }

    @PostMapping("/create")
    fun create(@RequestBody newUser: User): Boolean {
        return iUserService.addUser(newUser)
    }
}