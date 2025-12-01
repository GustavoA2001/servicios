<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <title>Recuperar contraseña | NOVA'S TRAVELS</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link rel="icon" href="../Imagenes/novas_logo.png">

        <style>
            body {
                background-color:#E6EEF5;
            }
            .login-box{
                background:#FAF3E0;
                padding:32px;
                border-radius:12px;
                box-shadow:0 6px 18px rgba(0,0,0,0.12);
                width:100%;
                max-width:720px;
            }
            .login-logo{
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
            .btn-login{
                background:#f5be0a;
                border:none;
                color:#fff;
                font-weight:600;
            }
            .btn-login:hover{
                background:#e6a800;
            }
            .center-vertical{
                min-height:100vh;
            }
            @media (max-width:576px){
                .center-vertical{
                    padding-top:2rem;
                    min-height:auto;
                }
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row align-items-center justify-content-center center-vertical gx-4">
                <div class="col-12 col-md-5 d-flex justify-content-center mb-4 mb-md-0">
                    <img src="../Imagenes/novas_logo.png" class="login-logo" alt="logo">
                </div>

                <div class="col-12 col-md-7 d-flex justify-content-center">
                    <div class="login-box">
                        <h3 class="text-center mb-3">Recuperar contraseña</h3>
                        <p>Introduce el correo asociado a tu cuenta. Te enviaremos un código de 6 dígitos.</p>

                        <form method="post" action="../srvIniciarSesion?accion=solicitarRecuperacion" id="formRecup" novalidate>
                            <div class="mb-3">
                                <label class="form-label">Correo</label>
                                <input name="txtCorreoRecup" type="email" class="form-control" autocomplete="email" required>
                            </div>
                            <div class="d-grid">
                                <button class="btn btn-login w-100" type="submit">Enviar código</button>
                            </div>
                        </form>

                        <div class="mt-3">
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
                if (err === 'noexist')
                    Swal.fire({icon: 'error', title: 'No existe', text: 'No encontramos una cuenta con ese correo.'});
                if (err === 'mail')
                    Swal.fire({icon: 'error', title: 'Error envío', text: 'No se pudo enviar el correo. Intenta más tarde.'});
                if (err === 'ex')
                    Swal.fire({icon: 'error', title: 'Error', text: 'Error inesperado. Revisa logs.'});
                if (err === 'exp')
                    Swal.fire({icon: 'warning', title: 'Código expirado', text: 'Solicita un nuevo código.'});
                if (err === 'blocked')
                    Swal.fire({icon: 'warning', title: 'Bloqueado', text: 'Demasiados intentos. Solicita un nuevo código.'});
            })();
        </script>
    </body>
</html>
