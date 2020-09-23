package havag.gifthing.controller.gift

import havag.gifthing.models.gift.Gift
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GiftRepository : CrudRepository<Gift, Long>