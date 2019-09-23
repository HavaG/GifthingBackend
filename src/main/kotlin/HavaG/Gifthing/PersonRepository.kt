package HavaG.Gifthing

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : CrudRepository<Person, Long>{
    fun findByName(name: String) : List<Person>
}