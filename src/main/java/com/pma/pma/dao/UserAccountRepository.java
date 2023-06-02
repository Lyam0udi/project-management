package com.pma.pma.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.pma.pma.entities.UserAccount;

public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount, Long>{

}
