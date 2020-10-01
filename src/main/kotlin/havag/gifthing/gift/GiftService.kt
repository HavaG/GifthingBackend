package havag.gifthing.gift

import havag.gifthing.models.gift.dto.GiftRequest
import havag.gifthing.models.gift.dto.GiftResponse
import havag.gifthing.models.user.dto.GiftUserResponse
import havag.gifthing.repositories.GiftRepository
import havag.gifthing.repositories.UserRepository
import havag.gifthing.security.services.UserDetailsProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GiftService(
	val giftRepository: GiftRepository,
	val userRepository: UserRepository,
	val userService: UserDetailsProvider,
	val logger: Logger = LoggerFactory.getLogger(GiftService::class.java)
) : IGiftService {

	override fun reserve(giftId: Long): GiftResponse? {
		val gift = giftRepository.findById(giftId)
		return if (gift.isPresent) {
			val tmpGift = gift.get()
			val user = userService.getUser()
			val myGiftList = user.getGifts()
			var owner = false
			myGiftList.forEach {
				if (it.id == tmpGift.id)
					owner = true
			}
			if (!owner) { //TODO: itt még lehet kavarodás ha már le van foglalva
				val dbUser = userRepository.findById(user.id)
				tmpGift.setReserveBy(dbUser.get())
				val result = giftRepository.save(tmpGift)
				logger.info("UserId ${user.id} reserved giftId ${tmpGift.id}")
				result.toGiftResponse()
			} else {
				logger.info("Can't reserve owned gift userId ${user.id}")
				null
			}
		} else {
			logger.info("Requested gift is not exists userId ${userService.getUser().id}")
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
		val tmpGift = giftRepository.findById(giftId)
		if (tmpGift.isPresent) {
			logger.info("UserId ${userService.getUser().id} get giftId ${tmpGift.get().id}")
			return tmpGift.get().toGiftResponse()
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

	override fun delete(giftId: Long): Boolean {
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
				true
			} else {
				logger.info("Delete gift failed userId ${user.id} gift ${temporal.id} (not owner)")
				false
			}
		} else {
			logger.info("Requested gift is not exists userId ${userService.getUser().id}")
			true
		}
	}

	override fun myGifts(): MutableIterable<GiftUserResponse> {
		logger.info("UserId ${userService.getUser().id} get my gifts")
		return userService.getUser().getGifts()
	}
}