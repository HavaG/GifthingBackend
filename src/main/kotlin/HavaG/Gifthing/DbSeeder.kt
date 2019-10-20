package HavaG.Gifthing

import HavaG.Gifthing.Controller.Gift.GiftRepository
import HavaG.Gifthing.Controller.Group.GroupRepository
import HavaG.Gifthing.Controller.User.UserRepository
import HavaG.Gifthing.Models.Gift
import HavaG.Gifthing.Models.Group
import HavaG.Gifthing.Models.User
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DbSeeder(val userRepository: UserRepository,
               val giftRepository: GiftRepository,
               val groupRepository: GroupRepository) : CommandLineRunner
{
    override fun run(vararg p0: String?) {
        this.userRepository.deleteAll()
        this.giftRepository.deleteAll()

        //TODO: groupRepository why?
        this.groupRepository.deleteAll()

        val p1 = User("havag@gmail.com", "pwd")
        val p2 = User("nemisemail@nincsilyen.hu", "rnd")

        val g1 = Gift("megkapnam")

        val group1 = Group("elsoCsoport")


        p2.reserveGift(g1)
        p1.addGift(g1)

        val users = mutableListOf<User>()
        users.add(p1)
        users.add(p2)


        val gifts = mutableListOf<Gift>()
        gifts.add(g1)

        val groups = mutableListOf<Group>()
        groups.add(group1)

        this.userRepository.saveAll(users)
        this.giftRepository.saveAll(gifts)
        this.groupRepository.saveAll(groups)
        println(" -- Database has been initialized")
    }
}