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

    override fun reserve(giftId: Long): GiftResponse? {
        val gift = giftRepository.findById(giftId)
        return if(gift.isPresent) {
            val tmpGift = gift.get()
            val user = userService.getUser()
            val myGiftList = user.getGifts()
            var owner = false
            myGiftList.forEach{
                if(it.id == tmpGift.id)
                    owner = true
            }
            if(!owner) { //TODO: itt még lehet kavarodás ha már le van foglalva
                val dbUser = userRepository.findById(user.id)
                tmpGift.setReserveBy(dbUser.get())
                val result = giftRepository.save(tmpGift)
                result.toGiftResponse()
            } else null
        } else null
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
        val user = userService.getUser()
        gift.owner = user.id
        val saveGift = gift.toGift(userRepository)
        val result = giftRepository.save(saveGift)
        return result.toGiftResponse()
    }

    override fun update(gift: GiftRequest): GiftResponse? {
        val tmp = giftRepository.findById(gift.id)
        if(tmp.isPresent) {
            val myGiftList = userService.getUser().getGifts()
            var owner = false
            myGiftList.forEach{
                if(it.id == tmp.get().id)
                    owner = true
            }
            if(owner) {
                val saveGift = gift.toGift(userRepository)
                val result = giftRepository.save(saveGift)
                return result.toGiftResponse()
            }
            return null //TODO: Nem a gift tulajdonosa
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
                if(it.id == temporal.id)
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