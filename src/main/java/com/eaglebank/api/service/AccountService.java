package com.eaglebank.api.service;

import com.eaglebank.api.metrics.MetricScope;
import com.eaglebank.api.metrics.MetricScopeFactory;
import com.eaglebank.api.model.dto.request.CreateBankAccountRequest;
import com.eaglebank.api.model.dto.request.UpdateBankAccountRequest;
import com.eaglebank.api.model.dto.response.BankAccountResponse;
import com.eaglebank.api.model.dto.response.ListBankAccountsResponse;
import com.eaglebank.api.model.entity.bankaccount.BankAccountEntity;
import com.eaglebank.api.model.entity.bankaccount.Currency;
import com.eaglebank.api.security.AuthUtils;
import com.eaglebank.api.security.IdGenerator;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Service class for managing bank accounts.
 * <p>
 * Provides methods for creating, listing, fetching, updating, and deleting
 * accounts, with metrics and validation for each operation. All methods ensure the
 * authenticated user is authorized to perform the requested operation.
 * </p>
 */
@Service
public class AccountService extends AbstractService {

    /**
     * Creates a new bank account for the authenticated user.
     *
     * @param request The request containing account details.
     * @return BankAccountResponse containing the created account's details.
     */
    public BankAccountResponse createAccount(CreateBankAccountRequest request) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.account.create.duration")) {
            userValidation.validateUserAuthenticated();
            return createAccountForUser(request, AuthUtils.getUsername());
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Creates a new bank account for a specific user.
     *
     * @param request The request containing account details.
     * @param userId  The ID of the user who will own the account.
     * @return BankAccountResponse containing the created account's details.
     */
    public BankAccountResponse createAccountForUser(CreateBankAccountRequest request, String userId) {
        accountValidation.validateNewAccount(request);

        BankAccountEntity account = new BankAccountEntity()
                .setAccountNumber(IdGenerator.generateAccountNumber())
                .setSortCode(IdGenerator.generateSortCode())
                .setAccountName(request.getAccountName())
                .setAccountType(request.getAccountType())
                .setCurrency(Currency.GBP)
                .setBalance(0.0)
                .setCreatedTimestamp(Instant.now())
                .setUpdatedTimestamp(Instant.now())
                .setOwnerUsername(userId);

        account = accountDAO.createBankAccount(account);
        return accountResponse(account);
    }

    /**
     * Lists all bank accounts for the authenticated user.
     *
     * @return ListBankAccountsResponse containing all accounts for the user.
     */
    public ListBankAccountsResponse getAccounts() {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.account.list.duration")) {
            userValidation.validateUserAuthenticated();

            List<BankAccountEntity> accounts = accountDAO.getUsersAccount(AuthUtils.getUsername());
            List<BankAccountResponse> responseList = accounts.stream()
                    .map(this::accountResponse)
                    .toList();

            return new ListBankAccountsResponse().setAccounts(responseList);
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Fetches a bank account by account number for the authenticated user.
     *
     * @param accountNumber The account number to fetch.
     * @return BankAccountResponse containing the account's details.
     */
    public BankAccountResponse getAccount(String accountNumber) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.account.fetch.duration")) {
            BankAccountEntity account = fetchAndValidateAccount(accountNumber);
            return accountResponse(account);
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Updates a bank account by account number for the authenticated user.
     *
     * @param accountNumber The account number to update.
     * @param updateRequest The request containing updated account fields.
     * @return BankAccountResponse with updated account details.
     */
    public BankAccountResponse updateAccount(String accountNumber, UpdateBankAccountRequest updateRequest) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.account.update.duration")) {
            BankAccountEntity account = fetchAndValidateAccount(accountNumber);

            if (updateRequest.getAccountName() != null
                    && !updateRequest.getAccountName().equals(account.getAccountName())) {
                accountValidation.validateAccountName(updateRequest.getAccountName());
                account.setAccountName(updateRequest.getAccountName());
            }
            if (updateRequest.getAccountType() != null
                    && !updateRequest.getAccountType().equals(account.getAccountType())) {
                account.setAccountType(updateRequest.getAccountType());
            }

            account.setUpdatedTimestamp(Instant.now());
            account = accountDAO.updateBankAccount(account);
            return accountResponse(account);
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Deletes a bank account by account number for the authenticated user.
     *
     * @param accountNumber The account number to delete.
     */
    public void deleteAccount(String accountNumber) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.account.delete.duration")) {
            BankAccountEntity account = fetchAndValidateAccount(accountNumber);
            accountDAO.deleteBankAccount(account);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Validates that the authenticated user can access the account and fetches it.
     *
     * @param accountNumber The account number to validate and fetch.
     * @return BankAccountEntity if accessible.
     */
    public BankAccountEntity fetchAndValidateAccount(String accountNumber) {
        userValidation.validateUserAuthenticated();
        BankAccountEntity account = accountDAO.getAccount(accountNumber);
        accountValidation.validateAccountAccessibleByRequestor(account);
        return account;
    }

    /**
     * Helper method to build a BankAccountResponse from a BankAccountEntity.
     *
     * @param account The BankAccountEntity to convert.
     * @return BankAccountResponse with mapped fields.
     */
    private BankAccountResponse accountResponse(BankAccountEntity account) {
        BankAccountResponse response = new BankAccountResponse();
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountName(account.getAccountName());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        response.setSortCode(account.getSortCode());
        response.setCurrency(account.getCurrency());
        // Convert timestamps to LocalDateTime in UTC as per spec.
        response.setCreatedTimestamp(convertInstantToLocalDateTimeUTC(account.getCreatedTimestamp()));
        response.setUpdatedTimestamp(convertInstantToLocalDateTimeUTC(account.getUpdatedTimestamp()));
        return response;
    }
}
