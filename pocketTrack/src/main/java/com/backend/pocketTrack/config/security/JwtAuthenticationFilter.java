package com.backend.pocketTrack.config.security;

import com.backend.pocketTrack.service.Usuario.IUsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtTokenProvider jwtService;
    private final IUsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        String username = null;
        String token = null;

        try{
            token = getTokenFromRequest(request);

            if (token == null)
            {
                filterChain.doFilter(request, response);
                return;
            }

            username = jwtService.extractUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                UserDetails user = usuarioService.loadUserByUsername(username);

                // Si el token existe y es válido
                if (StringUtils.hasText(token) && jwtService.isTokenValid(token, user))
                {
                    UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);

        }
        catch (Exception ex)
        {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

    // Procesamos el Token del Request
    private String getTokenFromRequest(HttpServletRequest request) {
        // Tomamos la cabecera
        final String authHeader = request.getHeader(JwtTokenProvider.TOKEN_HEADER);

        // Si tiene el prefijo y es de la logitud indicada
        if(StringUtils.hasText(authHeader) && authHeader.startsWith(JwtTokenProvider.TOKEN_PREFIX))
        {
            return authHeader.substring(JwtTokenProvider.TOKEN_PREFIX.length());
        }

        return null;
    }
}
