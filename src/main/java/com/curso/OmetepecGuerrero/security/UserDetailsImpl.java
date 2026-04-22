package com.curso.OmetepecGuerrero.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.curso.OmetepecGuerrero.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {

    private Integer id;
    private String correo;
    private String password;
    private String nombre;
    private String rol;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(Usuario usuario) {
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name().toUpperCase())
        );

        return UserDetailsImpl.builder()
                .id(usuario.getId())
                .correo(usuario.getCorreo())
                .password(usuario.getPassHash())
                .nombre(usuario.getNombre())
                .rol(usuario.getRol().name())
                .authorities(authorities)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
