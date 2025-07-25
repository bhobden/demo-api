package com.eaglebank.api.model.entity.user.address;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents a postal address for a user.
 * <p>
 * Contains address lines, town, county, and postcode.
 * </p>
 */
public class Address {
    private String line1;
    private String line2;
    private String line3;
    private String town;
    private String county;
    private String postcode;

    public Address() {}

    public String getLine1() {
        return line1;
    }

    public Address setLine1(String line1) {
        this.line1 = line1;
        return this;
    }

    public String getLine2() {
        return line2;
    }

    public Address setLine2(String line2) {
        this.line2 = line2;
        return this;
    }

    public String getLine3() {
        return line3;
    }

    public Address setLine3(String line3) {
        this.line3 = line3;
        return this;
    }

    public String getTown() {
        return town;
    }

    public Address setTown(String town) {
        this.town = town;
        return this;
    }

    public String getCounty() {
        return county;
    }

    public Address setCounty(String county) {
        this.county = county;
        return this;
    }

    public String getPostcode() {
        return postcode;
    }

    public Address setPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return new EqualsBuilder()
                .append(line1, address.line1)
                .append(line2, address.line2)
                .append(line3, address.line3)
                .append(town, address.town)
                .append(county, address.county)
                .append(postcode, address.postcode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(line1)
                .append(line2)
                .append(line3)
                .append(town)
                .append(county)
                .append(postcode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("line1", line1)
                .append("line2", line2)
                .append("line3", line3)
                .append("town", town)
                .append("county", county)
                .append("postcode", postcode)
                .toString();
    }
}
