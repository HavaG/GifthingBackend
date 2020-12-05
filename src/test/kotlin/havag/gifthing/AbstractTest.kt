package havag.gifthing

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import havag.gifthing.models.ERole
import havag.gifthing.models.Role
import havag.gifthing.repositories.RoleRepository
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.transaction.BeforeTransaction
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.MockMvcConfigurer
import org.springframework.web.context.WebApplicationContext
import java.io.IOException
import javax.management.relation.RoleResult

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [GifthingApplication::class])
@ContextConfiguration
abstract class AbstractTest {
	protected var mvc: MockMvc? = null

	@Autowired
	lateinit var roleRepository: RoleRepository
	/*
		roleRepository.deleteAll()
		val roles = mutableListOf<Role>()
		roles.add(Role(ERole.ROLE_ADMIN))
		roles.add(Role(ERole.ROLE_MODERATOR))
		roles.add(Role(ERole.ROLE_USER))
		roleRepository.saveAll(roles)
	 */

	@Autowired
	lateinit var webApplicationContext: WebApplicationContext

	protected fun setUp() {
		mvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.build()
	}

	@Throws(JsonProcessingException::class)
	protected fun mapToJson(obj: Any?): String {
		val objectMapper = ObjectMapper()
		return objectMapper.writeValueAsString(obj)
	}

	@Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
	protected fun <T> mapFromJson(json: String?, clazz: Class<T>?): T {
		val objectMapper = ObjectMapper()
		return objectMapper.readValue(json, clazz)
	}

	protected fun mvcPerform(uri: String, inputJson: String): MvcResult {
		return mvc!!.perform(
			post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)
		).andReturn()
	}
}