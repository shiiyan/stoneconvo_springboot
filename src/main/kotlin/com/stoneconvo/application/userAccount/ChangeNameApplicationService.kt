package com.stoneconvo.application.userAccount

import com.stoneconvo.application.command.userAccount.ChangeAccountNameCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.userAccount.UserAccount
import com.stoneconvo.domain.userAccount.UserAccountDomainService
import com.stoneconvo.domain.userAccount.UserAccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeNameApplicationService(
    @Autowired
    private val userAccountRepository: UserAccountRepository,
    @Autowired
    private val userAccountDomainService: UserAccountDomainService
) {
    @Transactional
    fun handleChangeName(changeAccountNameCommand: ChangeAccountNameCommand) {
        val foundUserAccount = UserAccount.fromDto(
            userAccountRepository.findByUserId(changeAccountNameCommand.currentUserId)
                ?: throw CustomException.UserAccountNotFoundException(
                    userId = changeAccountNameCommand.currentUserId.value
                )
        )

        userAccountDomainService.verifyAccountNotExistByName(changeAccountNameCommand.newName)

        foundUserAccount.changeName(changeAccountNameCommand.newName)

        userAccountRepository.update(foundUserAccount)
    }
}
