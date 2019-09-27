package HavaG.Gifthing

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

class Gift( var name: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     var id: Long = 0
     var link: String? = null
     var description: String? = null
     var price: Int? = null
     var isReserved: Person? = null
}

//TODO: setName, getName, setLink, getLink, setDescription, getDescription, setPrice, getPrive, setReserved, getReserved