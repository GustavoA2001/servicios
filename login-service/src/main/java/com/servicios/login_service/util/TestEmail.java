/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.servicios.login_service.util;

public class TestEmail {
    public static void main(String[] args) {
        try {
            EmailUtil.enviarCodigoVerificacion("pierrekrrera@gmail.com", "12345");
            System.out.println("Email enviado OK");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Fallo enviando email: " + e.getMessage());
        }
    }
}

