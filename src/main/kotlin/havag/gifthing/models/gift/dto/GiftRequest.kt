package havag.gifthing.models.gift.dto

import havag.gifthing.models.gift.Gift
import havag.gifthing.repositories.UserRepository

class GiftRequest(
	var id: Long,
	var name: String,
	var link: String? = null,
	var description: String? = null,
	var price: Int? = null,
	var owner: Long,
	var reservedBy: Long? = null
) {
	fun toGift(userRepository: UserRepository): Gift {
		val saveGift = Gift(
			this.name,
			this.link,
			this.description,
			this.price
		)
		saveGift.id = this.id
		val owner = userRepository.findById(this.owner)
		saveGift.setOwner(owner.get())

		this.reservedBy?.let {
			val reserved = userRepository.findById(it)
			saveGift.setReserveBy(reserved.get())
		}
		return saveGift
	}
}