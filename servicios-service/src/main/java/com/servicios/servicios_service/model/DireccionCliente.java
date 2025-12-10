package com.servicios.servicios_service.model;

public class DireccionCliente {
        /*
         * DIRECCION 
         */
        int direccionID;
        String direccion;
        String referencia;
        String estadoDireccion;
    
        int clienteid;
        int distritoid;
        /*
         * DISTRITO
         */
        private Distrito distrito; 
        // composición con el DISTRITO, una dirección tiene un distrito
    
        /*
         * CONSTRUCTORES
         */
        public DireccionCliente() { }
    
        public DireccionCliente(int direccionID, String direccion, String referencia, String estadoDireccion) {
            this.direccionID = direccionID;
            this.direccion = direccion;
            this.referencia = referencia;
            this.estadoDireccion = estadoDireccion;
        }
    
        // Getters y Setters
        public int getDireccionID() { return direccionID; }
        public void setDireccionID(int direccionID) { this.direccionID = direccionID; }
    
        public String getDireccion() { return direccion; }
        public void setDireccion(String direccion) { this.direccion = direccion; }
    
        public String getReferencia() { return referencia; }
        public void setReferencia(String referencia) { this.referencia = referencia; }
    
        public String getEstadoDireccion() { return estadoDireccion; }
        public void setEstadoDireccion(String estadoDireccion) { this.estadoDireccion = estadoDireccion; }
    
        public Distrito getDistrito() { return distrito; }
        public void setDistrito(Distrito distrito) { this.distrito = distrito; }
        
        public int getClienteID() { return clienteid; }
        public void setClienteID(int clienteid) { this.clienteid = clienteid; }

        public int getDistritoID() { return distritoid; }
        public void setDistritoID(int distritoid) { this.distritoid = distritoid; }
    }
    