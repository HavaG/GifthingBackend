package HavaG.Gifthing.controller.user

import HavaG.Gifthing.models.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long>{
    //TODO: UserRepository
}