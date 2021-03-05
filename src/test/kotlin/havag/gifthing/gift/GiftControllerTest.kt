package havag.gifthing.gift

import havag.gifthing.AbstractTest
import havag.gifthing.Utils
import havag.gifthing.models.gift.dto.GiftRequest
import havag.gifthing.models.gift.dto.GiftResponse
import havag.gifthing.models.user.dto.UserResponse
import havag.gifthing.user.UserService
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithUserDetails


internal class GiftControllerTest : AbstractTest() {

	val utils = Utils()
	val logger: Logger = LoggerFactory.getLogger(GiftControllerTest::class.java)

	@Autowired
	lateinit var userService: UserService

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun createGift() {
		logger.info("createGift START")
		val uri = "/api/gift/create"
		val me = userService.me()
		val giftRequest = GiftRequest(0, "Negyedik ajandek", null, null, null, me!!.id)
		val inputJson = super.mapToJson(giftRequest)
		val mvcResult = super.mvcPerformPost(uri, inputJson)
		assertEquals(200, mvcResult.response.status)
		val content = mvcResult.response.contentAsString
		val response: GiftResponse = super.mapFromJson(content, GiftResponse::class.java)
		response.id = 0
		response.lastUpdate = 0
		response.owner!!.lastUpdate = 0
		val resultJson = super.mapToJson(response)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/gift/json_files/gift-response.json")
		assertEquals(expected, resultJson)
		logger.info("createGift END")
	}


	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun findGiftById() {
		logger.info("findGiftById START")
		val uri = "/api/gift/8"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(200, mvcResult.response.status)
		val content = mvcResult.response.contentAsString
		val response: GiftResponse = super.mapFromJson(content, GiftResponse::class.java)
		response.lastUpdate = 0
		response.owner!!.lastUpdate = 0
		val resultJson = super.mapToJson(response)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/gift/json_files/findById.json")
		assertEquals(expected, resultJson)
		logger.info("findGiftById END")
	}


	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun reserveValidGift() {
		logger.info("reserveValidGift START")
		val uri = "/api/gift/reserve/8"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(200, mvcResult.response.status)
		val content = mvcResult.response.contentAsString
		val response: GiftResponse = super.mapFromJson(content, GiftResponse::class.java)
		response.lastUpdate = 0
		response.owner!!.lastUpdate = 0
		response.reservedBy!!.lastUpdate = 0
		val resultJson = super.mapToJson(response)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/gift/json_files/reserve.json")
		assertEquals(expected, resultJson)
		logger.info("reserveValidGift END")
	}

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun reserveAlreadyReservedGift() {
		logger.info("reserveAlreadyReservedGift START")
		val uri = "/api/gift/reserve/13"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(409, mvcResult.response.status)
		logger.info("reserveAlreadyReservedGift END")
	}

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun reserveOwnedGift() {
		logger.info("reserveOwnedGift START")
		val uri = "/api/gift/reserve/8"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(409, mvcResult.response.status)
		logger.info("reserveOwnedGift END")
	}

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun reserveDeletedGift() {
		logger.info("reserveDeletedGift START")
		val uri = "/api/gift/reserve/10000"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(404, mvcResult.response.status)
		logger.info("reserveDeletedGift END")
	}

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun releaseValidGift() {
		logger.info("releaseValidGift START")
		val uri = "/api/gift/release/8"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(200, mvcResult.response.status)
		val content = mvcResult.response.contentAsString
		val response: GiftResponse = super.mapFromJson(content, GiftResponse::class.java)
		response.lastUpdate = 0
		response.owner!!.lastUpdate = 0
		val resultJson = super.mapToJson(response)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/gift/json_files/release.json")
		assertEquals(expected, resultJson)
		logger.info("releaseValidGift END")
	}


	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun deleteGift() {
		logger.info("deleteGift START")
		val uri = "/api/gift/delete/11"
		val mvcResult = super.mvcPerformDelete(uri)
		assertEquals(200, mvcResult.response.status)
		val content = mvcResult.response.contentAsString
		val response: Boolean = super.mapFromJson(content, Boolean::class.java)
		assertTrue(response)
		logger.info("deleteGift END")
	}

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun deleteNotExistingGift() {
		logger.info("deleteNotExistingGift START")
		val uri = "/api/gift/delete/10000"
		val mvcResult = super.mvcPerformDelete(uri)
		assertEquals(404, mvcResult.response.status)
		logger.info("deleteNotExistingGift END")
	}

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun deleteNotOwnedGift() {
		logger.info("deleteNotOwnedGift START")
		val uri = "/api/gift/delete/11"
		val mvcResult = super.mvcPerformDelete(uri)
		assertEquals(401, mvcResult.response.status)
		logger.info("deleteNotOwnedGift END")
	}
}