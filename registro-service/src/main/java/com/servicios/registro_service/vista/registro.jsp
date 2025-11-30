<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Registro | NOVA'S TRAVELS</title>

        <!-- Bootstrap + Boxicons + jQuery + SweetAlert -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <link rel="icon" href="../Imagenes/novas_logo.png">

        <style>
            body {
                background-color: #E6EEF5;
            }
            .box {
                background-color: #FAF3E0;
                padding: 32px;
                border-radius: 12px;
                box-shadow: 0 6px 18px rgba(0,0,0,0.12);
                width:100%;
                max-width: 760px;
            }
            .brand-logo {
                width:100%;
                height:auto;
                max-width:420px;
                display:block;
            }
            @media (max-width:768px){
                .brand-logo{
                    max-width:220px;
                }
            }
            .btn-primary-custom {
                background-color: #f5be0a;
                border: none;
                color: #fff;
                font-weight: 600;
            }
            .btn-primary-custom:hover {
                background-color: #e6a800;
            }
            .form-label {
                font-weight: 500;
            }
            .pw-check {
                font-size: 0.95rem;
            }
            .pw-check i {
                width: 1.15rem;
                display:inline-block;
                text-align:center;
            } /* espacio para icono */
            .center-vertical {
                min-height: 100vh;
            }
            @media (max-width:576px){
                .center-vertical{
                    padding-top:1.5rem;
                    padding-bottom:1.5rem;
                    min-height:auto;
                }
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row align-items-center justify-content-center center-vertical gx-4">
                <!-- Logo (col izquierda en escritorio, arriba en móvil) -->
                <div class="col-12 col-md-5 d-flex justify-content-center mb-4 mb-md-0">
                    <img src="../Imagenes/novas_logo.png" class="brand-logo" alt="NOVA'S TRAVELS Logo" onerror="this.src='../Imagenes/novas_logo.png'">
                </div>

                <!-- Form -->
                <div class="col-12 col-md-7 d-flex justify-content-center">
                    <div class="box">
                        <h2 class="text-center mb-4">Registro de usuario</h2>

                        <form method="post" action="../srvIniciarSesion?accion=registrar" id="registroForm" novalidate>
                            <div class="row mb-2">
                                <div class="col">
                                    <label class="form-label">Apellido paterno</label>
                                    <input name="appat" type="text" class="form-control" required>
                                </div>
                                <div class="col">
                                    <label class="form-label">Apellido materno</label>
                                    <input name="apmat" type="text" class="form-control">
                                </div>
                            </div>

                            <div class="row mb-2">
                                <div class="col">
                                    <label class="form-label">Nombres</label>
                                    <input name="nombre" type="text" class="form-control" required>
                                </div>
                                <div class="col">
                                    <label class="form-label">DNI</label>
                                    <input name="dni" type="number" class="form-control" required>
                                </div>
                            </div>

                            <div class="row mb-2">
                                <div class="col">
                                    <label class="form-label">Correo</label>
                                    <input name="email" type="email" class="form-control" autocomplete="email" required>
                                </div>
                            </div>

                            <!-- Contraseña + ver -->
                            <div class="row mb-3">
                                <div class="col-12 mb-2">
                                    <label class="form-label">Contraseña</label>
                                    <div class="input-group">
                                        <input id="password" name="password" type="password" class="form-control" minlength="8" autocomplete="new-password" required>
                                        <button id="togglePwd" class="btn btn-outline-secondary" type="button" aria-label="Mostrar contraseña">
                                            <i class='bx bx-hide'></i>
                                        </button>
                                    </div>
                                    <div class="form-text">Mínimo 8 caracteres. Usa mayúsculas, minúsculas y números o símbolos.</div>
                                </div>

                                <div class="col-12">
                                    <label class="form-label">Confirmar contraseña</label>
                                    <div class="input-group">
                                        <input id="password2" name="password2" type="password" class="form-control" minlength="8" autocomplete="new-password" required>
                                        <button id="togglePwd2" class="btn btn-outline-secondary" type="button" aria-label="Mostrar confirmación">
                                            <i class='bx bx-hide'></i>
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <!-- Checklist en vivo -->
                            <div class="mb-3">
                                <ul class="list-unstyled mb-0">
                                    <li class="pw-check" id="chk-len"><i class="me-1 text-danger">✖</i> Mínimo 8 caracteres</li>
                                    <li class="pw-check" id="chk-upper"><i class="me-1 text-danger">✖</i> Al menos una letra mayúscula (A-Z)</li>
                                    <li class="pw-check" id="chk-lower"><i class="me-1 text-danger">✖</i> Al menos una letra minúscula (a-z)</li>
                                    <li class="pw-check" id="chk-num"><i class="me-1 text-danger">✖</i> Al menos un número (0-9) o símbolo</li>
                                    <li class="pw-check" id="chk-match"><i class="me-1 text-danger">✖</i> Las contraseñas coinciden</li>
                                </ul>
                            </div>

                            <div class="row mb-3">
                                <button id="submitBtn" class="btn btn-primary-custom btn-lg w-100" type="submit" disabled>Crear cuenta</button>
                            </div>

                            <div class="row">
                                <div class="d-flex justify-content-between">
                                    <a href="login.jsp" class="small-link">¿Ya tienes cuenta? Iniciar sesión</a>
                                    <a href="#" class="small-link">Política de privacidad</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            (function () {
                // elementos
                const pwd = document.getElementById('password');
                const pwd2 = document.getElementById('password2');
                const submitBtn = document.getElementById('submitBtn');

                const chkLen = document.getElementById('chk-len');
                const chkUpper = document.getElementById('chk-upper');
                const chkLower = document.getElementById('chk-lower');
                const chkNum = document.getElementById('chk-num');
                const chkMatch = document.getElementById('chk-match');

                const togglePwd = document.getElementById('togglePwd');
                const togglePwd2 = document.getElementById('togglePwd2');

                // toggle mostrar/ocultar (cambia tipo y el icono)
                function toggleInputVisibility(inputEl, btn) {
                    const icon = btn.querySelector('i');
                    if (inputEl.type === 'password') {
                        inputEl.type = 'text';
                        icon.className = 'bx bx-show'; // icono "mostrar"
                        btn.setAttribute('aria-label', 'Ocultar contraseña');
                    } else {
                        inputEl.type = 'password';
                        icon.className = 'bx bx-hide'; // icono "ocultar"
                        btn.setAttribute('aria-label', 'Mostrar contraseña');
                    }
                }
                togglePwd.addEventListener('click', () => toggleInputVisibility(pwd, togglePwd));
                togglePwd2.addEventListener('click', () => toggleInputVisibility(pwd2, togglePwd2));

                // validación en vivo
                function updateChecks() {
                    const v = pwd.value;
                    const v2 = pwd2.value;

                    const reLen = /.{8,}/;
                    const reUpper = /[A-ZÁÉÍÓÚÑ]/;
                    const reLower = /[a-záéíóúñ]/;
                    const reNumSym = /[0-9]|[^A-Za-z0-9]/;

                    const okLen = reLen.test(v);
                    const okUpper = reUpper.test(v);
                    const okLower = reLower.test(v);
                    const okNum = reNumSym.test(v);
                    const okMatch = (v.length > 0) && (v === v2);

                    setCheck(chkLen, okLen);
                    setCheck(chkUpper, okUpper);
                    setCheck(chkLower, okLower);
                    setCheck(chkNum, okNum);
                    setCheck(chkMatch, okMatch);

                    // habilita submit sólo si todo OK
                    submitBtn.disabled = !(okLen && okUpper && okLower && okNum && okMatch);
                }

                function setCheck(element, ok) {
                    const icon = element.querySelector('i');
                    if (ok) {
                        icon.textContent = '✓';
                        icon.classList.remove('text-danger');
                        icon.classList.add('text-success');
                    } else {
                        icon.textContent = '✖';
                        icon.classList.remove('text-success');
                        icon.classList.add('text-danger');
                    }
                }

                // listeners
                pwd.addEventListener('input', updateChecks);
                pwd2.addEventListener('input', updateChecks);

                // validación final antes de submit (por seguridad UX)
                const form = document.getElementById('registroForm');
                form.addEventListener('submit', function (e) {
                    // si el botón está deshabilitado no debería llegar, pero por si acaso:
                    if (submitBtn.disabled) {
                        e.preventDefault();
                        e.stopPropagation();
                        Swal.fire({icon: 'warning', title: 'Completa la contraseña', text: 'Asegúrate que la contraseña cumpla todos los requisitos.'});
                        return;
                    }
                    // opcional: puedes mostrar un spinner, deshabilitar el botón, etc.
                    submitBtn.disabled = true;
                });

                // Mensajes según parámetros (opcional)
                const params = new URLSearchParams(window.location.search);
                const registro = params.get('registro');
                const err = params.get('error');
                if (registro === 'ok') {
                    Swal.fire({icon: 'success', title: 'Registro exitoso', text: 'Cuenta creada. Revisa tu correo si hace falta verificar.', timer: 3000, showConfirmButton: false});
                }
                if (err) {
                    let text = 'Ocurrió un error.';
                    if (err === 'bd')
                        text = 'Error en la base de datos. Intenta nuevamente.';
                    if (err === 'ex')
                        text = 'Error inesperado. Revisa logs del servidor.';
                    if (err === 'email')
                        text = 'El correo ya está registrado.';
                    Swal.fire({icon: 'error', title: 'Error', text: text});
                }

                // Inicializa checks (por si hay autofill)
                updateChecks();
            })();
        </script>
    </body>
</html>
