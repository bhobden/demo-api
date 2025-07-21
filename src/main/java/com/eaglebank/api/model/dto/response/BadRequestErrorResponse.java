package com.eaglebank.api.model.dto.response;

import java.util.List;

public class BadRequestErrorResponse extends ErrorResponse {

    private List<Detail> details;

    public static class Detail {
        private String field;
        private String message;
        private String type;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public List<Detail> getDetails() {
        return details;
    }

    public BadRequestErrorResponse setDetails(List<Detail> details) {
        this.details = details;
        return this;
    }
}