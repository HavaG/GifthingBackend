package havag.gifthing.models.user

import com.fasterxml.jackson.annotation.JsonIgnore
import havag.gifthing.models.Role
import havag.gifthing.models.gift.Gift
import havag.gifthing.models.gift.dto.GiftResponse
import havag.gifthing.models.team.Team
import havag.gifthing.models.user.dto.TeamUserResponse
import havag.gifthing.models.user.dto.GiftUserResponse
import havag.gifthing.models.user.dto.UserResponse
import org.springframework.transaction.annotation.Transactional
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


@Entity(name = "User")
@Table(name = "users")
class User(
	@Column(unique = true)
	var username: String,
	@Column(unique = true)
	@NotBlank
	@Email
	var email: String,
	@NotBlank
	private var password: String
) {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	var id: Long = 0

	var firstName: String = ""

	var lastName: String = ""

	//TODO: add isActive var isActive: Boolean = true

	private var lastUpdate: Long = System.currentTimeMillis()

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "user_roles",
		joinColumns = [JoinColumn(name = "user_id")],
		inverseJoinColumns = [JoinColumn(name = "role_id")]
	)
	private var roles: Set<Role> = HashSet<Role>()


	@OneToMany(
		mappedBy = "owner",
		cascade = [CascadeType.ALL]
	)
	private var gifts = mutableListOf<Gift>()

	fun addGift(gift: Gift) {
		gifts.add(gift)
		gift.setOwner(this)
	}

	//remove a gift
	fun removeGift(gift: Gift) {
		//remove from my gift list
		gifts.remove(gift)
		gift.setOwner(null)
		gift.setReserveBy(null)
	}

	//remove all gifts one by one
	fun removeAllGifts() {
		for (i in gifts) {
			i.setOwner(null)
			i.setReserveBy(null)
		}
		gifts = mutableListOf<Gift>()
	}

	fun getGifts(): MutableList<Gift> {
		return gifts
	}

	fun setGifts(gifts: MutableList<Gift>) {
		this.gifts = gifts
	}

	fun getPassword(): String {
		return password
	}

	@OneToMany(
		mappedBy = "reservedBy",
		cascade = [CascadeType.ALL]
	)
	private var reservedGifts = mutableListOf<Gift>()

	fun reserveGift(gift: Gift) {
		reservedGifts.add(gift)
		gift.setReserveBy(this)
	}

	fun removeReservedGift(gift: Gift) {
		reservedGifts.remove(gift)
		gift.setReserveBy(null)
	}

	fun removeAllReservedGift() {
		for (i in reservedGifts) {
			i.setReserveBy(null)
		}
		reservedGifts = mutableListOf<Gift>()
	}

	fun getReservedGifts(): MutableList<Gift> {
		return reservedGifts
	}

	fun setReservedGifts(reservedGifts: MutableList<Gift>) {
		this.reservedGifts = reservedGifts
	}


	//the team, where the user is admin
	@OneToMany(
		mappedBy = "admin",
		cascade = [CascadeType.ALL]
	)
	private var myOwnedTeams = mutableListOf<Team>()

	fun addMyOwnedTeam(team: Team) {
		myOwnedTeams.add(team)
	}

	fun removeMyOwnedTeam(team: Team) {
		myOwnedTeams.remove(team)
		team.removeAdmin()
	}

	@JsonIgnore
	fun getMyOwnedTeams(): MutableList<Team> {
		return myOwnedTeams
	}

	fun setMyOwnedTeams(ownedTeams: MutableList<Team>) {
		this.myOwnedTeams = ownedTeams
	}

	//The team, where the user is a member

	@ManyToMany(cascade = [CascadeType.ALL])
	@JoinTable(
		name = "team_members",
		joinColumns = [JoinColumn(name = "user_id")],
		inverseJoinColumns = [JoinColumn(name = "team_id")]
	)
	private var myTeams = mutableListOf<Team>()

	@JsonIgnore
	fun getMyTeams(): MutableList<Team> {
		return myTeams
	}

	fun setMyTeams(myTeams: MutableList<Team>) {
		this.myTeams = myTeams
	}

	fun addMyTeam(team: Team) {
		myTeams.add(team)
	}

	fun removeMyTeam(team: Team) {
		myTeams.remove(team)
	}

	fun removeFromAllTeam() {
		myTeams = mutableListOf()
	}


	fun removeFromAllAdminTeam() {
		myOwnedTeams = mutableListOf()
	}

	fun getRoles(): Set<Role> {
		return roles
	}

	fun setRoles(roles: Set<Role>) {
		this.roles = roles
	}

	fun toUserResponse(): UserResponse {
		val tmpOneUser = UserResponse(
			this.email,
			this.id,
			this.firstName,
			this.lastName,
			this.username,
			this.lastUpdate
		)

		val tmpGiftList = mutableListOf<GiftResponse>()
		val tmpReservedGiftList = mutableListOf<GiftResponse>()
		val tmpTeamList = mutableListOf<TeamUserResponse>()
		val tmpOwnedTeamList = mutableListOf<TeamUserResponse>()

		for (j in this.getGifts()) {
			tmpGiftList.add(j.toGiftResponse())
		}
		tmpOneUser.setGifts(tmpGiftList)

		for (j in this.getReservedGifts()) {
			tmpReservedGiftList.add(j.toGiftResponse())
		}
		tmpOneUser.setReservedGifts(tmpReservedGiftList)

		for (j in this.getMyTeams()) {
			tmpTeamList.add(j.toTeamUserResponse())
		}
		tmpOneUser.setMyTeams(tmpTeamList)

		for (j in this.getMyOwnedTeams()) {
			tmpOwnedTeamList.add(j.toTeamUserResponse())
		}
		tmpOneUser.setMyOwnedTeams(tmpOwnedTeamList)

		return tmpOneUser
	}

	fun toUserGiftResponse(): GiftUserResponse {
		return GiftUserResponse(
			this.email,
			this.id,
			this.firstName,
			this.lastName,
			this.username,
			this.lastUpdate
		)
	}
}