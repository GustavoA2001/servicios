package com.servicios.registro_service.model;

import com.servicios.registro_service.model.DTOUsuario;
import java.util.LinkedList;

public interface CRUDUsuarios {
    public LinkedList<DTOUsuario> ListarUsuarios();
    DTOUsuario ValidarSesion(String correo, String contra);
}
