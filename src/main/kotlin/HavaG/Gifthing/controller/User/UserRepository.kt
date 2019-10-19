package HavaG.Gifthing.controller.User

import HavaG.Gifthing.models.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Long>{

    //TODO: ez itt mind
    /*
    fun findByTitle(title: String): List<User>
    fun findDistinctByCategory(category: String): List<User>
    fun findByTitleAndCategory(title: String, category: String): List<User>
     */
}