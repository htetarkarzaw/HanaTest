package com.htetarkarzaw.hanatest.repository

import com.google.common.truth.Truth.assertThat
import com.htetarkarzaw.hanatest.data.local.dao.UserDao
import com.htetarkarzaw.hanatest.data.remote.HanaApiService
import com.htetarkarzaw.hanatest.data.remote.criteria.PostModelCriteria
import com.htetarkarzaw.hanatest.data.remote.dto.AddressDTO
import com.htetarkarzaw.hanatest.data.remote.dto.CompanyDTO
import com.htetarkarzaw.hanatest.data.remote.dto.GeoDTO
import com.htetarkarzaw.hanatest.data.remote.dto.PostDTO
import com.htetarkarzaw.hanatest.data.remote.dto.UserDTO
import com.htetarkarzaw.hanatest.data.remote.dto.UsersDTO
import com.htetarkarzaw.hanatest.domain.repository.HanaRepositoryImpl
import com.htetarkarzaw.hanatest.test_rule.TestCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.Response


@OptIn(ExperimentalCoroutinesApi::class)
class HanaRepositoryTest {
    private lateinit var service: HanaApiService
    private lateinit var dao: UserDao
    private var repository: HanaRepositoryImpl? = null
    private val userDTOs = arrayListOf(
        UserDTO(
            id = 1,
            name = "Leanne Graham",
            phone = "1-770-736-8031 x56442",
            username = "Bret",
            email = "Sincere@april.biz",
            website = "hildegard.orgsite",
            address = AddressDTO(
                city = "Gwenborough",
                street = "Kulas Light",
                zipcode = "92998-3874",
                geo = GeoDTO(lat = "-37.3159", lng = "81.1496"),
                suite = "Apt. 556"
            ),
            company = CompanyDTO(
                bs = "harness real-time e-markets",
                catchPhrase = "Multi-layered client-server neural-net",
                name = "Romaguera-Crona"
            )
        ),
        UserDTO(
            id = 2,
            name = "Ervin Howell",
            phone = "1-770-736-8031 x56442",
            username = "Antonette",
            email = "Shanna@melissa.tv",
            website = "anastasia.net",
            address = AddressDTO(
                street = "Victor Plains",
                suite = "Suite 879",
                city = "Wisokyburgh",
                zipcode = "90566-7771",
                geo = GeoDTO(
                    lat = "-43.9509",
                    lng = "-34.4618"
                )
            ),
            company = CompanyDTO(
                name = "Deckow-Crist",
                catchPhrase = "Proactive didactic contingency",
                bs = "synergize scalable supply-chains"
            )
        ),
        UserDTO(
            id = 3,
            name = "Clementine Bauch",
            username = "Samantha",
            email = "Nathan@yesenia.net",
            address = AddressDTO(
                street = "Douglas Extension",
                suite = "Suite 847",
                city = "McKenziehaven",
                zipcode = "59590-4157",
                geo = GeoDTO(
                    lat = "-68.6102",
                    lng = "-47.0653"
                )
            ),
            phone = "1-463-123-4447",
            website = "ramiro.info",
            company = CompanyDTO(
                name = "Romaguera-Jacobson",
                catchPhrase = "Face to face bifurcated interface",
                bs = "e-enable strategic applications"
            )
        ),
        UserDTO(
            id = 4,
            name = "Patricia Lebsack",
            username = "Karianne",
            email = "Julianne.OConner@kory.org",
            address = AddressDTO(
                street = "Hoeger Mall",
                suite = "Apt. 692",
                city = "South Elvis",
                zipcode = "53919-4257",
                geo = GeoDTO(
                    lat = "29.4572",
                    lng = "-164.2990"
                )
            ),
            phone = "493-170-9623 x156",
            website = "kale.biz",
            company = CompanyDTO(
                name = "Robel-Corkery",
                catchPhrase = "Multi-tiered zero tolerance productivity",
                bs = "transition cutting-edge web services"
            )
        ),
        UserDTO(
            id = 5,
            name = "Chelsey Dietrich",
            username = "Kamren",
            email = "Lucio_Hettinger@annie.ca",
            address = AddressDTO(
                street = "Skiles Walks",
                suite = "Suite 351",
                city = "Roscoeview",
                zipcode = "33263",
                geo = GeoDTO(
                    lat = "-31.8129",
                    lng = "62.5342"
                )
            ),
            phone = "(254)954-1289",
            website = "demarco.info",
            company = CompanyDTO(
                name = "Keebler LLC",
                catchPhrase = "User-centric fault-tolerant solution",
                bs = "revolutionize end-to-end systems"
            )
        ),
        UserDTO(
            id = 6,
            name = "Mrs. Dennis Schulist",
            username = "Leopoldo_Corkery",
            email = "Karley_Dach@jasper.info",
            address = AddressDTO(
                street = "Norberto Crossing",
                suite = "Apt. 950",
                city = "South Christy",
                zipcode = "23505-1337",
                geo = GeoDTO(
                    lat = "-71.4197",
                    lng = "71.7478"
                )
            ),
            phone = "1-477-935-8478 x6430",
            website = "ola.org",
            company = CompanyDTO(
                name = "Considine-Lockman",
                catchPhrase = "Synchronised bottom-line interface",
                bs = "e-enable innovative applications"
            )

        ),
        UserDTO(
            id = 7,
            name = "Kurtis Weissnat",
            username = "Elwyn.Skiles",
            email = "Telly.Hoeger@billy.biz",
            address = AddressDTO(
                street = "Rex Trail",
                suite = "Suite 280",
                city = "Howemouth",
                zipcode = "58804-1099",
                geo = GeoDTO(
                    lat = "24.8918",
                    lng = "21.8984"
                )
            ),
            phone = "210.067.6132",
            website = "elvis.io",
            company = CompanyDTO(
                name = "Johns Group",
                catchPhrase = "Configurable multimedia task-force",
                bs = "generate enterprise e-tailers"
            )
        ),
        UserDTO(
            id = 8,
            name = "Nicholas Runolfsdottir V",
            username = "Maxime_Nienow",
            email = "Sherwood@rosamond.me",
            address = AddressDTO(
                street = "Ellsworth Summit",
                suite = "Suite 729",
                city = "Aliyaview",
                zipcode = "45169",
                geo = GeoDTO(
                    lat = "-14.3990",
                    lng = "-120.7677"
                )
            ),
            phone = "586.493.6943 x140",
            website = "jacynthe.com",
            company = CompanyDTO(
                name = "Abernathy Group",
                catchPhrase = "Implemented secondary concept",
                bs = "e-enable extensible e-tailers"
            )
        ),
        UserDTO(
            id = 9,
            name = "Glenna Reichert",
            username = "Delphine",
            email = "Chaim_McDermott@dana.io",
            address = AddressDTO(
                street = "Dayna Park",
                suite = "Suite 449",
                city = "Bartholomebury",
                zipcode = "76495-3109",
                geo = GeoDTO(
                    lat = "24.6463",
                    lng = "-168.8889"
                )
            ),
            phone = "(775)976-6794 x41206",
            website = "conrad.com",
            company = CompanyDTO(
                name = "Yost and Sons",
                catchPhrase = "Switchable contextually-based project",
                bs = "aggregate real-time technologies"
            )
        ),
        UserDTO(
            id = 10,
            name = "Clementina DuBuque",
            username = "Moriah.Stanton",
            email = "Rey.Padberg@karina.biz",
            address = AddressDTO(
                street = "Kattie Turnpike",
                suite = "Suite 198",
                city = "Lebsackbury",
                zipcode = "31428-2261",
                geo = GeoDTO(
                    lat = "-38.2386",
                    lng = "57.2232"
                )
            ),
            phone = "024-648-3804",
            website = "ambrose.net",
            company = CompanyDTO(
                name = "Hoeger LLC",
                catchPhrase = "Centralized empowering task-force",
                bs = "target end-to-end models"
            )
        )
    )

