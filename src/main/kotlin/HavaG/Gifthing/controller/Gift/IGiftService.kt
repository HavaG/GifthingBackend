package HavaG.Gifthing.controller.Gift

import HavaG.Gifthing.models.Gift
import java.util.*

interface IGiftService {

    fun getAllGift(): MutableIterable<Gift>
    fun getGiftById(giftId: Long): Optional<Gift>
    fun addGift(gift: Gift): Boolean
    fun updateGift(gift: Gift): Boolean
    fun deleteGift(giftId: Long)
}