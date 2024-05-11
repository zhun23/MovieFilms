package com.example.dev.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.dev.model.UserCt;

@Repository
public interface IUserCtDao extends JpaRepository<UserCt, Integer> {
	Page<UserCt> findAll(Pageable pageable);
	
	Optional<UserCt> findByUserid(int userid);
	
	List<UserCt> findUserByNickname(String nickname);
	
	@Query("SELECT u FROM UserCt u WHERE u.nickname = :nickname")
	UserCt findUserByNicknameUnique(@Param("nickname") String nickname);
	
	List<UserCt> findUserByFirstname(String firstname);
	
	List<UserCt> findUserByLastname(String lastname);
	
	List<UserCt> findUserctByMail(String mail);
	
	@Query("SELECT u FROM UserCt u WHERE u.mail = :mail")
	UserCt findUserByMailUnique(@Param("mail") String mail);
	
	List<UserCt> findByFirstnameContaining(String firstname);
	
	List<UserCt> findByLastnameContaining(String lastname);
	
	@Query("SELECT u FROM UserCt u WHERE (u.firstname LIKE %?1% OR u.lastname LIKE %?1% OR u.nickname LIKE %?1% OR u.mail LIKE %?1%) AND (u.firstname LIKE %?2% OR u.lastname LIKE %?2% OR u.nickname LIKE %?2% OR u.mail LIKE %?2%)")
    List<UserCt> findByFirstNameAndLastName(String word1, String word2);

	boolean existsByNickname(String nickname);

	boolean existsByMail(String mail);
	
	UserCt findByNicknameAndPassword(String nickname, String password);
	
	//Añadido para seguridad
	UserCt findByNickname(String nickname);
}
