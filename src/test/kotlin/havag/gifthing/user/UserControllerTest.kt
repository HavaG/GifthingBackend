package havag.gifthing.user

import havag.gifthing.AbstractTest
import havag.gifthing.Utils
import havag.gifthing.authentication.payload.request.SignupRequest
import havag.gifthing.authentication.payload.request.SignupResponse
import havag.gifthing.models.user.dto.UserResponse
import havag.gifthing.repositories.UserRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct


class UserControllerTest : AbstractTest() {

	private val utils = Utils()
	private val logger: Logger = LoggerFactory.getLogger(UserControllerTest::class.java)

	@Autowired
	lateinit var userService: UserService

	@Before
	public override fun setUp() {
		logger.info("before START")
		super.setUp()
		//signup()
		logger.info("before END")
	}

	@After
	fun clean() {
		logger.info("after START")
		//deleteTestUser()
		logger.info("after END")
	}

	@Test
	@Throws(Exception::class)
	fun signup() {
		logger.info("signup START")
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
		logger.info("signup END")
	}

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


/*
	fun findById(@PathVariable(value = "id") id: Long): ResponseEntity<UserResponse>

	fun findByUsername(@PathVariable(value = "username") username: String)

	fun getUsernames(): ResponseEntity<MutableIterable<String>>
 */



/*
	@Test
	@Throws(Exception::class)
	fun createProduct() {
		val uri = "/products"
		val product = Product()
		product.setId("3")
		product.setName("Ginger")
		val inputJson = super.mapToJson(product)
		val mvcResult = mvc!!.perform(
			MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)
		).andReturn()
		val status = mvcResult.response.status
		Assert.assertEquals(201, status.toLong())
		val content = mvcResult.response.contentAsString
		Assert.assertEquals(content, "Product is created successfully")
	}

	@Test
	@Throws(Exception::class)
	fun updateProduct() {
		val uri = "/products/2"
		val product = Product()
		product.setName("Lemon")
		val inputJson = super.mapToJson(product)
		val mvcResult = mvc!!.perform(
			MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)
		).andReturn()
		val status = mvcResult.response.status
		Assert.assertEquals(200, status.toLong())
		val content = mvcResult.response.contentAsString
		Assert.assertEquals(content, "Product is updated successsfully")
	}

	@Test
	@Throws(Exception::class)
	fun deleteProduct() {
		val uri = "/products/2"
		val mvcResult = mvc!!.perform(MockMvcRequestBuilders.delete(uri)).andReturn()
		val status = mvcResult.response.status
		Assert.assertEquals(200, status.toLong())
		val content = mvcResult.response.contentAsString
		Assert.assertEquals(content, "Product is deleted successsfully")
	}

 */
}