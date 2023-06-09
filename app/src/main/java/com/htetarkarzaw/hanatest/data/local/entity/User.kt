package com.htetarkarzaw.hanatest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.htetarkarzaw.hanatest.data.remote.dto.AddressDTO
import com.htetarkarzaw.hanatest.data.remote.dto.CompanyDTO
import com.htetarkarzaw.hanatest.data.remote.dto.GeoDTO
import com.htetarkarzaw.hanatest.data.remote.dto.UserDTO
import com.htetarkarzaw.hanatest.domain.vo.UserVO
import com.htetarkarzaw.hanatest.utils.Constant

@Entity(tableName = Constant.HANA_TABLE_NAME)
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val username: String,
    val name: String,
    @Embedded
    val address: Address,
    @Embedded
    val company: Company,
    val email: String,
    val phone: String,
    val website: String
) {
    fun toVO(): UserVO =
        UserVO(
            id = id,
            name = name,
            phone = phone, username = username,
            website = website, address = "${address.suite} \n${address.street} \n${address.city}",
            company = "${company.name} \n${company.bs} \n${company.catchPhrase}", email = email
        )

    fun toDto(): UserDTO =
        UserDTO(
            id = id,
            name = name,
            phone = phone,
            username = username,
            email = email,
            website = website,
            address = AddressDTO(
                city = address.city,
                street = address.street,
                zipcode = address.zipcode,
                geo = GeoDTO(lat = address.geo.lat, lng = address.geo.lng),
                suite = address.suite
            ),
            company = CompanyDTO(bs = company.bs, catchPhrase = company.catchPhrase, name = company.name)
        )
}

data class Address(
    val city: String,
    @Embedded
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
)

data class Company(
    val bs: String,
    val catchPhrase: String,
    @ColumnInfo(name = "company_name")
    val name: String
)

data class Geo(
    val lat: String,
    val lng: String
)