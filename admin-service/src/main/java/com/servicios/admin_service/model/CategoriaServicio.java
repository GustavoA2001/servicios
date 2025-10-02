package com.servicios.admin_service.model;

public class CategoriaServicio {
    
    private int CategoriaServicioID;
    private String nomb_Categoria;
    private String descrip_Categoria;

    public CategoriaServicio () {    }

    public void setCategoriaServicioID(int CategoriaServicioID) {
        this.CategoriaServicioID = CategoriaServicioID;
    }
    public int getCategoriaServicioID() {
        return CategoriaServicioID;
    }

    public void setNombreCategoria(String nomb_Categoria) {
        this.nomb_Categoria = nomb_Categoria;
    }
    public String getNombreCategoria() {
        return nomb_Categoria;
    }

    public void setDescripcionCategoria(String descrip_categoria) {
        this.descrip_Categoria = descrip_categoria;
    }
    public String getDescripcionCategoria() {
        return descrip_Categoria;
    }

}
