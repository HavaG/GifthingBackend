package havag.gifthing.gift

import havag.gifthing.repositories.UserRepository
import havag.gifthing.models.gift.dto.GiftRequest
import havag.gifthing.models.gift.dto.GiftResponse
import havag.gifthing.models.user.dto.GiftUserResponse
import havag.gifthing.repositories.GiftRepository
import havag.gifthing.security.services.UserDetailsProvider
import org.springframework.stereotype.Service

@Service
class GiftService(
	val giftRepository: GiftRepository,
	val userRepository: UserRepository,
    val userService: UserDetailsProvider
) : IGiftService {

    override fun reserve(giftId: Long, userId: Long): GiftResponse? {
        val gift = giftRepository.findById(giftId)
        val user = userRepository.findById(userId)
        return if(gift.isPresent && user.isPresent) {
            val tmp = gift.get()
            /*val tmpReserver = tmp.getReserveBy()
            if(tmpReserver != null){
                if(tmpReserver.id == userId) { //unreserve
                    tmp.reservedBy = null
                    val result = giftRepository.save(tmp)
                    result.toGiftResponse()
                } else { //valaki más már lefoglalta az ajit
                    return null
                }
            }*/
            tmp.setReserveBy(user.get()) //reserve
            val result = giftRepository.save(tmp)
            result.toGiftResponse()
        } else {
            null
        }
    }

    override fun findAll(): MutableIterable<GiftResponse> {
        val gifts = giftRepository.findAll()
        val result = mutableListOf<GiftResponse>()
        for(i in gifts) {
            result.add(i.toGiftResponse())
        }
        return result
    }

    override fun findById(giftId: Long): GiftResponse? {
        val tmpGift = giftRepository.findById(giftId)
        if(!tmpGift.isPresent){
            return null
        }
        return tmpGift.get().toGiftResponse()
    }

    override fun create(gift: GiftRequest): GiftResponse {
        val saveGift = gift.toGift(userRepository)
        val result = giftRepository.save(saveGift)
        return result.toGiftResponse()
    }

    override fun update(gift: GiftRequest): GiftResponse? {
        val tmp = giftRepository.findById(gift.id)
        if(tmp.isPresent) {
            val saveGift = gift.toGift(userRepository)
            val result = giftRepository.save(saveGift)
            return result.toGiftResponse()
        }
        else
            return null
    }

    override fun delete(giftId: Long): Boolean {
        val tmp = giftRepository.findById(giftId)

        if (tmp.isPresent) {
            val temporal = tmp.get()
            val myGiftList = userService.getUser().getGifts()
            var owner = false
            myGiftList.forEach{
                if(it.id == tmp.get().id)
                    owner = true
            }
            if(owner) {
                temporal.removeOwner()
                temporal.removeReserveBy()
                giftRepository.delete(temporal)
                return true
            }
            return false
        }
        return true
    }

    override fun myGifts(): MutableIterable<GiftUserResponse> {
        return userService.getUser().getGifts()
    }


}