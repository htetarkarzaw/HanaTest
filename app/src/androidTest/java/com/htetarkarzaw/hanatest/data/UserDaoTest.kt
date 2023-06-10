package com.htetarkarzaw.hanatest.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.htetarkarzaw.hanatest.data.local.HanaDatabase
import com.htetarkarzaw.hanatest.data.local.dao.UserDao
import com.htetarkarzaw.hanatest.data.local.entity.Address
import com.htetarkarzaw.hanatest.data.local.entity.Company
import com.htetarkarzaw.hanatest.data.local.entity.Geo
import com.htetarkarzaw.hanatest.data.local.entity.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var database: HanaDatabase
    private lateinit var userDao: UserDao
    private val users = listOf(
        User(
            id = 1,
            name = "Leanne Graham",
            phone = "1-770-736-8031 x56442",
            username = "Bret",
            email = "Sincere@april.biz",
            website = "hildegard.orgsite",
            address = Address(
                city = "Gwenborough",
                street = "Kulas Light",
                zipcode = "92998-3874",
                geo = Geo(lat = "-37.3159", lng = "81.1496"),
                suite = "Apt. 556"
            ),
            company = Company(
                bs = "harness real-time e-markets",
                catchPhrase = "Multi-layered client-server neural-net",
                name = "Romaguera-Crona"
            )
        ),
        User(
            id = 2,
            name = "Ervin Howell",
            phone = "1-770-736-8031 x56442",
            username = "Antonette",
            email = "Shanna@melissa.tv",
            website = "anastasia.net",
            address = Address(
                street = "Victor Plains",
                suite = "Suite 879",
                city = "Wisokyburgh",
                zipcode = "90566-7771",
                geo = Geo(
                    lat = "-43.9509",
                    lng = "-34.4618"
                )
            ),
            company = Company(
                name = "Deckow-Crist",
                catchPhrase = "Proactive didactic contingency",
                bs = "synergize scalable supply-chains"
            )
        ),
        User(
            id = 3,
            name = "Clementine Bauch",
            username = "Samantha",
            email = "Nathan@yesenia.net",
            address = Address(
                street = "Douglas Extension",
                suite = "Suite 847",
                city = "McKenziehaven",
                zipcode = "59590-4157",
                geo = Geo(
                    lat = "-68.6102",
                    lng = "-47.0653"
                )
            ),
            phone = "1-463-123-4447",
            website = "ramiro.info",
            company = Company(
                name = "Romaguera-Jacobson",
                catchPhrase = "Face to face bifurcated interface",
                bs = "e-enable strategic applications"
            )
        ),
        User(
            id = 4,
            name = "Patricia Lebsack",
            username = "Karianne",
            email = "Julianne.OConner@kory.org",
            address = Address(
                street = "Hoeger Mall",
                suite = "Apt. 692",
                city = "South Elvis",
                zipcode = "53919-4257",
                geo = Geo(
                    lat = "29.4572",
                    lng = "-164.2990"
                )
            ),
            phone = "493-170-9623 x156",
            website = "kale.biz",
            company = Company(
                name = "Robel-Corkery",
                catchPhrase = "Multi-tiered zero tolerance productivity",
                bs = "transition cutting-edge web services"
            )
        ),
        User(
            id = 5,
            name = "Chelsey Dietrich",
            username = "Kamren",
            email = "Lucio_Hettinger@annie.ca",
            address = Address(
                street = "Skiles Walks",
                suite = "Suite 351",
                city = "Roscoeview",
                zipcode = "33263",
                geo = Geo(
                    lat = "-31.8129",
                    lng = "62.5342"
                )
            ),
            phone = "(254)954-1289",
            website = "demarco.info",
            company = Company(
                name = "Keebler LLC",
                catchPhrase = "User-centric fault-tolerant solution",
                bs = "revolutionize end-to-end systems"
            )
        ),
        User(
            id = 6,
            name = "Mrs. Dennis Schulist",
            username = "Leopoldo_Corkery",
            email = "Karley_Dach@jasper.info",
            address = Address(
                street = "Norberto Crossing",
                suite = "Apt. 950",
                city = "South Christy",
                zipcode = "23505-1337",
                geo = Geo(
                    lat = "-71.4197",
                    lng = "71.7478"
                )
            ),
            phone = "1-477-935-8478 x6430",
            website = "ola.org",
            company = Company(
                name = "Considine-Lockman",
                catchPhrase = "Synchronised bottom-line interface",
                bs = "e-enable innovative applications"
            )

        ),
        User(
            id = 7,
            name = "Kurtis Weissnat",
            username = "Elwyn.Skiles",
            email = "Telly.Hoeger@billy.biz",
            address = Address(
                street = "Rex Trail",
                suite = "Suite 280",
                city = "Howemouth",
                zipcode = "58804-1099",
                geo = Geo(
                    lat = "24.8918",
                    lng = "21.8984"
                )
            ),
            phone = "210.067.6132",
            website = "elvis.io",
            company = Company(
                name = "Johns Group",
                catchPhrase = "Configurable multimedia task-force",
                bs = "generate enterprise e-tailers"
            )
        ),
        User(
            id = 8,
            name = "Nicholas Runolfsdottir V",
            username = "Maxime_Nienow",
            email = "Sherwood@rosamond.me",
            address = Address(
                street = "Ellsworth Summit",
                suite = "Suite 729",
                city = "Aliyaview",
                zipcode = "45169",
                geo = Geo(
                    lat = "-14.3990",
                    lng = "-120.7677"
                )
            ),
            phone = "586.493.6943 x140",
            website = "jacynthe.com",
            company = Company(
                name = "Abernathy Group",
                catchPhrase = "Implemented secondary concept",
                bs = "e-enable extensible e-tailers"
            )
        ),
        User(
            id = 9,
            name = "Glenna Reichert",
            username = "Delphine",
            email = "Chaim_McDermott@dana.io",
            address = Address(
                street = "Dayna Park",
                suite = "Suite 449",
                city = "Bartholomebury",
                zipcode = "76495-3109",
                geo = Geo(
                    lat = "24.6463",
                    lng = "-168.8889"
                )
            ),
            phone = "(775)976-6794 x41206",
            website = "conrad.com",
            company = Company(
                name = "Yost and Sons",
                catchPhrase = "Switchable contextually-based project",
                bs = "aggregate real-time technologies"
            )
        ),
        User(
            id = 10,
            name = "Clementina DuBuque",
            username = "Moriah.Stanton",
            email = "Rey.Padberg@karina.biz",
            address = Address(
                street = "Kattie Turnpike",
                suite = "Suite 198",
                city = "Lebsackbury",
                zipcode = "31428-2261",
                geo = Geo(
                    lat = "-38.2386",
                    lng = "57.2232"
                )
            ),
            phone = "024-648-3804",
            website = "ambrose.net",
            company = Company(
                name = "Hoeger LLC",
                catchPhrase = "Centralized empowering task-force",
                bs = "target end-to-end models"
            )
        )
    )

    @Before
    fun setup() {
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                HanaDatabase::class.java,
            )
            .allowMainThreadQueries()
            .build()
        userDao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun successfully_insert_and_retrieve_users() = runBlocking {
        userDao.insertUsers(users)
        val dbUsers = userDao.retrieveUsers().first()
        assertEquals(users, dbUsers)
    }

    @Test
    fun successfully_deleted_all_feeds() = runBlocking {
        userDao.insertUsers(users)
        userDao.deleteAllUsers()
        val dbUsers = userDao.retrieveUsers().first()
        assertEquals(0, dbUsers.size)
    }

    @Test
    fun successfully_retrieve_user_by_id() = runBlocking {
        userDao.insertUsers(users)
        val dbSuer = userDao.retrieveUserByIdViaFlow(3).first()
        assertEquals(users[2],dbSuer)
    }
}