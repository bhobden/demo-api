package com.eaglebank.api.service;

import com.eaglebank.api.dao.AccountDAO;
import com.eaglebank.api.dao.TransactionDAO;
import com.eaglebank.api.metrics.MetricScope;
import com.eaglebank.api.metrics.MetricScopeFactory;
import com.eaglebank.api.model.dto.request.CreateTransactionRequest;
import com.eaglebank.api.model.dto.response.ListTransactionsResponse;
import com.eaglebank.api.model.dto.response.TransactionResponse;
import com.eaglebank.api.model.entity.bankaccount.BankAccountEntity;
import com.eaglebank.api.model.entity.bankaccount.Currency;
import com.eaglebank.api.model.entity.bankaccount.transaction.TransactionEntity;
import com.eaglebank.api.model.entity.bankaccount.transaction.TransactionType;
import com.eaglebank.api.repository.BankAccountRepository;
import com.eaglebank.api.security.IdGenerator;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.events.Event.ID;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for handling business logic related to transactions.
 * <p>
 * Provides methods to create, retrieve, and delete transactions,
 * as well as deposit and withdrawal operations with validation and metrics.
 * </p>
 */
@Service
public class TransactionService extends AbstractService {

    @Autowired
    protected TransactionDAO transactionDAO;

    @Autowired
    protected AccountDAO accountDAO;

    /**
     * Finds a transaction by its ID.
     *
     * @param id the transaction ID
     * @return the transaction if found, or null if not found
     */
    public TransactionEntity getTransaction(String id) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.transaction.findbyid.duration")) {
            userValidation.validateUserAuthenticated();
            return transactionDAO.findTransactionById(id);
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Finds all transactions for a specific account.
     *
     * @param accountId the account ID
     * @return a list of transactions for the account
     */
    public ListTransactionsResponse getTransactions(String accountId) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.transaction.findbyaccountid.duration")) {
            userValidation.validateUserAuthenticated();
            List<TransactionEntity> transactions = transactionDAO.findTransactionsByAccountId(accountId);
            return new ListTransactionsResponse().setTransactions(transactions.stream()
                    .map(this::toTransactionResponse)
                    .toList());
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Deletes a transaction by its ID.
     *
     * @param id the transaction ID
     */
    public void deleteTransaction(String id) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.transaction.delete.duration")) {
            userValidation.validateUserAuthenticated();
            transactionDAO.deleteTransaction(id);
        } catch (Exception e) {
            handleException(e);
            return; // Unreachable, but required for compilation
        }
    }

    /**
     * Performs a deposit operation on a bank account.
     *
     * @param accountNumber the account number
     * @param amount        the amount to deposit
     * @param userId        the user performing the deposit
     * @param reference     optional reference for the transaction
     * @return the created TransactionEntity
     * @throws IllegalArgumentException if the amount is invalid or account not
     *                                  found
     */
    public TransactionResponse createTransaction(CreateTransactionRequest transactionRequest, String accountNumber,
            String userId) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.transaction.deposit.duration")) {
            userValidation.validateUserAuthenticated();
            BankAccountEntity account = accountDAO.getAccount(accountNumber);
            return toTransactionResponse(save(transactionRequest, account, userId));

        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Converts a TransactionEntity to a TransactionResponse.
     *
     * @param entity the TransactionEntity to convert
     * @return the corresponding TransactionResponse
     */
    public TransactionResponse toTransactionResponse(TransactionEntity entity) {
        if (entity == null)
            return null;
        TransactionResponse response = new TransactionResponse();
        response.setId(entity.getId());
        response.setAmount(entity.getAmount());
        response.setCurrency(entity.getCurrency());
        response.setType(entity.getType());
        response.setReference(entity.getReference());
        response.setUserId(entity.getUserId());
        response.setCreatedTimestamp(convertInstantToLocalDateTimeUTC(entity.getCreatedTimestamp()));
        return response;
    }

    /**
     * Saves a transaction entity.
     * 
     * @param transactionRequest the request containing transaction details
     * @param account            the bank account associated with the transaction
     * @param userId             the ID of the user performing the transaction
     * @return the saved transaction entity
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    protected TransactionEntity save(CreateTransactionRequest transactionRequest, BankAccountEntity account,
            String userId) {

        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(IdGenerator.generateTransactionId());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setCurrency(transactionRequest.getCurrency());
        transaction.setType(transactionRequest.getType());
        transaction.setReference(transactionRequest.getReference());
        transaction.setUserId(userId);
        transaction.setCreatedTimestamp(Instant.now());
        transaction.setAccountNumber(account.getAccountNumber());

        account.setBalance(account.getBalance() + transactionRequest.getAmount());
        account.setUpdatedTimestamp(Instant.now());

        accountDAO.updateBankAccount(account);
        TransactionEntity newTransaction = transactionDAO.createTransaction(transaction);

        return newTransaction;
    }
}