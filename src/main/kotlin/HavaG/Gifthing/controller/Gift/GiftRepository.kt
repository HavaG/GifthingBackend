package HavaG.Gifthing.controller.Gift

import HavaG.Gifthing.models.Gift
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GiftRepository : CrudRepository<Gift, Long>{

    //TODO: ez itt mind
}