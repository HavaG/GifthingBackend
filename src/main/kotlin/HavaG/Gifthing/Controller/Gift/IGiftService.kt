package HavaG.Gifthing.Controller.Gift

import HavaG.Gifthing.Models.Gift
import java.util.*

interface IGiftService {

    fun getAllGift(): MutableIterable<Gift>
    fun getGiftById(giftId: Long): Optional<Gift>
    fun addGift(gift: Gift): Boolean
    fun updateGift(gift: Gift): Boolean
    fun deleteGift(giftId: Long)
}