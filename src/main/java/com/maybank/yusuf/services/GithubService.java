/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maybank.yusuf.services;

import com.maybank.yusuf.models.forms.GithubSearchResponse;
import com.maybank.yusuf.models.forms.GithubUser;
import com.maybank.yusuf.models.User;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author 62878
 */
@Service
public class GithubService {
    private static final String GITHUB_API_URL = "https://api.github.com/search/users";
    private static final int MAX_RESULTS = 100;

    private final RestTemplate restTemplate;

    public GithubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> searchGithubUsers(String query) {
        String url = GITHUB_API_URL + "?q=" + query + "&per_page=" + MAX_RESULTS;
        ResponseEntity<GithubSearchResponse> response = restTemplate.getForEntity(url, GithubSearchResponse.class);
        GithubSearchResponse searchResponse = response.getBody();
        if (searchResponse != null) {
            return searchResponse.getItems().stream()
                    .map(this::mapToUser)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private User mapToUser(GithubUser githubUser) {
        User user = new User();
        user.setUsername(githubUser.getLogin());
        user.setName(githubUser.getName());
        user.setEmail(githubUser.getEmail());
        return user;
    }
}