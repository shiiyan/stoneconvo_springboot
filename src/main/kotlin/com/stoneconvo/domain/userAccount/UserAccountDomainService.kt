package com.stoneconvo.domain.userAccount

import com.stoneconvo.common.exception.CustomException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserAccountDomainService(
    @Autowired
    private val userAccountRepository: UserAccountRepository,
) {
    fun verifyAccountNotExist(name: UserAccountName) {
        if (userAccountRepository.findByUserName(name) != null) {
            throw CustomException.UserAccountAlreadyExistException(
                userName = name.value
            )
        }
    }
}
