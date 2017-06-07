package com.archsystemsinc.pqrs.service;

import com.archsystemsinc.pqrs.model.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);

}
