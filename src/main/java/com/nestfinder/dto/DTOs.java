package com.nestfinder.dto;

import lombok.Data;

public class DTOs {

    @Data
    public static class StudentRegisterRequest {
        private String name;
        private String email;
        private String password;
        private String phone;
        private String college;
    }

    @Data
    public static class OwnerRegisterRequest {
        private String name;
        private String email;
        private String password;
        private String phone;
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
        private String role;
    }

    @Data
    public static class LoginResponse {
        private String token;
        private String role;
        private String name;
        private Long id;

        public LoginResponse(String token, String role, String name, Long id) {
            this.token = token;
            this.role = role;
            this.name = name;
            this.id = id;
        }
    }

    @Data
    public static class PGHostelRequest {
        private String name;
        private String address;
        private String city;
        private String locality;
        private Double price;
        private String type;
        private String gender;
        private String amenities;
        private String description;
        private Integer totalRooms;
        private Integer availableRooms;
        private Long ownerId;
    }

    @Data
    public static class BookingRequest {
        private Long studentId;
        private Long pgId;
        private String checkInDate;
        private Integer months;
    }

    @Data
    public static class ReviewRequest {
        private Long studentId;
        private Long pgId;
        private Integer rating;
        private String comment;
    }
}
