
package com.security.security.rest;

import java.util.HashMap;
import java.util.Map;
import lombok.var;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class DemoRest {
    
    private static final Logger logger = LoggerFactory.getLogger(DemoRest.class);
    
    @GetMapping("/mensaje")
   public ResponseEntity<?> getMensaje() {
    var auth =  SecurityContextHolder.getContext().getAuthentication();
    logger.info("Datos del Usuario: {}", auth.getPrincipal());
    logger.info("Datos de los Permisos {}", auth.getAuthorities());
    logger.info("Esta autenticado {}", auth.isAuthenticated());

    Map<String, String> mensaje = new HashMap<>();
    mensaje.put("contenido", "Hola Peru");
    return ResponseEntity.ok(mensaje);
  }
}