    @get:Rule
    val testRule = TestCoroutinesRule()

    @Before
    fun setup() {
        service = mock(HanaApiService::class.java)
        dao = mock(UserDao::class.java)
        repository = HanaRepositoryImpl(
            dao = dao,
            apiService = service,
        )
    }

    @After
    fun teardown() {
        repository = null
    }

    @Test
    fun `fetchUsers successfully`() = runTest {
        val mockResponse = UsersDTO()
        mockResponse.addAll(userDTOs)
        Mockito.`when`(service.fetchUsers()).thenReturn(Response.success(mockResponse))
        val actual = repository?.fetchUsers()?.first()?.data.toString()
        assertThat(actual).isEqualTo("Success")
    }

    @Test
    fun `fetchUsers error`() = runTest {
        Mockito.`when`(service.fetchUsers()).thenReturn(
            Response.error(
                500,
                "error response".toResponseBody("txt".toMediaTypeOrNull())
            )
        )
        val actual = repository?.fetchUsers()?.first()?.message
        assertThat(actual).isEqualTo("Internal Server Error")
    }

    @Test
    fun `uploadUser successfully`() = runTest {
        val mockResponse = PostDTO(id = 101)
        val postModelCriteria = PostModelCriteria(userId = "101", title = "title", body = "body")
        Mockito.`when`(service.uploadUser(postModelCriteria)).thenReturn(Response.success(mockResponse))
        val actual = repository?.uploadUser(postModelCriteria)?.first()?.data.toString()
        assertThat(actual).isEqualTo("Uploaded Success: UserId is 101")
    }


    @Test
    fun `retrieve users successfully`() = runTest {
        val mockUsers = userDTOs.map { it.toEntity() }
        Mockito.`when`(dao.retrieveUsers()).thenReturn(flowOf(mockUsers))
        val actual = repository?.retrieveUsers()?.first()
        assertThat(actual).isEqualTo(mockUsers)
    }

    @Test
    fun `retrieve user by id successfully`() = runTest {
        val mockUser = userDTOs[0].toEntity()
        Mockito.`when`(dao.retrieveUserByIdViaFlow(1)).thenReturn(flowOf(mockUser))
        val actual = repository?.retrieveUserByUserId(1)?.first()
        assertThat(actual).isEqualTo(mockUser)
    }
}
