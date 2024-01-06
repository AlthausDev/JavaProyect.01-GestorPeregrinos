package com.althaus.dev.GestorPeregrinos.service.impl;

import com.althaus.dev.GestorPeregrinos.config.SecurityContext;
import com.althaus.dev.GestorPeregrinos.model.AdminParada;
import com.althaus.dev.GestorPeregrinos.model.Credenciales;
import com.althaus.dev.GestorPeregrinos.model.User;
import com.althaus.dev.GestorPeregrinos.repository.CredencialesRepository;
import com.althaus.dev.GestorPeregrinos.service.CredencialesService;
import com.althaus.dev.GestorPeregrinos.util.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CredencialesServiceImpl extends CoreServiceImpl<Credenciales> implements CredencialesService {

    private final CredencialesRepository credencialesRepository;

    @Autowired
    public CredencialesServiceImpl(CredencialesRepository credencialesRepository) {
        super(credencialesRepository);
        this.credencialesRepository = credencialesRepository;
        log.info("CredencialesRepository inyectado correctamente");
    }


    /**
     * Verifica si existe un usuario con el nombre de usuario proporcionado.
     *
     * @param username El nombre de usuario a verificar.
     * @return `true` si existe un usuario con el nombre de usuario, `false` de lo contrario.
     */
    @Override
    public boolean existeUsuario(String username) {
        return credencialesRepository.existsByUsername(username);
    }

    /**
     * Verifica si existe una credencial con la contraseña proporcionada.
     *
     * @param password La contraseña a verificar.
     * @return `true` si existe una credencial con la contraseña, `false` de lo contrario.
     */
    @Override
    public boolean existePassword(String password) {
        return credencialesRepository.existsByPassword(password);
    }


    /**
     * Inicia sesión con las credenciales proporcionadas.
     *
     * @param username El nombre de usuario para iniciar sesión.
     * @param password La contraseña para iniciar sesión.
     * @return `true` si la sesión se inicia correctamente, `false` de lo contrario.
     */
    public boolean iniciarSesion(String username, String password) {
        try {
            Optional<Credenciales> optionalCredenciales = credencialesRepository.findByUsername(username);

            if (optionalCredenciales.isPresent()) {
                Credenciales credenciales = optionalCredenciales.get();
                System.out.println(credenciales);

                if (PasswordUtils.checkPassword(password, credenciales.getPassword())) {
                    User usuario = getUserFromCredentials(credenciales);

                    SecurityContext.setUsuarioAutenticado(usuario);
                    return true;
                }
            }
            return false;
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al acceder a la base de datos", e);
        }
    }

    /**
     * @param credenciales
     * @return
     */
    @Override
    public User getUserFromCredentials(Credenciales credenciales) {
        Optional <AdminParada> adminParada = adminParadaService.findById(credenciales.getId());
        if (adminParada.isPresent()) {
            return adminParada.get();
        } else
        return null;
    }

    public Long getLastId() {
        return credencialesRepository.findMaxId();
    }
}
