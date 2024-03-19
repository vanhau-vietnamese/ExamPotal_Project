
package com.exam.config;

import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    @Getter
    private String jwt;

    public String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        System.out.println("bearerToken: " + bearerToken);
        // Kiểm tra xem header authorization có chứa thông tin jwt k?
        if(StringUtils.hasText(bearerToken)){
            if(bearerToken.startsWith("Bearer ")){
                // token bắt đầu bằng chuỗi "Bearer "
                jwt = bearerToken.substring(7);
                return jwt;
            }
            else{
                // nếu k,return về chuỗi token
                jwt = bearerToken;
                return jwt;
            }
        }

        return null;
    }
    private boolean isValidateRequest(HttpServletRequest request, String url) {
        String requestURI = request.getRequestURI();
        return requestURI.equals(url);
    }
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = getJwtFromRequest(request);
        if(isValidateRequest(request, "/auth/register") || isValidateRequest(request, "/user/me")
                || isValidateRequest(request, "/user/add") ){
            filterChain.doFilter(request, response);
            return;
        }
        if (token != null) {
            FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
            String email = decodedToken.getEmail();
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        filterChain.doFilter(request, response);
    }

}
