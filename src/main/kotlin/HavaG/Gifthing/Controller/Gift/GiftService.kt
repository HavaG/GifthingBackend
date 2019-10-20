package HavaG.Gifthing.Controller.Gift

import HavaG.Gifthing.Models.Gift
import org.springframework.stereotype.Service
import java.util.*

@Service
class GiftService(val giftRepository: GiftRepository) : IGiftService {

    //TODO: itt mindegyiknek avn értelme? így kell őket használni? :D
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

    //TODO: updateGift
    override fun updateGift(gift: Gift): Boolean {
            return false
    }

    override fun deleteGift(giftId: Long) {
        val tmp = getGiftById(giftId)
        if (tmp.isPresent) {
            giftRepository.delete(tmp.get())
        }
    }

}