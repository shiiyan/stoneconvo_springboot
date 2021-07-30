package com.stoneconvo.application

import com.stoneconvo.application.command.userAccount.ChangeAccountNameCommand
import com.stoneconvo.application.command.userAccount.ChangePasswordCommand
import com.stoneconvo.application.command.userAccount.CreateAccountCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.administrator.AdministratorRepository
import com.stoneconvo.domain.userAccount.UserAccount
import com.stoneconvo.domain.userAccount.UserAccountDomainService
import com.stoneconvo.domain.userAccount.UserAccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAccountApplicationService(
    @Autowired
    private val administratorRepository: AdministratorRepository,
    @Autowired
    private val userAccountRepository: UserAccountRepository,
    @Autowired
    private val userAccountDomainService: UserAccountDomainService
) {
    @Transactional
    fun handleCreate(createAccountCommand: CreateAccountCommand): String {
        val administrator = administratorRepository.findByUserId(createAccountCommand.currentUserId)
            ?: throw CustomException.AdministratorNotFoundException(
                userId = createAccountCommand.currentUserId
            )

        userAccountDomainService.verifyAccountNotExistByName(createAccountCommand.name)

        val newUserAccount = UserAccount.create(
            name = createAccountCommand.name,
            passwordHash = createAccountCommand.passwordHash,
            creator = administrator
        )

        userAccountRepository.insert(newUserAccount)

        return newUserAccount.id.value
    }

    @Transactional
    fun handleChangeName(changeAccountNameCommand: ChangeAccountNameCommand) {
        val foundUserAccount =
            userAccountRepository.findByUserId(changeAccountNameCommand.currentUserId)
                ?: throw CustomException.UserAccountNotFoundException(
                    userId = changeAccountNameCommand.currentUserId.value
                )

        userAccountDomainService.verifyAccountNotExistByName(changeAccountNameCommand.newName)

        foundUserAccount.changeName(changeAccountNameCommand.newName)

        userAccountRepository.update(foundUserAccount)
    }

    @Transactional
    fun handleChangePassword(changePasswordCommand: ChangePasswordCommand) {
        val foundUserAccount =
            userAccountRepository.findByUserId(changePasswordCommand.currentUserId)
                ?: throw CustomException.UserAccountNotFoundException(
                    userId = changePasswordCommand.currentUserId.value
                )

        foundUserAccount.changePassword(changePasswordCommand.passwordHash)

        userAccountRepository.update(foundUserAccount)
    }
}
