package HavaG.Gifthing.controller.gift

import HavaG.Gifthing.controller.user.UserRepository
import HavaG.Gifthing.models.gift.dto.GiftRequest
import HavaG.Gifthing.models.gift.dto.GiftResponse
import org.springframework.stereotype.Service

@Service
class GiftService(
        val giftRepository: GiftRepository,
        val userRepository: UserRepository) : IGiftService {

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

    override fun saveGift(gift: GiftRequest): GiftResponse {
        val saveGift = gift.toGift(userRepository)
        val result = giftRepository.save(saveGift)
        return result.toGiftResponse()
    }

    override fun updateGift(gift: GiftRequest): Boolean {
        val tmp = giftRepository.findById(gift.id)
        return if(tmp.isPresent) {
            val saveGift = gift.toGift(userRepository)
            giftRepository.save(saveGift)
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
        return true
    }
}