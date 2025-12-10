package com.servicios.pagos_service.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class PaypalService {

  @Value("${paypal.client.id}")
  private String clientId;

  @Value("${paypal.client.secret}")
  private String clientSecret;

  @Value("${paypal.base.url}")
  private String baseUrl;

  private final RestTemplate restTemplate = new RestTemplate();

  // =================== TOKEN ===================
  public String obtenerAccessToken() {

    System.out.println("[PAYPAL] Solicitando TOKEN...");

    HttpHeaders headers = new HttpHeaders();
    headers.setBasicAuth(clientId, clientSecret);
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("grant_type", "client_credentials");

    HttpEntity<?> req = new HttpEntity<>(form, headers);

    ResponseEntity<Map> resp = restTemplate.postForEntity(baseUrl + "/v1/oauth2/token", req, Map.class);

    String token = (String) resp.getBody().get("access_token");

    System.out.println("[PAYPAL] TOKEN: " + token);

    return token;
  }

  // =================== CREAR ORDEN ===================
  public Map<String, String> crearOrden(Integer pedidoId, BigDecimal costo) {

    String token = obtenerAccessToken();

    HttpHeaders h = new HttpHeaders();
    h.setBearerAuth(token);
    h.setContentType(MediaType.APPLICATION_JSON);

    DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));

    String body = """
        {
          "intent": "CAPTURE",
          "purchase_units": [{
            "reference_id": "%d",
            "amount": {
              "currency_code": "USD",
              "value": "%s"
            }
          }],
          "application_context": {
            "return_url": "http://localhost:8083/pagos/paypal/return",
            "cancel_url": "http://localhost:8083/pagos/paypal/cancel"
          }
        }
        """.formatted(pedidoId, df.format(costo));

    HttpEntity<String> req = new HttpEntity<>(body, h);

    ResponseEntity<Map> resp = restTemplate.postForEntity(baseUrl + "/v2/checkout/orders", req, Map.class);

    Map<String, Object> mapa = resp.getBody();

    System.out.println("[PAYPAL] ORDEN CREADA: " + mapa);

    String id = (String) mapa.get("id");

    List<Map<String, String>> links = (List<Map<String, String>>) mapa.get("links");

    String approval = links.stream()
        .filter(x -> x.get("rel").equals("approve"))
        .findFirst().get().get("href");

    return Map.of("id", id, "approval_url", approval);
  }

  // =================== CAPTURAR ===================
  public Map<String, Object> capturarOrden(String orderId) {

    System.out.println("[PAYPAL] CAPTURANDO orderId=" + orderId);

    String token = obtenerAccessToken();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> req = new HttpEntity<>("{}", headers);

    ResponseEntity<Map> resp = restTemplate.postForEntity(
        baseUrl + "/v2/checkout/orders/" + orderId + "/capture",
        req,
        Map.class);

    Map<String, Object> body = resp.getBody();

    System.out.println("[PAYPAL] RESPUESTA CAPTURE: " + body);

    Map pu = ((List<Map>) body.get("purchase_units")).get(0);
    Map payments = (Map) pu.get("payments");
    Map cap = ((List<Map>) payments.get("captures")).get(0);

    return Map.of(
        "status", body.get("status"),
        "orderId", orderId,
        "captureId", cap.get("id"),
        "details", body);
  }

}
