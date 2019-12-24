package HavaG.Gifthing

import HavaG.Gifthing.controller.gift.GiftRepository
import HavaG.Gifthing.controller.team.TeamRepository
import HavaG.Gifthing.controller.user.UserRepository
import HavaG.Gifthing.models.gift.Gift
import HavaG.Gifthing.models.team.Team
import HavaG.Gifthing.models.user.User
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

        val p1 = User("a@aa.a", "a", "first", "last")
        p1.nickName = "Gaborka"
        val p2 = User("nemisemail@nincsilyen.hu", "rnd", "Random", "kereszt")

        val p3 = User("admin@admin.com", "admin", "Gabor", "Havasi")

        val g1 = Gift("kes")
        val g2 = Gift("villa")
        val g3 = Gift("ollo")
        val g4 = Gift("gyerek")
        val g5 = Gift("keze")

        g1.price = 100
        g2.price = 110
        g3.price = 111
        g4.price = 30000

        val team1 = Team("elsoCsoport")
        val team2 = Team("masodikCsoport")
        val team3 = Team("harmadikCsoport")
        val team4 = Team("negyedikCsoport")
        val team5 = Team("otodikCsoport")

        p2.reserveGift(g1)
        p1.addGift(g1)
        p1.addGift(g2)
        p1.addGift(g3)
        p1.addGift(g4)
        p1.addGift(g5)

        team1.setAdmin(p1)
        team2.setAdmin(p1)
        team3.setAdmin(p1)
        team4.setAdmin(p2)
        team5.setAdmin(p2)

        team1.addMember(p1)
        team1.addMember(p2)
        team2.addMember(p1)
        team3.addMember(p1)
        team4.addMember(p2)
        team5.addMember(p2)

        val users = mutableListOf<User>()
        users.add(p1)
        users.add(p2)
        users.add(p3)


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