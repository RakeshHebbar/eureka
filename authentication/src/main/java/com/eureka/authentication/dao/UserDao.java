package com.eureka.authentication.dao;

import com.eureka.authentication.model.UserModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<UserModel, Integer> {

    @Query(value = "Select * from users where username =:username", nativeQuery = true)
    UserModel findByUserName(@Param("username") String userName);


    @Query(value = "Select * from users where id =:userId", nativeQuery = true)
    UserModel findByUserId(@Param("userId") Integer userId);

}
