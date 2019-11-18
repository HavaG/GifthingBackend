package HavaG.Gifthing.controller.gift

import HavaG.Gifthing.models.gift.Gift
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GiftRepository : CrudRepository<Gift, Long>{

}