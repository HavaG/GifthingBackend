package HavaG.Gifthing.Controller.Group

import HavaG.Gifthing.Models.Group
import HavaG.Gifthing.Models.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : CrudRepository<Group, Long>{

    //TODO: ez itt mind
    /*
    fun findByTitle(title: String): List<User>
    fun findDistinctByCategory(category: String): List<User>
    fun findByTitleAndCategory(title: String, category: String): List<User>
     */
}