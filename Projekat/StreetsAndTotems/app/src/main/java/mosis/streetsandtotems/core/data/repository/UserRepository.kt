package mosis.streetsandtotems.core.data.repository

import mosis.streetsandtotems.core.data.data_source.UserDataDao
import mosis.streetsandtotems.core.domain.repository.UserRepository

class UserRepositoryImpl(private val dao: UserDataDao) : UserRepository {

}