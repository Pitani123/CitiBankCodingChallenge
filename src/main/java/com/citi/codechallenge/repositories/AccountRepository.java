package com.citi.codechallenge.repositories;

import com.citi.codechallenge.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {
}
