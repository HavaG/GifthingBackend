package havag.gifthing.team

import havag.gifthing.AbstractTest
import havag.gifthing.Utils
import havag.gifthing.models.team.dto.TeamRequest
import havag.gifthing.models.team.dto.TeamResponse
import havag.gifthing.models.user.dto.UserResponse
import havag.gifthing.user.UserService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithUserDetails
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class TeamControllerTest : AbstractTest() {

	private val utils = Utils()
	private val logger: Logger = LoggerFactory.getLogger(TeamControllerTest::class.java)

	@Autowired
	lateinit var userService: UserService

	@Before
	public override fun setUp() {
		logger.info("before START")
		super.setUp()
		logger.info("before END")
	}

	//not working
	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun createTeam() {
		logger.info("createTeam START")
		val uri = "/api/team/create"
		val me = userService.me()
		val teamRequest = TeamRequest("Csalad", null, me!!.id, mutableListOf(2, 10, 12))
		val inputJson = super.mapToJson(teamRequest)
		val mvcResult = super.mvcPerformPost(uri, inputJson)
		assertEquals(200, mvcResult.response.status)
		val content = mvcResult.response.contentAsString
		val response: TeamResponse = super.mapFromJson(content, TeamResponse::class.java)
		response.id = 0
		response.lastUpdate = 0
		response.getMembers().forEach { user ->
			user.lastUpdate = 0
			user.getGifts().forEach {
					gift -> gift.lastUpdate = 0
			}
		}
		val resultJson = super.mapToJson(response)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/team/json_files/team-response.json")
		assertEquals(expected, resultJson)
		logger.info("createTeam END")
	}

	//not working
	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun findById() {
		logger.info("findById START")
		val uri = "/api/team/17"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(200, mvcResult.response.status)
		val content = mvcResult.response.contentAsString
		val response: TeamResponse = super.mapFromJson(content, TeamResponse::class.java)
		val resultJson = super.mapToJson(response)
		val expected = utils.readFileDirectlyAsText("src/test/kotlin/havag/gifthing/user/json_files/team-response.json")
		assertEquals(expected, resultJson)
		logger.info("findById END")
	}

	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun findByIdNotFound() {
		logger.info("findByIdNotFound START")
		val uri = "/api/team/1000000"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(404, mvcResult.response.status)
		logger.info("findByIdNotFound END")
	}

	//not working
	@Test
	@WithUserDetails("example")
	@Throws(Exception::class)
	fun getMyTeams() {
		logger.info("getMyTeams START")
		val uri = "/api/team/my-teams"
		val mvcResult = super.mvcPerformGet(uri)
		assertEquals(200, mvcResult.response.status)
		val content = mvcResult.response.contentAsString
		val response: Array<TeamResponse> = super.mapFromJson(content, Array<TeamResponse>::class.java)
		assertTrue(response.isNotEmpty())
		logger.info("getMyTeams END")
	}
}