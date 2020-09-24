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

    @GetMapping("/all")
    fun all(): ResponseEntity<MutableIterable<UserResponse>> {
        return ResponseEntity(iUserService.findAll(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun findById(@PathVariable(value = "id") id: Long): ResponseEntity<UserResponse> {
        val tmp = iUserService.findById(id)
        return if(tmp != null) {
            ResponseEntity(tmp, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/email/{email}")
    @ResponseBody
    fun findByEmail(@PathVariable(value = "email") email: String): ResponseEntity<UserResponse> {
        val tmp = iUserService.findByEmail(email)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long) : ResponseEntity<Boolean>{
        return if(iUserService.delete(id)) {
            ResponseEntity(true, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/update")
    fun update(@RequestBody editUser: UserRequest): ResponseEntity<UserResponse> {
        val tmp = iUserService.update(editUser)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/create")
    fun create(@RequestBody newUser: UserRequest): ResponseEntity<UserResponse> {
        val tmp = iUserService.create(newUser)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.CONFLICT)
    }
}