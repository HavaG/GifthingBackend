package HavaG.Gifthing.Controller.User

import HavaG.Gifthing.Models.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long>{

    //TODO: UserRepository
    /*
    fun findByTitle(title: String): List<User>
    fun findDistinctByCategory(category: String): List<User>
    fun findByTitleAndCategory(title: String, category: String): List<User>
     */
}