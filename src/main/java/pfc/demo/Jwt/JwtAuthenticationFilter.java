package pfc.demo.Jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Procesa y valida los tokens de la solicitud http
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //Extrae el token
        final String token = getTokenFromRequest(request);
        final String username;

        //Si no hay token, continúa con los filtros por lo que que no dejaría entrar
        if (token==null)
        {
            filterChain.doFilter(request, response);
            return;
        }

        //Si hay token, extrae el nombre de usuario
        username=jwtService.getUsernameFromToken(token);

        //Si hay nombre de usuario pero no hay autenticación carga los datos desde el userDetailsService-ç
        //Podría deberse a que es la primera solicitud http por lo que no tiene contexto de seguridad, que el token no sea válido...
        //Se ha configurado esta aplicación como SessionCreationPolicy.STATELESS. Esto quiere decir que el servidor
        //no almacena ningún detalle de autenticación entre las solicitudes, por lo que en  cada solicitud necesita autenticar al usuario nuevamente.
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);

            //Si el token es del usuario crea una instancia de UserNamePasswordAuthenticationToken
            if (jwtService.isTokenValid(token, userDetails))
            {
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                //Establece los detalles de autenticación (dirección IP, agente de usuario: tipo de navegador, dispositivo...).
                //Se puede utilizar operaciones de auditoría, registrar inicios de sesión, aplicar restricciones de seguridad...

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        filterChain.doFilter(request, response);
    }

    /**
     * Devuelve el token sin la cadena "bearer"
     * @param request
     * @return
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))
        {
            return authHeader.substring(7);
        }
        return null;
    }

}