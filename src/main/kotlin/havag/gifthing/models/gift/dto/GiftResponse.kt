package havag.gifthing.models.gift.dto

class GiftResponse (
        var id: Long,
        var name: String,
        var link: String? = null,
        var description: String? = null,
        var price: Int? = null,
        var owner: Long? = null,
        var reservedBy: Long? = null
)