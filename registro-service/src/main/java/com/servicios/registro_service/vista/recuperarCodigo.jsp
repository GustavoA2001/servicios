<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <title>Verificar código | NOVA'S TRAVELS</title>

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
                        <h3 class="text-center mb-3">Verificar código</h3>
                        <p>Hemos enviado un código de 6 dígitos a tu correo.</p>

                        <form method="post" action="../srvIniciarSesion?accion=verificarRecuperacion" id="formCod" novalidate>
                            <div class="mb-3">
                                <label class="form-label">Código (6 dígitos)</label>
                                <input name="txtCodigoRecup" type="text" maxlength="6" pattern="[0-9]{6}" inputmode="numeric" class="form-control" required autofocus>
                            </div>
                            <div class="d-grid mb-2">
                                <button class="btn btn-primary-custom w-100" type="submit">Verificar</button>
                            </div>

                            <div class="d-flex justify-content-between">
                                <a href="recuperar.jsp">Repetir correo</a>
                                <form method="post" action="../srvIniciarSesion?accion=solicitarRecuperacion" style="display:inline;">
                                    <button type="submit" class="btn btn-link p-0">Reenviar código</button>
                                </form>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            (function () {
                const params = new URLSearchParams(window.location.search);
                const err = params.get('error');
                if (err === 'cod')
                    Swal.fire({icon: 'error', title: 'Código inválido', text: 'Código incorrecto o expirado.'});
                if (err === 'mail')
                    Swal.fire({icon: 'error', title: 'Error envío', text: 'No se pudo enviar el correo.'});
                if (err === 'blocked')
                    Swal.fire({icon: 'warning', title: 'Bloqueado', text: 'Demasiados intentos.'});

                const form = document.getElementById('formCod');
                form.addEventListener('submit', function (e) {
                    const val = form.querySelector('input[name="txtCodigoRecup"]').value.trim();
                    if (!/^\d{6}$/.test(val)) {
                        e.preventDefault();
                        e.stopPropagation();
                        Swal.fire({icon: 'warning', title: 'Código inválido', text: 'Ingresa 6 números.'});
                    }
                });
            })();
        </script>
    </body>
</html>
