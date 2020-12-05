package havag.gifthing.auth

import havag.gifthing.AbstractTest
import havag.gifthing.Utils
import havag.gifthing.authentication.payload.request.LoginRequest
import havag.gifthing.authentication.payload.request.SignupRequest
import havag.gifthing.authentication.payload.request.SignupResponse
import havag.gifthing.authentication.payload.response.JwtResponse
import havag.gifthing.user.UserService
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithUserDetails


class AuthControllerTest : AbstractTest() {

	private val utils = Utils()
	private val logger: Logger = LoggerFactory.getLogger(AuthControllerTest::class.java)

	@Autowired
	lateinit var userService: UserService

	@Before
	public override fun setUp() {
		logger.info("before START")
		super.setUp()
		logger.info("before END")
	}

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun deleteTestUser() {
		logger.info("delete START")
		val me = userService.me()
		val success = userService.delete(me!!.id)
		assertEquals(true, success)
		logger.info("delete END")
	}

	@Test
	@Throws(Exception::class)
	fun signupWithValidData() {
		logger.info("signupWithValidData START")
		val uri = "/api/auth/signup"
		val signupRequest = SignupRequest("example", "example@gmail.com", "example")
		val inputJson = super.mapToJson(signupRequest)
		val mvcResult = super.mvcPerform(uri, inputJson)
		assertEquals(200, mvcResult.response.status)
		val content = mvcResult.response.contentAsString
		val response: SignupResponse = super.mapFromJson(content, SignupResponse::class.java)
		response.user?.id = 0
		response.user?.lastUpdate = 0
		val resultJson = super.mapToJson(response)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/auth/json_files/signupWithValidData.json")
		assertEquals(expected, resultJson)
		logger.info("signupWithValidData END")
	}

	@Test
	@Throws(Exception::class)
	fun signupWithTakenUsername() {
		logger.info("signupWithTakenUsername.json START")
		val uri = "/api/auth/signup"
		val signupRequest = SignupRequest("example", "valid@gmail.com", "example")
		val inputJson = super.mapToJson(signupRequest)
		val mvcResult = super.mvcPerform(uri, inputJson)
		assertEquals(400, mvcResult.response.status)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/auth/json_files/signupWithTakenUsername.json")
		assertEquals(expected, mvcResult.response.contentAsString)
		logger.info("signupWithTakenUsername.json END")
	}

	@Test
	@Throws(Exception::class)
	fun signupWithTakenEmail() {
		logger.info("signupWithTakenEmail START")
		val uri = "/api/auth/signup"
		val signupRequest = SignupRequest("valid", "example@gmail.com", "example")
		val inputJson = super.mapToJson(signupRequest)
		val mvcResult = super.mvcPerform(uri, inputJson)
		assertEquals(400, mvcResult.response.status)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/auth/json_files/signupWithTakenEmail.json")
		assertEquals(expected, mvcResult.response.contentAsString)
		logger.info("signupWithTakenEmail END")
	}

	@Test
	@Throws(Exception::class)
	fun loginWithValidData() {
		logger.info("loginWithValidData START")
		val uri = "/api/auth/login"
		val loginResponse = LoginRequest("example", "example")
		val inputJson = super.mapToJson(loginResponse)
		val mvcResult = super.mvcPerform(uri, inputJson)
		assertEquals(200, mvcResult.response.status)
		val content = mvcResult.response.contentAsString
		val response = super.mapFromJson(content, JwtResponse::class.java)
		response.accessToken = "token"
		response.id = 0
		val resultJson = super.mapToJson(response)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/auth/json_files/loginWithValidData.json")
		assertEquals(expected, resultJson)
		logger.info("loginWithValidData END")
	}

	@Test
	@Throws(Exception::class)
	fun loginWithInvalidUsername() {
		logger.info("loginWithInvalidUsername START")
		val uri = "/api/auth/login"
		val loginResponse = LoginRequest("invalid", "example")
		val inputJson = super.mapToJson(loginResponse)
		val mvcResult = super.mvcPerform(uri, inputJson)
		assertEquals(400, mvcResult.response.status)
		val resultJson = super.mapToJson(mvcResult.response.contentAsString)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/auth/json_files/loginWithInvalidData.json")
		assertEquals(expected, resultJson)
		logger.info("loginWithInvalidUsername END")
	}

	@Test
	@Throws(Exception::class)
	fun loginWithInvalidPassword() {
		logger.info("loginWithInvalidPassword START")
		val uri = "/api/auth/login"
		val loginResponse = LoginRequest("example", "invalid")
		val inputJson = super.mapToJson(loginResponse)
		val mvcResult = super.mvcPerform(uri, inputJson)
		assertEquals(400, mvcResult.response.status)
		val resultJson = super.mapToJson(mvcResult.response.contentAsString)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/auth/json_files/loginWithInvalidData.json")
		assertEquals(expected, resultJson)
		logger.info("loginWithInvalidPassword END")
	}
}