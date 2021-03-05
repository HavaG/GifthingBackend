package havag.gifthing.authentication

import havag.gifthing.authentication.payload.request.LoginRequest
import havag.gifthing.authentication.payload.request.SignupRequest
import havag.gifthing.authentication.payload.response.JwtResponse
import havag.gifthing.models.user.User

interface IAuthService {
	fun authenticateUser(loginRequest: LoginRequest?): JwtResponse
	fun registerUser(signUpRequest: SignupRequest?): User
}