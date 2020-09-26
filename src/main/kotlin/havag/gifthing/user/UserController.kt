package havag.gifthing.user

import havag.gifthing.models.user.dto.UserRequest
import havag.gifthing.models.user.dto.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController (val iUserService: IUserService){

    //TODO: ennek van értelme?
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    fun all(): ResponseEntity<MutableIterable<UserResponse>> {
        return ResponseEntity(iUserService.findAll(), HttpStatus.OK)
    }

    //TODO: ezt nem tudom, hogy ki csinálhatja meg
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    fun findById(@PathVariable(value = "id") id: Long): ResponseEntity<UserResponse> {
        val tmp = iUserService.findById(id)
        return if(tmp != null) {
            ResponseEntity(tmp, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    //TODO: ezt be lehet szüntetni (elvileg)
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('USER')")
    fun findByEmail(@PathVariable(value = "email") email: String): ResponseEntity<UserResponse> {
        val tmp = iUserService.findByEmail(email)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('USER')")
    fun findByUsername(@PathVariable(value = "username") username: String): ResponseEntity<UserResponse> {
        val tmp = iUserService.findByUsername(username)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/usernames")
    @PreAuthorize("hasRole('USER')")
    fun getUsernames(): ArrayList<String> {
        return iUserService.getUsernames()
    }

    //TODO: ez tuti admin funkció
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteById(@PathVariable(value = "id") id: Long) : ResponseEntity<Boolean>{
        return if(iUserService.delete(id)) {
            ResponseEntity(true, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun update(@RequestBody editUser: UserRequest): ResponseEntity<UserResponse> {
        val tmp = iUserService.update(editUser)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    /*
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    fun create(@RequestBody newUser: UserRequest): ResponseEntity<UserResponse> {
        val tmp = iUserService.create(newUser)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.CONFLICT)
    } */

}