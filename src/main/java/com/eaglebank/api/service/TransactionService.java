package com.eaglebank.api.service;

import com.eaglebank.api.metrics.MetricScope;
import com.eaglebank.api.metrics.MetricScopeFactory;
import com.eaglebank.api.model.dto.request.CreateTransactionRequest;
import com.eaglebank.api.model.dto.response.ListTransactionsResponse;
import com.eaglebank.api.model.dto.response.TransactionResponse;
import com.eaglebank.api.model.entity.account.AccountEntity;
import com.eaglebank.api.model.entity.account.transaction.TransactionEntity;
import com.eaglebank.api.model.entity.account.transaction.TransactionType;
import com.eaglebank.api.security.AuthUtils;
import com.eaglebank.api.security.IdGenerator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
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

    /**
     * Retrieves a transaction by its ID for a specific account, ensuring the user is authenticated
     * and authorized to access the account.
     *
     * @param accountNumber the account number
     * @param transactionId the transaction ID
     * @return the transaction as a TransactionResponse if found, or null if not found or unauthorized
     */
    public TransactionResponse getTransaction(String accountNumber, String transactionId) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.transaction.findbyid.duration")) {
            userValidation.validateUserAuthenticated();

            AccountEntity account = accountDAO.getAccount(accountNumber);
            accountValidation.validateAccountAccessibleByRequestor(account);

            TransactionEntity transaction = transactionDAO.getTransaction(transactionId);
            transactionValidation.validateTransactionAccessibleByRequestor(transaction, account);   

            return transactionResponse(transaction);
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    /**
     * Retrieves all transactions for a specific account, ensuring the user is authenticated
     * and authorized to access the account.
     *
     * @param accountNumber the account number
     * @return a ListTransactionsResponse containing the list of transactions for the account
     */
    public ListTransactionsResponse getTransactions(String accountNumber) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.transaction.findbyaccountid.duration")) {
            userValidation.validateUserAuthenticated();

            AccountEntity account = accountDAO.getAccount(accountNumber);
            accountValidation.validateAccountAccessibleByRequestor(account);

            List<TransactionEntity> transactions = transactionDAO.findTransactionsByAccountId(accountNumber);
            return new ListTransactionsResponse().setTransactions(transactions.stream()
                    .map(this::transactionResponse)
                    .toList());
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    /**
     * Creates a new transaction (deposit or withdrawal) for a specific account.
     * Validates authentication, authorization, and transaction details.
     *
     * @param accountNumber the account number
     * @param transactionRequest the transaction creation request
     * @return the created transaction as a TransactionResponse
     */
    public TransactionResponse createTransaction(String accountNumber, CreateTransactionRequest transactionRequest) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.transaction.deposit.duration")) {
            
            userValidation.validateUserAuthenticated();
            AccountEntity account = accountDAO.getAccount(accountNumber);
            accountValidation.validateAccountAccessibleByRequestor(account);

            return transactionResponse(executeTransaction(transactionRequest, account, AuthUtils.getUsername()));

        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    /**
     * Converts a TransactionEntity to a TransactionResponse.
     *
     * @param entity the TransactionEntity to convert
     * @return the corresponding TransactionResponse, or null if the entity is null
     */
    public TransactionResponse transactionResponse(TransactionEntity entity) {
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
     * Saves a transaction entity and updates the associated bank account balance.
     * Handles both deposit and withdrawal logic with validation.
     *
     * @param transactionRequest the request containing transaction details
     * @param account            the bank account associated with the transaction
     * @param userId             the ID of the user performing the transaction
     * @return the saved TransactionEntity
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    protected TransactionEntity executeTransaction(CreateTransactionRequest transactionRequest, AccountEntity account,
            String userId) {

        transactionValidation.validateTransactionRequest(transactionRequest);
        
        double balance = account.getBalance();
        if(transactionRequest.getType() == TransactionType.WITHDRAWAL) {
            transactionValidation.validateFundsAvailable(transactionRequest, account);
            balance = account.getBalance() - transactionRequest.getAmount();
        }else if(transactionRequest.getType() == TransactionType.DEPOSIT) {
            transactionValidation.validateTransactionAmount(transactionRequest);
            balance = account.getBalance() + transactionRequest.getAmount();
        }

        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(IdGenerator.generateTransactionId());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setCurrency(transactionRequest.getCurrency());
        transaction.setType(transactionRequest.getType());
        transaction.setReference(transactionRequest.getReference());
        transaction.setUserId(userId);
        transaction.setCreatedTimestamp(Instant.now());
        transaction.setAccountNumber(account.getAccountNumber());

        account.setBalance(balance);
        account.setUpdatedTimestamp(Instant.now());

        accountDAO.updateBankAccount(account);
        TransactionEntity newTransaction = transactionDAO.createTransaction(transaction);

        return newTransaction;
    }
}