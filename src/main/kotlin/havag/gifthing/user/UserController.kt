package havag.gifthing.user

import havag.gifthing.models.user.dto.UserRequest
import havag.gifthing.models.user.dto.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(val iUserService: IUserService) {

	//TODO: ennek van értelme?
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	fun all(): ResponseEntity<MutableIterable<UserResponse>> {
		return ResponseEntity(iUserService.findAll(), HttpStatus.OK)
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	fun findById(@PathVariable(value = "id") id: Long): ResponseEntity<UserResponse> {
		val user = iUserService.findById(id)
		return user?.let { ResponseEntity(user, HttpStatus.OK) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
	}

	//TODO: ezt be lehet szüntetni (elvileg)
	@GetMapping("/email/{email}")
	@PreAuthorize("hasRole('USER')")
	fun findByEmail(@PathVariable(value = "email") email: String): ResponseEntity<UserResponse> {
		val user = iUserService.findByEmail(email)
		return user?.let { ResponseEntity(user, HttpStatus.OK) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
	}

	@GetMapping("/username/{username}")
	@PreAuthorize("hasRole('USER')")
	fun findByUsername(@PathVariable(value = "username") username: String): ResponseEntity<UserResponse> {
		val user = iUserService.findByUsername(username)
		return user?.let { ResponseEntity(user, HttpStatus.OK) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
	}

	@GetMapping("/usernames")
	@PreAuthorize("hasRole('USER')")
	fun getUsernames(): ResponseEntity<MutableIterable<String>> {
		return ResponseEntity(iUserService.getUsernames(), HttpStatus.OK)
	}

	//TODO: ez tuti admin funkció (delete my user?)
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	fun deleteById(@PathVariable(value = "id") id: Long): ResponseEntity<Boolean> {
		val status = iUserService.delete(id)
		val success = status.is2xxSuccessful
		return ResponseEntity(success, status)
	}

	@PutMapping("/update")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	fun update(@RequestBody editUser: UserRequest): ResponseEntity<UserResponse> {
		val user = iUserService.update(editUser)
		return user?.let { ResponseEntity(user, HttpStatus.OK) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
	}
}