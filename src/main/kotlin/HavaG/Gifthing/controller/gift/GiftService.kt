package HavaG.Gifthing.controller.gift

import HavaG.Gifthing.models.Gift
import org.springframework.stereotype.Service
import java.util.*

@Service
class GiftService(val giftRepository: GiftRepository) : IGiftService {

    override fun getAllGift(): MutableIterable<Gift> {
        return giftRepository.findAll()
    }

    override fun getGiftById(giftId: Long): Optional<Gift> {
        return giftRepository.findById(giftId)
    }

    override fun addGift(gift: Gift): Boolean {
        giftRepository.save(gift)
        return true
    }

    override fun updateGift(gift: Gift): Boolean {
        //check if exist in db
        val tmp = giftRepository.findById(gift.id)
        return if(tmp.isPresent) {
            //save new
            giftRepository.save(gift)
            true
        }
        else
            false
    }

    override fun deleteGift(giftId: Long): Boolean {
        val tmp = getGiftById(giftId)
        if (tmp.isPresent) {
            val temporal = tmp.get()
            temporal.removeOwner()
            temporal.removeReserveBy()
            giftRepository.delete(temporal)
            return true
        }
        return false
    }
}