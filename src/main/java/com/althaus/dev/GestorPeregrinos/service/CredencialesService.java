package com.althaus.dev.GestorPeregrinos.service;

import com.althaus.dev.GestorPeregrinos.model.Credenciales;
import com.althaus.dev.GestorPeregrinos.model.User;
import org.springframework.stereotype.Service;

/**
 * Servicio para operaciones relacionadas con las credenciales de usuario.
 */
@Service
public interface CredencialesService extends CoreService<Credenciales> {

    /**
     * Verifica si existe un usuario con el nombre de usuario dado.
     *
     * @param username Nombre de usuario a verificar.
     * @return true si existe, false de lo contrario.
     */
    boolean existeUsuario(String username);

    /**
     * Verifica si existen credenciales asociadas a la contraseña proporcionada.
     *
     * @param password Contraseña a verificar.
     * @return true si existen, false de lo contrario.
     */
    boolean existePassword(String password);

    /**
     * Intenta iniciar sesión con el nombre de usuario y la contraseña proporcionados.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @return true si la autenticación es exitosa, false de lo contrario.
     * @throws RuntimeException Si hay un error al acceder a la base de datos.
     */
    boolean iniciarSesion(String username, String password);

    /**
     * Registra un nuevo usuario con las credenciales proporcionadas.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @param user     Usuario asociado a las credenciales.
     * @return Las credenciales registradas.
     */
    Credenciales registrarNuevoUsuario(String username, String password, User user);
}
