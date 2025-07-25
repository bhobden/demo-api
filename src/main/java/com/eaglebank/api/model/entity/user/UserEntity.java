package com.eaglebank.api.model.entity.user;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.eaglebank.api.model.entity.user.address.Address;

@Entity
@Table(name = "users") // Explicit table naming for clarity and compatibility
public class UserEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true, length = 32)
    private String id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Embedded
    private Address address;

    @Column(name = "phone_number", nullable = false, length = 32)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 128)
    private String email;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "created_timestamp", nullable = false)
    private LocalDateTime createdTimestamp;

    @Column(name = "updated_timestamp", nullable = false)
    private LocalDateTime updatedTimestamp;

    public String getId() {
        return id;
    }

    public UserEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public UserEntity setAddress(Address address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public UserEntity setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
        return this;
    }

    public LocalDateTime getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public UserEntity setUpdatedTimestamp(LocalDateTime updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserEntity that = (UserEntity) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(address, that.address)
                .append(phoneNumber, that.phoneNumber)
                .append(email, that.email)
                .append(password, that.password)
                .append(createdTimestamp, that.createdTimestamp)
                .append(updatedTimestamp, that.updatedTimestamp)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(address)
                .append(phoneNumber)
                .append(email)
                .append(password)
                .append(createdTimestamp)
                .append(updatedTimestamp)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .append("address", address)
                .append("phoneNumber", phoneNumber)
                .append("email", email)
                .append("password", "[PROTECTED]")
                .append("createdTimestamp", createdTimestamp)
                .append("updatedTimestamp", updatedTimestamp)
                .toString();
    }
}
