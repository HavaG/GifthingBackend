package HavaG.Gifthing.models

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity(name = "User")
@Table(name = "user")
class User(var email: String, var password: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
    var name: String? = null

    @OneToMany(mappedBy = "owner",
            cascade= [CascadeType.ALL])
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
        for(i in gifts) {
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


    @OneToMany(mappedBy = "reservedBy",
            cascade= [CascadeType.ALL])
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
        for(i in reservedGifts) {
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
    @OneToMany(mappedBy = "admin",
            cascade= [CascadeType.ALL])
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

    @ManyToMany(cascade= [CascadeType.ALL])
    @JoinTable(
            name = "team_members",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "team_id")])
    private var myTeams = mutableListOf<Team>()

    //the team, where the user is admin
    fun addMyTeam(team: Team) {
        myTeams.add(team)
    }

    fun removeMyTeam(team: Team) {
        myTeams.remove(team)
    }

    fun removeFromAllTeam() {
        for(i in myTeams) {
            i.removeMember(this)
        }
        myTeams = mutableListOf<Team>()
    }

    @JsonIgnore
    fun getMyTeams(): MutableList<Team> {
        return myTeams
    }

    fun setMyTeams(myTeams: MutableList<Team>) {
        this.myTeams = myTeams
    }

}