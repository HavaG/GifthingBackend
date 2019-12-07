package HavaG.Gifthing.controller.gift

import HavaG.Gifthing.controller.user.UserRepository
import HavaG.Gifthing.models.gift.dto.GiftRequest
import HavaG.Gifthing.models.gift.dto.GiftResponse
import HavaG.Gifthing.models.user.User
import org.springframework.stereotype.Service

@Service
class GiftService(
        val giftRepository: GiftRepository,
        val userRepository: UserRepository) : IGiftService {

    override fun reserveGift(giftId: Long, userId: Long): GiftResponse? {
        val gift = giftRepository.findById(giftId)
        val user = userRepository.findById(userId)
        return if(gift.isPresent && user.isPresent) {
            val tmp = gift.get()
            val tmpReserver = tmp.getReserveBy()
            if(tmpReserver != null){
                if(tmpReserver.id == userId) { //unreserve
                    tmp.reservedBy = null
                    val result = giftRepository.save(tmp)
                    result.toGiftResponse()
                } else { //valaki más már lefoglalta az ajit
                    return null
                }
            }
            tmp.setReserveBy(user.get()) //reserve
            val result = giftRepository.save(tmp)
            result.toGiftResponse()
        } else {
            null
        }
    }

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

    override fun createGift(gift: GiftRequest): GiftResponse {
        val saveGift = gift.toGift(userRepository)
        val result = giftRepository.save(saveGift)
        return result.toGiftResponse()
    }

    override fun updateGift(gift: GiftRequest): GiftResponse? {
        val tmp = giftRepository.findById(gift.id)
        if(tmp.isPresent) {
            val saveGift = gift.toGift(userRepository)
            val result = giftRepository.save(saveGift)
            return result.toGiftResponse()
        }
        else
            return null
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