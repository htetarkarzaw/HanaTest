package com.htetarkarzaw.hanatest.data.remote.dto

import com.htetarkarzaw.hanatest.data.local.entity.Address
import com.htetarkarzaw.hanatest.data.local.entity.Company
import com.htetarkarzaw.hanatest.data.local.entity.Geo
import com.htetarkarzaw.hanatest.data.local.entity.User
import com.htetarkarzaw.hanatest.domain.vo.UserVO
import java.io.Serializable

class UsersDTO : ArrayList<UserDTO>()

data class UserDTO(
    val id: Int,
    val username: String,
    val address: AddressDTO,
    val company: CompanyDTO,
    val email: String,
    val name: String,
    val phone: String,
    val website: String
):Serializable {
    fun toVO(): UserVO =
        UserVO(
            id = id,
            name = name,
            phone = phone, username = username,
            website = website, address = "${address.suite} ${address.street} ${address.city}",
            company = "${company.name} \n${company.bs} \n${company.catchPhrase}", email = email
        )

    fun toEntity(): User =
        User(
            id = id,
            name = name,
            phone = phone,
            username = username,
            email = email,
            website = website,
            address = Address(
                city = address.city,
                street = address.street,
                zipcode = address.zipcode,
                geo = Geo(lat = address.geo.lat, lng = address.geo.lng),
                suite = address.suite
            ),
            company = Company(bs = company.bs, catchPhrase = company.catchPhrase, name = company.name)
        )
}

data class AddressDTO(
    val city: String,
    val geo: GeoDTO,
    val street: String,
    val suite: String,
    val zipcode: String
)

data class CompanyDTO(
    val bs: String,
    val catchPhrase: String,
    val name: String
)

data class GeoDTO(
    val lat: String,
    val lng: String
)