package pfc.demo.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    //Clave para firmar y validar los tokens
    private static final String SECRET_KEY="586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    /**
     * Método para generar un token para un usuario
     * @param user
     * @return
     */
    public String getToken(UserDetails user) {

        //Llama al método privado con un HashMap vacío (se podría implementar en un futuro por si se quisieran claims adicionales)
        // y el usuario para configurar el token.
        return getToken(new HashMap<>(), user);
    }

    /**
     * Método para configurar la creación del token.
     * @param extraClaims
     * @param user
     * @return
     */
    private String getToken(Map<String,Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Devuelve la clave secreta como un objeto key
     * @return
     */
    private Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Obtiene el username de un token
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Comprueba que el token sea válido
     * @param token
     * @param userDetails
     * @return
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    /**
     * Devuelve las claims del token
     * @param token
     * @return
     */
    private Claims getAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Devuelve una claim específica
     * @param token
     * @param claimsResolver
     * @return
     * @param <T>
     */
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Obtener la fecha de expiración
     * @param token
     * @return
     */
    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Comprueba que el token no esté expirado
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }

    /**
     * Extrae el token de una autenticación
     * @param authentication
     * @return
     */
    public String getUsernameFromAuthentication(Authentication authentication) {
        Object auth = authentication.getPrincipal();
        if (auth instanceof UserDetails) {
            return ((UserDetails) auth).getUsername();
        } else {
            return auth.toString();
        }
    }

}