package com.servicios.login_service.model;

import com.servicios.login_service.model.DTOUsuario;
import java.util.LinkedList;

public interface CRUDUsuarios {
    public LinkedList<DTOUsuario> ListarUsuarios();
    DTOUsuario ValidarSesion(String correo, String contra);
}
