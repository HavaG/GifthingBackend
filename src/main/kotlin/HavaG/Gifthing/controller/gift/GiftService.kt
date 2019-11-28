package HavaG.Gifthing.controller.gift

import HavaG.Gifthing.models.gift.Gift
import HavaG.Gifthing.models.gift.dto.GiftResponse
import HavaG.Gifthing.models.gift.dto.UserGiftResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class GiftService(val giftRepository: GiftRepository) : IGiftService {

    override fun getAllGift(): MutableIterable<GiftResponse> {
        val gifts = giftRepository.findAll()
        val result = mutableListOf<GiftResponse>()
        for(i in gifts) {
            result.add(i.toGiftResponse())
        }

        return result
    }

    override fun getGiftById(giftId: Long): GiftResponse? {
        val tmpGift = giftRepository.findById(giftId)
        if(!tmpGift.isPresent){
            return null
        }
        return tmpGift.get().toGiftResponse()
    }

    override fun saveGift(gift: Gift): Gift {
        return giftRepository.save(gift)
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
        val tmp = giftRepository.findById(giftId)
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