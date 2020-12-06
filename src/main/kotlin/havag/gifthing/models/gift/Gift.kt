package havag.gifthing.models.gift

import com.fasterxml.jackson.annotation.JsonIgnore
import havag.gifthing.models.gift.dto.GiftResponse
import havag.gifthing.models.user.User
import javax.persistence.*


@Entity(name = "Gift")
@Table(name = "gifts")
class Gift(
	var name: String,
	private var link: String? = null,
	private var description: String? = null,
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

	fun removeReserveBy() {
		setReserveBy(null)
	}

	fun toGiftResponse(): GiftResponse {
		val result = GiftResponse(
			id = this.id,
			name = this.name,
			link = this.link,
			description = this.description,
			price = this.price,
			lastUpdate = this.lastUpdate
		)
		this.owner?.let { result.owner = it.toUserGiftResponse() }
		this.reservedBy?.let { result.reservedBy = it.toUserGiftResponse() }
		return result
	}
}