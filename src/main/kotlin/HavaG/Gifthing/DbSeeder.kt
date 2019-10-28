package HavaG.Gifthing

import HavaG.Gifthing.controller.gift.GiftRepository
import HavaG.Gifthing.controller.team.TeamRepository
import HavaG.Gifthing.controller.user.UserRepository
import HavaG.Gifthing.models.Gift
import HavaG.Gifthing.models.Team
import HavaG.Gifthing.models.User
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DbSeeder(val userRepository: UserRepository,
               val giftRepository: GiftRepository,
               val teamRepository: TeamRepository
               ) : CommandLineRunner
{
    override fun run(vararg p0: String?) {
        this.userRepository.deleteAll()
        this.giftRepository.deleteAll()
        this.teamRepository.deleteAll()

        val p1 = User("havag@gmail.com", "pwd")
        p1.name = "Gaborka"
        val p2 = User("nemisemail@nincsilyen.hu", "rnd")

        val g1 = Gift("megkapnam")
        val g2 = Gift("megkapnam")
        val g3 = Gift("megkapnam")
        val g4 = Gift("megkapnam")
        val g5 = Gift("megkapnam")

        val team1 = Team("elsoCsoport")
        val team2 = Team("masodikCsoport")
        val team3 = Team("harmadikCsoport")
        val team4 = Team("negyedikCsoport")
        val team5 = Team("otodikCsoport")

        p2.reserveGift(g1)
        p1.addGift(g1)

        team1.setAdmin(p1)
        team2.setAdmin(p1)
        team3.setAdmin(p1)
        team4.setAdmin(p2)
        team5.setAdmin(p2)

        team1.addMember(p2)

        val users = mutableListOf<User>()
        users.add(p1)
        users.add(p2)


        val gifts = mutableListOf<Gift>()
        gifts.add(g1)
        gifts.add(g2)
        gifts.add(g3)
        gifts.add(g4)
        gifts.add(g5)

        val teams = mutableListOf<Team>()
        teams.add(team1)
        teams.add(team2)
        teams.add(team3)
        teams.add(team4)
        teams.add(team5)

        this.userRepository.saveAll(users)
        this.giftRepository.saveAll(gifts)
        this.teamRepository.saveAll(teams)
        println(" -- Database has been initialized")
    }
}