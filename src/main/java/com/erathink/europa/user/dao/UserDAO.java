package com.erathink.europa.user.dao;

import com.erathink.europa.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by fengyun on 2017/12/17.
 */
@Repository
public interface UserDAO extends CrudRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {

    UserEntity findByUserNameAndPassword(String userName,String password);
}
