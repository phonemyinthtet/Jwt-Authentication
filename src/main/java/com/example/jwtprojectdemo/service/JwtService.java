package com.example.jwtprojectdemo.service;
import com.example.jwtprojectdemo.entity.User;
import com.example.jwtprojectdemo.dao.UserRepository;
import com.example.jwtprojectdemo.entity.JwtRequest;
import com.example.jwtprojectdemo.entity.JwtResponse;
import com.example.jwtprojectdemo.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    public JwtService(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public JwtResponse createJwtToken(JwtRequest jwtRequest){
        var username = jwtRequest.getUsername();
        var password =jwtRequest.getPassword();
        //username and password ကို authentication Manger မှာသိမ်းတာ
        authenticate(username,password);
       final var userDetials = loadUserByUsername(username);
        var newGenerationToken = jwtUtil.generateToken(userDetials);
        User user= userRepository.findUserByUsername(username).get();
        return new JwtResponse(user,newGenerationToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByUsername(username).get();
        if (user != null ){
            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),userRole(user));
        }else {
            throw new UsernameNotFoundException("Username not found ");
        }
    }
    private Set userRole(com.example.jwtprojectdemo.entity.User user){
        Set authorization = new HashSet();
        user.getRoles().forEach(role->{
            authorization.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        });
        return authorization;
    }
    private void authenticate(String username,String password){
        try{

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException e){
            throw new DisabledException("User is disabled");
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Username and Password are wrong ");
        }

    }

}
