<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <title>Nueva contraseña | NOVA'S TRAVELS</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link rel="icon" href="../Imagenes/novas_logo.png">

        <style>
            body {
                background-color: #E6EEF5;
            }
            .login-box {
                background-color: #FAF3E0;
                padding: 32px;
                border-radius: 12px;
                box-shadow: 0 6px 18px rgba(0,0,0,0.12);
                width: 100%;
                max-width: 720px;
            }
            .login-logo {
                width:100%;
                height:auto;
                max-width:420px;
                display:block;
            }
            @media (max-width:768px){
                .login-logo{
                    max-width:220px;
                }
            }
            @media (max-width:420px){
                .login-logo{
                    max-width:160px;
                }
            }
            .btn-primary-custom{
                background:#f5be0a;
                border:none;
                color:#fff;
                font-weight:600;
            }
            .btn-primary-custom:hover{
                background:#e6a800;
            }
            .center-vertical{
                min-height:100vh;
            }
            @media (max-width:576px){
                .center-vertical{
                    padding-top:2rem;
                    padding-bottom:2rem;
                    min-height:auto;
                }
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row align-items-center justify-content-center center-vertical gx-4">
                <!-- Logo -->
                <div class="col-12 col-md-5 d-flex justify-content-center mb-4 mb-md-0 order-1">
                    <img src="../Imagenes/novas_logo.png" class="login-logo" alt="NOVA'S TRAVELS Logo" onerror="this.src='../Imagenes/novas_logo.png'">
                </div>

                <!-- Form -->
                <div class="col-12 col-md-7 d-flex justify-content-center order-2">
                    <div class="login-box">
                        <h3 class="text-center mb-3">Nueva contraseña</h3>
                        <p>Ingresa tu nueva contraseña.</p>

                        <form method="post" action="../srvIniciarSesion?accion=cambiarPassword" id="formPass" novalidate>
                            <div class="mb-3">
                                <label class="form-label">Contraseña</label>
                                <input name="password1" type="password" minlength="6" class="form-control" autocomplete="new-password" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Confirmar contraseña</label>
                                <input name="password2" type="password" minlength="6" class="form-control" autocomplete="new-password" required>
                            </div>
                            <div class="d-grid mb-2">
                                <button class="btn btn-primary-custom w-100" type="submit">Cambiar contraseña</button>
                            </div>
                        </form>

                        <div class="mt-2">
                            <a href="login.jsp">Volver al inicio</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            (function () {
                const params = new URLSearchParams(window.location.search);
                const err = params.get('error');
                if (err === 'match')
                    Swal.fire({icon: 'error', title: 'No coinciden', text: 'Las contraseñas no coinciden.'});
                if (err === 'short')
                    Swal.fire({icon: 'warning', title: 'Corta', text: 'La contraseña debe tener al menos 6 caracteres.'});
                if (err === 'bd' || err === 'ex')
                    Swal.fire({icon: 'error', title: 'Error', text: 'Ocurrió un error. Intenta más tarde.'});

                const form = document.getElementById('formPass');
                form.addEventListener('submit', function (e) {
                    const p1 = form.querySelector('input[name="password1"]').value;
                    const p2 = form.querySelector('input[name="password2"]').value;
                    if (p1.length < 6) {
                        e.preventDefault();
                        e.stopPropagation();
                        Swal.fire({icon: 'warning', title: 'Corta', text: 'La contraseña debe tener al menos 6 caracteres.'});
                        return;
                    }
                    if (p1 !== p2) {
                        e.preventDefault();
                        e.stopPropagation();
                        Swal.fire({icon: 'error', title: 'No coinciden', text: 'Las contraseñas deben coincidir.'});
                    }
                });
            })();
        </script>
    </body>
</html>
