package havag.gifthing.gift

import havag.gifthing.models.gift.Gift
import havag.gifthing.models.gift.dto.GiftRequest
import havag.gifthing.models.gift.dto.GiftResponse
import havag.gifthing.models.user.User
import havag.gifthing.repositories.GiftRepository
import havag.gifthing.repositories.UserRepository
import havag.gifthing.security.services.UserDetailsProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class GiftService(
	val giftRepository: GiftRepository,
	val userRepository: UserRepository,
	val userService: UserDetailsProvider,
	val logger: Logger = LoggerFactory.getLogger(GiftService::class.java)
) : IGiftService {

	private fun isPresentGift(giftId: Long): Gift? {
		val gift = giftRepository.findById(giftId)
		return if (gift.isPresent) {
			gift.get()
		} else {
			null
		}
	}

	private fun isPresentUser(userId: Long): User? {
		val user = userRepository.findById(userId)
		return if (user.isPresent) {
			user.get()
		} else {
			null
		}
	}

	private fun isOwner(userId: Long, giftId: Long): Boolean {
		val user = isPresentUser(userId)
		val myGiftList = user?.getGifts()
		var owner = false
		myGiftList?.forEach {
			if (it.id == giftId)
				owner = true
		}
		return owner
	}

	override fun reserve(giftId: Long): GiftResponse? {
		val gift = isPresentGift(giftId)
		val userId = userService.getUser().id
		return if (gift != null) {
			val owner = isOwner(userId, giftId)
			if (!owner) {
				gift.reservedBy?.let {
					logger.info("Requested giftId ${gift.id} is already reserved. UserId $userId")
					return null
				} ?: run {
					val dbUser = isPresentUser(userId)
					gift.setReserveBy(dbUser)
					logger.info("UserId $userId reserved giftId $giftId")
				}
				gift.lastUpdate = System.currentTimeMillis()
				val result = giftRepository.save(gift)
				result.toGiftResponse()
			} else {
				logger.info("Can't reserve owned gift userId $userId")
				null
			}
		} else {
			logger.info("Requested gift is not exists userId $userId")
			null
		}
	}

	override fun release(giftId: Long): GiftResponse? {
		val gift = isPresentGift(giftId)
		val userId = userService.getUser().id
		return if (gift != null) {
			val owner = isOwner(userId, giftId)
			if (!owner) {
				gift.reservedBy?.let {
					gift.removeReserveBy()
					logger.info("UserId $userId released giftId $giftId")
				} ?: run {
					logger.info("GiftId $giftId is not reserved by anyone")
					return null
				}
				gift.lastUpdate = System.currentTimeMillis()
				val result = giftRepository.save(gift)
				result.toGiftResponse()
			} else {
				logger.info("Can't reserve owned gift userId $userId")
				null
			}
		} else {
			logger.info("Requested gift is not exists userId $userId")
			null
		}
	}

	override fun findAll(): MutableIterable<GiftResponse> {
		val gifts = giftRepository.findAll()
		val result = mutableListOf<GiftResponse>()
		for (i in gifts) {
			result.add(i.toGiftResponse())
		}
		logger.info("UserId ${userService.getUser().id} get all gifts")
		return result
	}

	override fun findById(giftId: Long): GiftResponse? {
		val gift = isPresentGift(giftId)
		if (gift != null) {
			logger.info("UserId ${userService.getUser().id} get giftId ${gift.id}")
			return gift.toGiftResponse()
		}
		logger.info("Requested gift is not exists userId ${userService.getUser().id}")
		return null
	}

	override fun create(gift: GiftRequest): GiftResponse {
		val user = userService.getUser()
		gift.owner = user.id
		val saveGift = gift.toGift(userRepository)
		val result = giftRepository.save(saveGift)
		logger.info("UserId ${user.id} created gift ${result.id}")
		return result.toGiftResponse()
	}

	override fun update(gift: GiftRequest): GiftResponse? {
		val tmp = giftRepository.findById(gift.id)
		return if (tmp.isPresent) {
			val tmpGift = tmp.get()
			val user = userService.getUser()
			val myGiftList = user.getGifts()
			var owner = false
			myGiftList.forEach {
				if (it.id == tmpGift.id)
					owner = true
			}
			if (owner) {
				val saveGift = gift.toGift(userRepository)
				val result = giftRepository.save(saveGift)
				logger.info("UserId ${user.id} updated gift ${result.id}")
				result.toGiftResponse()
			} else {
				logger.info("Update gift failed userId ${user.id} gift ${tmpGift.id} (not owner)")
				null
			}
		} else {
			logger.info("Update gift failed userId ${userService.getUser().id} giftId ${gift.id}")
			null
		}
	}

	override fun delete(giftId: Long): HttpStatus {
		val tmp = giftRepository.findById(giftId)
		return if (tmp.isPresent) {
			val temporal = tmp.get()
			val user = userService.getUser()
			val myGiftList = user.getGifts()
			var owner = false
			myGiftList.forEach {
				if (it.id == temporal.id)
					owner = true
			}
			if (owner) {
				temporal.removeOwner()
				temporal.removeReserveBy()
				giftRepository.delete(temporal)
				logger.info("UserId ${temporal.id} deleted gift $giftId")
				HttpStatus.OK
			} else {
				logger.info("Delete gift failed userId ${user.id} gift ${temporal.id} (not owner)")
				HttpStatus.UNAUTHORIZED
			}
		} else {
			logger.info("Requested gift is not exists userId ${userService.getUser().id}")
			HttpStatus.NOT_FOUND
		}
	}

	override fun myGifts(): MutableIterable<GiftResponse> {
		logger.info("UserId ${userService.getUser().id} get my gifts")
		return userService.getUser().getGifts()
	}

	override fun hisGifts(userId: Long): MutableIterable<GiftResponse> {
		logger.info("UserId ${userService.getUser().id} get his gifts userId: $userId")
		val result = mutableListOf<GiftResponse>()
		userRepository.findById(userId).get().getGifts().forEach { result.add(it.toGiftResponse()) }
		return result
	}
}