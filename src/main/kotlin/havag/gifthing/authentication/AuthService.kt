package havag.gifthing.authentication

import havag.gifthing.authentication.payload.request.LoginRequest
import havag.gifthing.authentication.payload.request.RolesRequest
import havag.gifthing.authentication.payload.request.SignupRequest
import havag.gifthing.authentication.payload.response.JwtResponse
import havag.gifthing.models.ERole
import havag.gifthing.models.Role
import havag.gifthing.models.user.User
import havag.gifthing.repositories.RoleRepository
import havag.gifthing.repositories.UserRepository
import havag.gifthing.security.jwt.JwtUtils
import havag.gifthing.security.services.UserDetailsImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.function.Consumer
import java.util.stream.Collectors

@Service
class AuthService {
	@Autowired
	var authenticationManager: AuthenticationManager? = null

	@Autowired
	var jwtUtils: JwtUtils? = null

	@Autowired
	var userRepository: UserRepository? = null

	@Autowired
	var roleRepository: RoleRepository? = null

	@Autowired
	var encoder: PasswordEncoder? = null


	fun authenticateUser(loginRequest: LoginRequest?): JwtResponse {
		val authentication = authenticationManager!!.authenticate(
			UsernamePasswordAuthenticationToken(loginRequest!!.username, loginRequest.password)
		)
		SecurityContextHolder.getContext().authentication = authentication
		val jwt = jwtUtils!!.generateJwtToken(authentication)
		val userDetails = authentication.principal as UserDetailsImpl
		val roles = userDetails.authorities.stream()
			.map { item: GrantedAuthority -> item.authority }
			.collect(Collectors.toList())

		return JwtResponse(
			jwt,
			userDetails.id,
			userDetails.username,
			userDetails.email,
			roles
		)
	}

	fun registerUser(signUpRequest: SignupRequest?) {
		if (userRepository!!.existsByUsername(signUpRequest!!.username)!!) {
			throw IllegalArgumentException("Error: Username is already taken!")
		}
		if (userRepository!!.existsByEmail(signUpRequest.email)!!) {
			throw IllegalArgumentException("Error: Email is already in use!")
		}

		val user = User(
			signUpRequest.username!!,
			signUpRequest.email!!,
			encoder!!.encode(signUpRequest.password)
		)
		val roles: MutableSet<Role> = HashSet()
		val userRole: Role = roleRepository!!.findByName(ERole.ROLE_USER)
			.orElseThrow { RuntimeException("Error: User role is not found.") }
		roles.add(userRole)
		user.setRoles(roles)
		userRepository!!.save(user)
		return
	}

	fun setAdmin(id: Long, rolesRequest: RolesRequest?) {
		val user = userRepository!!.findById(id)
		if (user.isPresent) {
			val realUser = user.get()

			val strRoles: Set<String> = rolesRequest!!.roles!!
			val newRoles: MutableSet<Role> = HashSet()
			strRoles.forEach(Consumer { role: String? ->
				when (role) {
					"admin" -> {
						val adminRole: Role = roleRepository!!.findByName(ERole.ROLE_ADMIN)
							.orElseThrow { RuntimeException("Error: Role is not found.") }
						newRoles.add(adminRole)
					}
					"mod" -> {
						val modRole: Role = roleRepository!!.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow { RuntimeException("Error: Role is not found.") }
						newRoles.add(modRole)
					}
					"user" -> {
						val userRole: Role = roleRepository!!.findByName(ERole.ROLE_USER)
							.orElseThrow { RuntimeException("Error: Role is not found.") }
						newRoles.add(userRole)
					}
					else -> {
						throw RuntimeException("Error: Role is not found.")
					}
				}
			})
			realUser.setRoles(newRoles)
			userRepository!!.save(realUser)
			return
		} else {
			throw IllegalArgumentException("Error: User not exists!")
		}
	}
}