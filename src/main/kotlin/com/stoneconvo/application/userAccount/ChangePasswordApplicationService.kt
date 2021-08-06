package com.stoneconvo.application.userAccount

import com.stoneconvo.application.command.userAccount.ChangePasswordCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.userAccount.UserAccount
import com.stoneconvo.domain.userAccount.UserAccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangePasswordApplicationService(
    @Autowired
    private val userAccountRepository: UserAccountRepository
) {
    @Transactional
    fun handleChangePassword(changePasswordCommand: ChangePasswordCommand) {
        val foundUserAccount = UserAccount.fromDto(
            userAccountRepository.findByUserId(changePasswordCommand.currentUserId)
                ?: throw CustomException.UserAccountNotFoundException(
                    userId = changePasswordCommand.currentUserId.value
                )
        )

        foundUserAccount.changePassword(changePasswordCommand.passwordHash)

        userAccountRepository.update(foundUserAccount)
    }
}
