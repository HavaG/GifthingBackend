package havag.gifthing

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import java.io.IOException

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [GifthingApplication::class])
@ContextConfiguration
@AutoConfigureMockMvc
abstract class AbstractTest {
	@Autowired
	lateinit var mvc: MockMvc

	/*
	@Autowired
	lateinit var roleRepository: RoleRepository
		roleRepository.deleteAll()
		val roles = mutableListOf<Role>()
		roles.add(Role(ERole.ROLE_ADMIN))
		roles.add(Role(ERole.ROLE_MODERATOR))
		roles.add(Role(ERole.ROLE_USER))
		roleRepository.saveAll(roles)
	 */

	@Throws(JsonProcessingException::class)
	protected fun mapToJson(obj: Any?): String {
		return ObjectMapper().writeValueAsString(obj)
	}

	@Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
	protected fun <T> mapFromJson(json: String?, clazz: Class<T>?): T {
		val objectMapper = ObjectMapper()
		return objectMapper.readValue(json, clazz)
	}

	protected fun mvcPerformGet(uri: String): MvcResult {
		return mvc.perform(
			get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)
		).andReturn()

	}

	protected fun mvcPerformPost(uri: String, inputJson: String): MvcResult {
		return mvc.perform(
			post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)
		).andReturn()
	}

	protected fun mvcPerformDelete(uri: String): MvcResult {
		return mvc.perform(delete(uri)).andReturn()
	}
/*
	protected fun mvcPerformDelete(uri: String, inputJson: String?): MvcResult {
	}

	protected fun mvcPerformPut(uri: String, inputJson: String): MvcResult {

	}
 */
}