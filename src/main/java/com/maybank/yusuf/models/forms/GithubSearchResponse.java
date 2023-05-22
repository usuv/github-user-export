/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maybank.yusuf.models.forms;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author 62878
 */
public class GithubSearchResponse {
    
    private List<GithubUser> items;

    public List<GithubUser> getItems() {
        return items;
    }

    public void setItems(List<GithubUser> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.items);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GithubSearchResponse other = (GithubSearchResponse) obj;
        if (!Objects.equals(this.items, other.items)) {
            return false;
        }
        return true;
    }
}