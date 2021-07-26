package com.stoneconvo.application

import com.stoneconvo.application.command.ChangeAccountNameCommand
import com.stoneconvo.application.command.CreateAccountCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.administrator.AdministratorRepository
import com.stoneconvo.domain.userAccount.UserAccount
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
) {
    @Transactional
    fun create(createAccountCommand: CreateAccountCommand): String {
        val administrator = administratorRepository.findByUserId(createAccountCommand.currentUserId)
            ?: throw CustomException.AdministratorNotFoundException(
                userId = createAccountCommand.currentUserId
            )

        val newUserAccount = UserAccount.create(
            name = createAccountCommand.name,
            passwordHash = createAccountCommand.passwordHash,
            creator = administrator
        )

        userAccountRepository.insert(newUserAccount)

        return newUserAccount.id.value
    }

    @Transactional
    fun changeName(changeAccountNameCommand: ChangeAccountNameCommand) {
        val foundUserAccount =
            userAccountRepository.findByUserId(changeAccountNameCommand.currentUserId)
                ?: throw CustomException.UserAccountNotFoundException(
                    userId = changeAccountNameCommand.currentUserId.value
                )

        foundUserAccount.changeName(changeAccountNameCommand.newName)

        userAccountRepository.update(foundUserAccount)
    }

    // changePassword
}