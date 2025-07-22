package com.eaglebank.api.model.dto.response;

import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * DTO representing a list of transaction responses.
 * <p>
 * Used to return a collection of transactions for an account or user.
 * </p>
 */
public class ListTransactionsResponse {
    private List<TransactionResponse> transactions;

    /**
     * Gets the list of transactions.
     * @return the list of TransactionResponse objects
     */
    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    /**
     * Sets the list of transactions.
     * @param transactions the list of TransactionResponse objects
     */
    public ListTransactionsResponse setTransactions(List<TransactionResponse> transactions) {
        this.transactions = transactions;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListTransactionsResponse)) return false;
        ListTransactionsResponse that = (ListTransactionsResponse) o;
        return new EqualsBuilder()
                .append(transactions, that.transactions)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(transactions)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("transactions", transactions)
                .toString();
    }
}