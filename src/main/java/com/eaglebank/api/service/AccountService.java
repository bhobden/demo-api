package com.eaglebank.api.service;

import com.eaglebank.api.dao.AccountDAO;
import com.eaglebank.api.metrics.MetricScope;
import com.eaglebank.api.metrics.MetricScopeFactory;
import com.eaglebank.api.model.BankAccountEntity;
import com.eaglebank.api.model.dto.request.CreateBankAccountRequest;
import com.eaglebank.api.model.dto.request.UpdateBankAccountRequest;
import com.eaglebank.api.model.dto.response.BankAccountResponse;
import com.eaglebank.api.model.dto.response.ListBankAccountsResponse;
import com.eaglebank.api.security.AuthUtils;
import com.eaglebank.api.security.IdGenerator;
import com.eaglebank.api.validation.AccountValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class AccountService extends AbstractService {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private AccountValidation accountValidation;

    /**
     * Creates a new bank account for the authenticated user.
     */
    public BankAccountResponse createAccount(CreateBankAccountRequest request) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.account.create.duration")) {

            userValidation.validateUserAuthenticated();

            BankAccountEntity account = new BankAccountEntity()
                    .setAccountNumber(IdGenerator.generateAccountNumber())
                    .setSortCode(IdGenerator.generateSortCode())
                    .setAccountName(request.getAccountName())
                    .setAccountType(request.getAccountType())
                    .setBalance(0.0)
                    .setCreatedTimestamp(Instant.now())
                    .setUpdatedTimestamp(Instant.now())
                    .setOwnerUsername(AuthUtils.getUsername());
            account = accountDAO.createBankAccount(account);

            return buildBankAccountResponse(account);
        }
    }

    /**
     * Lists all bank accounts for the authenticated user.
     */
    public ListBankAccountsResponse listAccounts() {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.account.list.duration")) {

            userValidation.validateUserAuthenticated();

            List<BankAccountEntity> accounts = accountDAO.getUsersAccount(AuthUtils.getUsername());
            List<BankAccountResponse> responseList = accounts.stream().map(account -> {
                BankAccountResponse r = buildBankAccountResponse(account);
                return r;
            }).toList();

            return new ListBankAccountsResponse().setAccounts(responseList);
        }
    }

    private BankAccountResponse buildBankAccountResponse(BankAccountEntity account) {
        BankAccountResponse response = new BankAccountResponse();
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountName(account.getAccountName());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        response.setSortCode(account.getSortCode());
        response.setCurrency(account.getCurrency());
        // Convert timestamps to LocalDateTime in UTC as per spec but would consider
        // using instance/epoch for more consistency.. plus i should do a load of null
        // checking and abstract this to its own method.
        response.setCreatedTimestamp(LocalDateTime.ofInstant(account.getCreatedTimestamp(), ZoneId.of("UTC")));
        response.setUpdatedTimestamp(LocalDateTime.ofInstant(account.getUpdatedTimestamp(), ZoneId.of("UTC")));
        return response;
    }

    /**
     * Deletes a bank account by account number for the authenticated user.
     */
    public void deleteAccount(String accountNumber) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.account.delete.duration")) {

            userValidation.validateUserAuthenticated();

            BankAccountEntity account = accountDAO.getAccount(accountNumber);

            accountValidation.validateAccountAccessibleByUser(AuthUtils.getUsername(), account);

            accountDAO.deleteBankAccount(account);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Fetches a bank account by account number for the authenticated user.
     */
    public BankAccountResponse getAccount(String accountNumber) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.account.fetch.duration")) {

            userValidation.validateUserAuthenticated();

            BankAccountEntity account = accountDAO.getAccount(accountNumber);

            accountValidation.validateAccountAccessibleByUser(AuthUtils.getUsername(), account);

            return buildBankAccountResponse(account);
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Updates a bank account by account number for the authenticated user.
     */
    public BankAccountResponse updateAccount(String accountNumber,
            UpdateBankAccountRequest updateRequest) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.account.update.duration")) {
            userValidation.validateUserAuthenticated();

            BankAccountEntity account = accountDAO.getAccount(accountNumber);
            accountValidation.validateAccountAccessibleByUser(AuthUtils.getUsername(), account);

            if (updateRequest.getAccountName() != null) {
                account.setAccountName(updateRequest.getAccountName());
            }
            if (updateRequest.getAccountType() != null) {
                account.setAccountType(updateRequest.getAccountType());
            }

            account.setUpdatedTimestamp(Instant.now());

            account = accountDAO.updateBankAccount(account);
            return buildBankAccountResponse(account);
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }
}
