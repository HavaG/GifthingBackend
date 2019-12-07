package HavaG.Gifthing.controller.user

import HavaG.Gifthing.models.user.User
import HavaG.Gifthing.models.user.dto.UserRequest
import HavaG.Gifthing.models.user.dto.UserResponse
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import java.util.*


@RestController
@RequestMapping("/user")
class UserController (val iUserService: IUserService){

    @GetMapping("/all")
    fun all(): MutableIterable<UserResponse> {
        return iUserService.getAllUser()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable(value = "id") id: Long): UserResponse? {
        return iUserService.getUserById(id)
    }

    @GetMapping("/email/{email}")
    @ResponseBody
    fun findByEmail(@PathVariable(value = "email") email: String): ResponseEntity<UserResponse?> {
        val tmp: UserResponse? = iUserService.getUserByEmail(email)
        if(tmp != null)
            return ResponseEntity(tmp, HttpStatus.OK)
        else
            //TODO: ez így full szar HttpStatus.FAILED
            return ResponseEntity(UserResponse("a","a", 0), HttpStatus.CREATED)

    }

    @DeleteMapping("/delete/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long): Boolean{
        return iUserService.deleteUser(id)
    }

    @PutMapping("/update")
    fun update(@RequestBody editUser: UserRequest): UserResponse? {
        return iUserService.updateUser(editUser)
    }

    @PostMapping("/create")
    fun create(@RequestBody newUser: UserRequest): UserResponse? {
        return iUserService.createUser(newUser)
    }
}