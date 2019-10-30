package HavaG.Gifthing.controller.Gift

import HavaG.Gifthing.models.Gift
import org.springframework.stereotype.Service
import java.util.*

@Service
class GiftService(giftRepository: GiftRepository) : IGiftService {

    //TODO: itt mindegyiknek avn értelme? így kell őket használni? :D
    val giftRepository = giftRepository

    override fun getAllGift(): MutableIterable<Gift> {
        return giftRepository.findAll()
    }

    override fun getGiftById(giftId: Long): Optional<Gift> {
        return giftRepository.findById(giftId)
    }

    override fun addGift(gift: Gift): Boolean {
        for(i in giftRepository.findAll()) {
            //Already exist
            if(i.name == gift.name) {
                return false
            }
        }

        giftRepository.save(gift)
        return true
    }

    //TODO: ez itt nem is jó
    override fun updateGift(gift: Gift): Boolean {
        val updateId = gift.id
        //check if exist in db
        val tmp = giftRepository.findById(updateId)
        if(tmp.isPresent) {
            //delete old
            giftRepository.deleteById(tmp.get().id)
            //save new
            giftRepository.save(gift)
            //set old gift id
            gift.id = tmp.get().id
            return true
        }
        else
            return false
    }

    override fun deleteGift(giftId: Long) {
        val tmp = getGiftById(giftId)
        if (tmp.isPresent) {
            giftRepository.delete(tmp.get())
        }
    }

}