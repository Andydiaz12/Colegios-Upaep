package com.upaep.colegios.model.repository.accountbalance

import com.upaep.colegios.model.api.accountbalance.AccountBalanceService
import com.upaep.colegios.model.database.accountbalancedao.AccountBalanceDao
import com.upaep.colegios.model.entities.AnswerBack
import com.upaep.colegios.model.entities.accountbalance.AccountBalance
import com.upaep.colegios.model.repository.locksmith.LocksmithRepository
import javax.inject.Inject

class AccountBalanceRepository @Inject constructor(
    private val accountBalanceService: AccountBalanceService,
    private val locksmithRepository: LocksmithRepository,
    private val accountBalanceDao: AccountBalanceDao
) {
    suspend fun getAccountBalance(persclv: String, perseq: String): AnswerBack<List<AccountBalance>> {
        return when (val accountBalance = accountBalanceService.getAccountBalance(
            perseq = perseq,
            persclv = persclv,
            keyChain = locksmithRepository.getLockSmith().colegiosUpaepAcademico!!
        )) {
            is AnswerBack.Success -> {
                accountBalanceDao.deleteAccountBalance()
                accountBalanceDao.insertAccountBalance(item = accountBalance.data)
                accountBalance
            }
            else -> {
                AnswerBack.Error(errorMessage = "", data = accountBalanceDao.getAccountBalance())
            }
        }
    }
}