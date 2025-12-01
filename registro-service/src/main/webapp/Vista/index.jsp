<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NOVAS - Viajes Seguros y Cómodos</title>
    <link href="../css/novas-landing.css" rel="stylesheet" type="text/css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">

    <!-- Pequeños estilos locales para alinear el botón en el header -->
    <style>
      .nav-actions {
        display: flex;
        align-items: center;
        gap: 0.5rem;
        margin-left: 1rem;
      }
      @media (max-width: 768px) {
        .nav-actions { margin-left: 0; }
      }
    </style>
</head>
<body>
    <!-- HEADER-->
    <header>
        <nav class="navbar">
            <div class="container" style="display:flex; align-items:center; justify-content:space-between;">
                <div class="nav-brand" style="display:flex; align-items:center; gap:10px;">
                    <img src="${pageContext.request.contextPath}/Imagenes/logo-novas.png" alt="NOVAS Logo" class="logo">
                    <span class="company-name">NOVAS</span>
                </div>

                <!-- menú principal -->
                <ul class="nav-menu" style="display:flex; list-style:none; gap:1rem; align-items:center;">
                    <li><a href="#inicio">Inicio</a></li>
                    <li><a href="#destinos">Destinos</a></li>
                    <li><a href="#promociones">Promociones</a></li>
                    <li><a href="#contacto">Contacto</a></li>
                </ul>

                <!-- BOTÓN ACCEDER -->
                <div class="nav-actions">
                    <a href="${pageContext.request.contextPath}/Vista/login.jsp"
                       class="btn btn-primary btn-access"
                       role="button"
                       aria-label="Acceder a iniciar sesión">
                       <i class="fas fa-user-circle" aria-hidden="true"></i>
                       <span style="margin-left:8px;">Acceder</span>
                    </a>
                </div>
            </div>
        </nav>
    </header>

    <!-- ANUNCIO -->
    <section class="hero" id="inicio">
        <div class="hero-overlay">
            <div class="container">
                <div class="hero-content">
                    <h1 class="hero-title">Viaja con <span class="highlight">NOVAS</span></h1>
                    <p class="hero-subtitle">Descubre Perú con comodidad y seguridad</p>
                    <div class="hero-tagline">
                        <i class="fas fa-check"></i>
                        <span>NOVAS es atención y dedicación</span>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- VISTA PARA PASAJES  -->
    <section class="search-section">
        <div class="container">
            <div class="search-card">
                <h2 class="search-title">
                    <i class="fas fa-search"></i>
                    Encuentra tu próximo viaje
                </h2>
                
                <form id="search-form" action="BuscarPasajes" method="post" class="modern-form">
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="origen" class="form-label">
                                <i class="fas fa-map-marker-alt"></i>
                                Origen
                            </label>
                            <select id="origen" name="origen" class="form-select" required>
                                <option value="">Seleccione ciudad de origen</option>
                                <option value="lima">Lima</option>
                                <option value="arequipa">Arequipa</option>
                                <option value="cusco">Cusco</option>
                                <option value="trujillo">Trujillo</option>
                                <option value="chiclayo">Chiclayo</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="destino" class="form-label">
                                <i class="fas fa-flag-checkered"></i>
                                Destino
                            </label>
                            <select id="destino" name="destino" class="form-select" required>
                                <option value="">Seleccione ciudad de destino</option>
                                <option value="abancay">Abancay</option>
                                <option value="arequipa">Arequipa</option>
                                <option value="camana">Camaná</option>
                                <option value="chiclayo">Chiclayo</option>
                                <option value="chimbote">Chimbote</option>
                                <option value="cusco">Cusco</option>
                                <option value="lima">Lima</option>
                                <option value="trujillo">Trujillo</option>
                            </select>
                        </div>
                        <!-- OPCIONES -->
                        <div class="form-group">
                            <label for="fecha-salida" class="form-label">
                                <i class="fas fa-calendar-alt"></i>
                                Fecha de salida
                            </label>
                            <input type="date" id="fecha-salida" name="fechaSalida" class="form-input" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="fecha-retorno" class="form-label">
                                <i class="fas fa-calendar-check"></i>
                                Fecha de retorno
                            </label>
                            <input type="date" id="fecha-retorno" name="fechaRetorno" class="form-input">
                            <div class="checkbox-group">
                                <input type="checkbox" id="solo-ida" name="soloIda">
                                <label for="solo-ida" class="checkbox-label">Solo ida</label>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="pasajeros" class="form-label">
                                <i class="fas fa-users"></i>
                                Pasajeros
                            </label>
                            <select id="pasajeros" name="pasajeros" class="form-select">
                                <option value="1">1 pasajero</option>
                                <option value="2">2 pasajeros</option>
                                <option value="3">3 pasajeros</option>
                                <option value="4">4 pasajeros</option>
                                <option value="5">5 pasajeros</option>
                            </select>
                        </div>
                    </div>
                    
                    <button type="submit" class="btn btn-primary btn-large">
                        <i class="fas fa-search"></i>
                        Buscar Pasajes
                    </button>
                </form>
            </div>
        </div>
    </section>
    <!-- pasajes -->
    <section class="destinations-section" id="destinos">
        <div class="container">
            <div class="section-header">
                <h2 class="section-title">Destinos Populares</h2>
                <p class="section-subtitle">Descubre los lugares más visitados de Perú</p>
            </div>
            <!-- destinos -->
            <div class="destinations-grid">
                <div class="destination-card" data-destino="arequipa">
                    <div class="destination-image">
                        <img src="${pageContext.request.contextPath}/Imagenes/arequipa.jpg" alt="Arequipa">
                        <div class="destination-overlay">
                            <span class="destination-price">Desde S/ 45</span>
                        </div>
                    </div>
                    <div class="destination-content">
                        <h3 class="destination-name">Arequipa</h3>
                        <p class="destination-desc">La Ciudad Blanca</p>
                        <div class="destination-features">
                            <span><i class="fas fa-clock"></i> 12h</span>
                            <span><i class="fas fa-bus"></i> Directo</span>
                        </div>
                    </div>
                </div>
                <!-- destinos -->
                
                <div class="destination-card" data-destino="cusco">
                    <div class="destination-image">
                        <img src="${pageContext.request.contextPath}/Imagenes/cusco.jpg" alt="Cusco">
                        <div class="destination-overlay">
                            <span class="destination-price">Desde S/ 55</span>
                        </div>
                    </div>
                    <div class="destination-content">
                        <h3 class="destination-name">Cusco</h3>
                        <p class="destination-desc">Capital Imperial</p>
                        <div class="destination-features">
                            <span><i class="fas fa-clock"></i> 20h</span>
                            <span><i class="fas fa-bus"></i> Semi-cama</span>
                        </div>
                    </div>
                </div>
                
                <div class="destination-card" data-destino="chiclayo">
                    <div class="destination-image">
                        <img src="${pageContext.request.contextPath}/Imagenes/chiclayo.jpg" alt="Chiclayo">
                        <div class="destination-overlay">
                            <span class="destination-price">Desde S/ 35</span>
                        </div>
                    </div>
                    <div class="destination-content">
                        <h3 class="destination-name">Chiclayo</h3>
                        <p class="destination-desc">Capital de la Amistad</p>
                        <div class="destination-features">
                            <span><i class="fas fa-clock"></i> 10h</span>
                            <span><i class="fas fa-bus"></i> Directo</span>
                        </div>
                    </div>
                </div>
                
                <div class="destination-card" data-destino="trujillo">
                    <div class="destination-image">
                        <img src="${pageContext.request.contextPath}/Imagenes/trujillo.jpg" alt="Trujillo">
                        <div class="destination-overlay">
                            <span class="destination-price">Desde S/ 40</span>
                        </div>
                    </div>
                    <div class="destination-content">
                        <h3 class="destination-name">Trujillo</h3>
                        <p class="destination-desc">Ciudad de la Eterna Primavera</p>
                        <div class="destination-features">
                            <span><i class="fas fa-clock"></i> 8h</span>
                            <span><i class="fas fa-bus"></i> Ejecutivo</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- CHERRY XD -->
    <section class="visa-section">
        <div class="container">
            <div class="visa-card">
                <div class="visa-icon">
                    <i class="fas fa-bus"></i>
                </div>
                <div class="visa-content">
                    <h2>Porque viajar con NOVAS</h2>
                    <p>Te brindamos un servicio de transporte integral que combina seguridad, calidad, cumplimiento de plazos y trazabilidad de tus trayectos. </p>
                    <button class="btn btn-outline">                    
                        Más Información
                    </button>
                </div>
            </div>
        </div>
    </section>

    <!-- FOOTER -->
    <footer class="footer">
        <div class="container">
            <div class="footer-content">
                <div class="footer-section">
                    <div class="footer-logo">
                        <img src="${pageContext.request.contextPath}/Imagenes/logo-novas-white.png" alt="NOVAS" class="logo">
                        <span class="company-name">NOVAS</span>
                    </div>
                    <p class="footer-text">Viajes seguros y cómodos por todo el Perú</p>
                    <!-- redes  -->
                    <div class="social-links">
                        <a href="#"><i class="fab fa-facebook"></i></a>
                        <a href="#"><i class="fab fa-twitter"></i></a>
                        <a href="#"><i class="fab fa-instagram"></i></a>
                        <a href="#"><i class="fab fa-whatsapp"></i></a>
                    </div>
                </div>
                
                <div class="footer-section">
                    <h3>Destinos</h3>
                    <ul>
                        <li><a href="#">Arequipa</a></li>
                        <li><a href="#">Cusco</a></li>
                        <li><a href="#">Chiclayo</a></li>
                        <li><a href="#">Trujillo</a></li>
                    </ul>
                </div>
                
                <div class="footer-section">
                    <h3>Opciones de Viaje</h3>
                    <ul>
                        <li><a href="#">Promociones</a></li>
                        <li><a href="#">Rutas</a></li>
                        <li><a href="#">Horarios</a></li>
                        <li><a href="#">Contacto</a></li>
                    </ul>
                </div>
                
                <div class="footer-section">
                    <h3>Contacto</h3>
                    <div class="contact-info">
                        <p><i class="fas fa-phone"></i> (01) 123-4567</p>
                        <p><i class="fas fa-envelope"></i> info@novas.com</p>
                        <p><i class="fas fa-map-marker-alt"></i> Lima, Perú</p>
                    </div>
                </div>
            </div>
            
            <div class="footer-bottom">
                <p>&copy; 2025 NOVAS - Todos los derechos reservados</p>
                <p>NOVAS es atención y dedicación</p>
            </div>
        </div>
    </footer>

    <script src="js/script.js"></script>
</body>
</html>
