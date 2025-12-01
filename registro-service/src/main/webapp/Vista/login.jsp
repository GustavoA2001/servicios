<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login | NOVA'S TRAVELS</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link rel="icon" href="../Imagenes/novas_logo.png">

        <style>
            body {
                background-color: #E6EEF5;
            }

            /* Caja del formulario */
            .login-box {
                background-color: #FAF3E0;
                padding: 28px;
                border-radius: 12px;
                box-shadow: 0 6px 18px rgba(0,0,0,0.12);
                width: 100%;
                max-width: 660px;
            }

            /* Logo: controlamos tamaños en distintos breakpoints */
            .login-logo {
                width: 100%;
                height: auto;
                display: block;
                max-width: 620px;     /* escritorio/tablet */
            }
            @media (max-width: 768px) {
                .login-logo {
                    max-width: 220px;
                }   /* tablet/móvil grande */
            }
            @media (max-width: 420px) {
                .login-logo {
                    max-width: 160px;
                }   /* móviles pequeños: evita que sea gigante */
            }

            /* Pequeños ajustes visuales */
            .btn-login {
                background-color: #f5be0a;
                border: none;
                color: #fff;
                font-weight: 600;
            }
            .btn-login:hover {
                background-color: #e6a800;
            }

            /* Aseguramos buena separación en vh-100 (centrado vertical) pero con padding extra arriba en móvil */
            .center-vertical {
                min-height: 100vh;
            }
            @media (max-width: 576px) {
                .center-vertical {
                    padding-top: 3rem;
                    padding-bottom: 3rem;
                    min-height: auto;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="row align-items-center justify-content-center center-vertical gx-4">
                <!-- Logo arriba en móviles (order-1), en escritorio ocupa la columna izquierda (order-md-1) -->
                <div class="col-12 col-md-5 d-flex justify-content-center mb-4 mb-md-0 order-1">
                    <img src="../Imagenes/novas_logo.png" alt="NOVA'S TRAVELS" class="login-logo" onerror="this.src='../Imagenes/novas_logo.png'">
                </div>

                <!-- Formulario: en móvil aparece debajo (order-2), en md+ derecha (order-md-2) -->
                <div class="col-12 col-md-7 d-flex justify-content-center order-2">       
                    <div class="login-box">
                        <h3 class="text-center mb-3">Iniciar sesión</h3>

                        <form method="post" action="../srvIniciarSesion?accion=verificar" id="loginForm" novalidate>
                            <div class="mb-3">
                                <label for="username" class="form-label">Correo</label>
                                <input type="email" id="username" name="txtCorreo" class="form-control" autocomplete="username" required>
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label">Contraseña</label>
                                <input type="password" id="password" name="txtPassword" class="form-control" autocomplete="current-password" required>
                            </div>

                            <div class="d-grid mb-3">
                                <button class="btn btn-login btn-lg" type="submit">Inicia Sesión</button>
                            </div>

                            <% String codigo = (String) session.getAttribute("codigo2FA");
                                Long expira = (Long) session.getAttribute("codigoExpira");
                                if (codigo != null && expira != null) {
                                    long remSeg = (expira - System.currentTimeMillis()) / 1000;
                                    if (remSeg < 0)
                                        remSeg = 0;
                            %>
                            <div class="mb-3">
                                <a href="verificarCodigo.jsp" class="btn btn-outline-primary w-100">Ingresar código — tiempo restante: <%= remSeg%> s</a>
                            </div>
                            <% }%>

                            <div class="d-flex justify-content-between small">
                                <a href="registro.jsp">¿No tienes cuenta? Registrarse</a>
                                <a href="recuperar.jsp">Olvidé mi contraseña</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Scripts -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

        <!-- Reutilizo tu código de SweetAlert para mensajes -->
        <script>
            (function () {
                const params = new URLSearchParams(window.location.search);
                const err = params.get('error');
                const registro = params.get('registro');
                const recup = params.get('recup');

                if (registro === 'ok') {
                    Swal.fire({icon: 'success', title: 'Registro exitoso', text: 'Cuenta creada correctamente. Ahora ingresa con tu correo y contraseña.', timer: 3500, showConfirmButton: false});
                    return;
                }
                if (recup === 'ok') {
                    Swal.fire({icon: 'success', title: 'Contraseña cambiada', text: 'Tu contraseña fue actualizada. Ingresa de nuevo.', timer: 3000, showConfirmButton: false});
                    return;
                }

                if (!err)
                    return;
                switch (err) {
                    case 'cred':
                        Swal.fire({icon: 'error', title: 'Credenciales inválidas', text: 'Correo o contraseña incorrectos, o la cuenta está inactiva.'});
                        break;
                    case 'mail':
                        Swal.fire({icon: 'error', title: 'Error enviando código', text: 'No se pudo enviar el correo con el código. Intenta más tarde.'});
                        break;
                    case 'ex':
                    case 'ex2':
                        Swal.fire({icon: 'error', title: 'Error', text: 'Ocurrió un error inesperado. Revisa el log del servidor.'});
                        break;
                    default:
                        Swal.fire({icon: 'info', title: 'Información', text: 'Revisa los datos e intenta nuevamente.'});
                }
            })();
        </script>
    </body>
</html>
