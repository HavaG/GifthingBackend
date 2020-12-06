package havag.gifthing.user

import havag.gifthing.AbstractTest
import havag.gifthing.Utils
import havag.gifthing.models.user.dto.UserResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.test.context.support.WithUserDetails

class UserControllerTest : AbstractTest() {

	private val utils = Utils()
	private val logger: Logger = LoggerFactory.getLogger(UserControllerTest::class.java)

	@Before
	public override fun setUp() {
		logger.info("before START")
		super.setUp()
		logger.info("before END")
	}

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun findById() {
		logger.info("findById START")
		val uri = "/api/user/2"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(200, mvcResult.response.status)
		val content = mvcResult.response.contentAsString
		val response: UserResponse = super.mapFromJson(content, UserResponse::class.java)
		response.lastUpdate = 0
		val resultJson = super.mapToJson(response)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/user/json_files/findBy.json")
		assertEquals(expected, resultJson)
		logger.info("findById END")
	}

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun findByIdNotFound() {
		logger.info("findByIdNotFound START")
		val uri = "/api/user/1000000"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(404, mvcResult.response.status)
		logger.info("findByIdNotFound END")
	}

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun findByUsername() {
		logger.info("findByUsername START")
		val uri = "/api/user/username/default"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(200, mvcResult.response.status)
		val content = mvcResult.response.contentAsString
		val response: UserResponse = super.mapFromJson(content, UserResponse::class.java)
		response.lastUpdate = 0
		val resultJson = super.mapToJson(response)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/user/json_files/findBy.json")
		assertEquals(expected, resultJson)
		logger.info("findByUsername END")
	}

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun findByUsernameNotFound() {
		logger.info("findByUsernameNotFound START")
		val uri = "/api/user/username/not-exists"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(404, mvcResult.response.status)
		logger.info("findByUsernameNotFound END")
	}

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun getAllUsernames() {
		logger.info("getAllUsernames START")
		val uri = "/api/user/usernames"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(200, mvcResult.response.status)
		val content = mvcResult.response.contentAsString
		val response: Array<String> = super.mapFromJson(content, Array<String>::class.java)
		assertTrue(response.isNotEmpty())
		logger.info("getAllUsernames END")
	}

	/*
	@Test
	@Throws(Exception::class)
	@WithUserDetails("example")
	fun deleteTestUser() {
		logger.info("delete START")
		val me = userService.me()
		val success = userService.delete(me!!.id)
		assertEquals(true, success)
		logger.info("getUserList END")
	}
	*/
}