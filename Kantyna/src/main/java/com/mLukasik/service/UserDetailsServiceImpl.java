package com.mLukasik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mLukasik.model.Uzytkownik;
import com.mLukasik.repository.UzytkownikRepository;
import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service(value = "userService")
public class UserDetailsServiceImpl implements UserDetailsService 
{
	@Autowired
    private UzytkownikRepository uzytkownikRepository;

    public UserDetailsServiceImpl(UzytkownikRepository uzytkownikRepository) 
    {
        this.uzytkownikRepository = uzytkownikRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException 
    {
        List<Uzytkownik> uzytk = uzytkownikRepository.findByLogin(login);
        if (uzytk.size() == 0) 
        {
            throw new UsernameNotFoundException(login);
        }
    	System.out.println(uzytk.get(0).getRola().getRola());
        if(!(uzytk.get(0).getRola().getRola().equals("ROLE_KLIENT")))
        {
            throw new UsernameNotFoundException(login);
        }
        System.out.println(uzytk.get(0).getRola().getRola());
        List<SimpleGrantedAuthority> role = Arrays.asList(new SimpleGrantedAuthority(uzytk.get(0).getRola().getRola()));
        return new User(uzytk.get(0).getLogin(), uzytk.get(0).getHaslo(), role);
    }
    
}