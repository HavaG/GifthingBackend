package havag.gifthing.models.gift

import com.fasterxml.jackson.annotation.JsonIgnore
import havag.gifthing.models.gift.dto.GiftResponse
import havag.gifthing.models.user.User
import havag.gifthing.models.user.dto.GiftUserResponse
import javax.persistence.*


@Entity(name = "Gift")
@Table(name = "gifts")
class Gift(
	var name: String,
	var link: String? = null,
	var description: String? = null,
	var price: Int? = null
) {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	var id: Long = 0

	var lastUpdate: Long = System.currentTimeMillis()

	@JsonIgnore
	@ManyToOne(cascade = [CascadeType.ALL])
	@JoinColumn(name = "user_id")
	private var owner: User? = null

	fun setOwner(user: User?) {
		owner = user
	}

	@JsonIgnore
	fun getOwner(): User? {
		return owner
	}

	fun removeOwner() {
		setOwner(null)
		setReserveBy(null)
	}

	@ManyToOne(cascade = [CascadeType.ALL])
	@JoinColumn(name = "reserve_id")
	var reservedBy: User? = null

	fun setReserveBy(user: User?) {
		reservedBy = user
	}

	@JsonIgnore
	fun getReserveBy(): User? {
		return reservedBy
	}

	fun removeReserveBy() {
		setReserveBy(null)
	}

	fun toGiftUserResponse(): GiftUserResponse {
		return GiftUserResponse(
			this.name,
			this.id,
			this.description,
			this.link,
			this.price
		)
	}

	fun toGiftResponse(): GiftResponse {
		val tmp = GiftResponse(
			this.id,
			this.name,
			this.link,
			this.description,
			this.price,
			lastUpdate = this.lastUpdate
		)
		this.owner?.let { tmp.owner = it.id }

		this.reservedBy?.let { tmp.reservedBy = it.id }

		return tmp
	}
}