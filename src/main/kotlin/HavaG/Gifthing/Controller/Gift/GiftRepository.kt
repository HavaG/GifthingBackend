package HavaG.Gifthing.Controller.Gift

import HavaG.Gifthing.Models.Gift
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GiftRepository : CrudRepository<Gift, Long>{
    //TODO: GiftRepository
}