package com.stoneconvo.applicationService.userAccount

import com.stoneconvo.applicationService.userAccount.command.ChangeNameCommand
import com.stoneconvo.applicationService.userAccount.command.CreateCommand
import com.stoneconvo.domain.userAccount.UserAccount
import com.stoneconvo.exceptions.AdministratorNotFoundException
import com.stoneconvo.exceptions.UserAccountNotFoundException
import com.stoneconvo.repository.administrator.AdministratorRepository
import com.stoneconvo.repository.userAccount.UserAccountRepository
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
    fun create(createCommand: CreateCommand) {
        val administrator = administratorRepository.findByUserId(createCommand.currentUserId)
            ?: throw AdministratorNotFoundException(
                userId = createCommand.currentUserId
            )

        val newUserAccount = UserAccount.create(
            name = createCommand.name,
            passwordHash = createCommand.passwordHash,
            creator = administrator
        )

        userAccountRepository.insert(newUserAccount)
    }

    @Transactional
    fun changeName(changeNameCommand: ChangeNameCommand) {
        val foundUserAccount =
            userAccountRepository.findByUserId(changeNameCommand.currentUserId)
                ?: throw UserAccountNotFoundException(
                    userAccountId = changeNameCommand.currentUserId
                )

        foundUserAccount.changeName(changeNameCommand.newName)

        userAccountRepository.update(foundUserAccount)
    }

    // changePassword
}