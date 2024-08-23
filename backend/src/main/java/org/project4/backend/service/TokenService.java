package org.project4.backend.service;


import org.project4.backend.dto.TokenDTO;

public interface TokenService {
    void  saveToken(String token);
    void  logoutToken(Long id);
    TokenDTO getByid(Long id);
    Boolean validateToken(String token);
    Boolean getByToken(String token);
}
