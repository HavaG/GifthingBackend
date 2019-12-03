package HavaG.Gifthing.controller.gift

import HavaG.Gifthing.models.gift.dto.GiftRequest
import HavaG.Gifthing.models.gift.dto.GiftResponse

interface IGiftService {
    fun getAllGift(): MutableIterable<GiftResponse>
    fun getGiftById(giftId: Long): GiftResponse?
    fun saveGift(gift: GiftRequest): GiftResponse
    fun updateGift(gift: GiftRequest): Boolean
    fun deleteGift(giftId: Long): Boolean
}