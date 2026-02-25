package com.comercio.comercio_auth.auth.dto;

import com.comercio.comercio_auth.user.UserRole;

public record RegisterDTO(String email, String password, UserRole role) {

}
