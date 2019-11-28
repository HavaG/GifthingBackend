package HavaG.Gifthing.controller.gift

import HavaG.Gifthing.models.gift.Gift
import HavaG.Gifthing.models.gift.dto.GiftResponse
import java.util.*

interface IGiftService {

    fun getAllGift(): MutableIterable<GiftResponse>
    fun getGiftById(giftId: Long): GiftResponse?
    fun saveGift(gift: Gift): Gift
    fun updateGift(gift: Gift): Boolean
    fun deleteGift(giftId: Long): Boolean
}