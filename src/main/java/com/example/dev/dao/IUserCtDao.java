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
import com.example.dev.model.UserParameters;

@Repository
public interface IUserCtDao extends JpaRepository<UserCt, Integer> {
	@Override
	Page<UserCt> findAll(Pageable pageable);

	Optional<UserCt> findByUserid(Integer id);

	@Query("SELECT u.credit FROM UserCt u WHERE u.nickname = :nickname")
	Integer findUserCreditByNickname(@Param("nickname") String nickname);

	List<UserCt> findUserByNickname(String nickname);

	UserCt findEspecificUserByNickname(String nickname);

	@Query("SELECT u FROM UserCt u WHERE u.nickname = :nickname")
	UserCt findUserByNicknameUnique(@Param("nickname") String nickname);

	List<UserCt> findUserByFirstname(String firstname);

	List<UserCt> findUserByLastname(String lastname);

	List<UserCt> findUserctByMail(String mail);

	Optional<UserCt> findByMail(String mail);

//	@Query(nativeQuery=true, value = "SELECT * FROM Users WHERE mail = ... ") //EJEMPLO CON MYSQL
	@Query("SELECT u FROM UserCt u WHERE u.mail = :mail")
	UserCt findUserByMailUnique(@Param("mail") String mail);

	List<UserCt> findByFirstnameContaining(String firstname);

	List<UserCt> findByLastnameContaining(String lastname);

	@Query("SELECT u FROM UserCt u WHERE (u.firstname LIKE %?1% OR u.lastname LIKE %?1% OR u.nickname LIKE %?1% OR u.mail LIKE %?1%) AND (u.firstname LIKE %?2% OR u.lastname LIKE %?2% OR u.nickname LIKE %?2% OR u.mail LIKE %?2%)")
    List<UserCt> findByFirstNameAndLastName(String word1, String word2);

	boolean existsByNickname(String nickname);

	boolean existsByMail(String mail);

	UserCt findByNicknameAndPassword(String nickname, String password);

	UserCt findByNickname(String nickname);

	@Query(value = """
			SELECT u FROM UserCt u
			WHERE
				(:#{#params.userid}   IS NULL OR u.userid = :#{#params.userid})
			AND (:#{#params.nickname} IS NULL OR :#{#params.nickname} = ''   OR u.nickname  LIKE %:#{#params.nickname}%)
			AND (:#{#params.firstname} IS NULL OR :#{#params.firstname} = '' OR u.firstname LIKE %:#{#params.firstname}%)
			AND (:#{#params.lastname} IS NULL OR :#{#params.lastname} = ''   OR u.lastname  LIKE %:#{#params.lastname}%)
			AND (:#{#params.mail} 	  IS NULL OR :#{#params.mail} = ''       OR u.mail      LIKE %:#{#params.mail}%)
			""")
	List<UserCt> findWithParameters(@Param("params") UserParameters params);

//	@Query(value = """ HAY QUE MODIFICAR EL HQL POR MYSQL
//			SELECT u FROM UserCt u
//			WHERE
//				(:#{#params.userid}   IS NULL OR u.userid = :#{#params.userid})
//			AND (:#{#params.nickname} IS NULL OR :#{#params.nickname} = ''   OR u.nickname  LIKE %:#{#params.nickname}%)
//			AND (:#{#params.firstname} IS NULL OR :#{#params.firstname} = '' OR u.firstname LIKE %:#{#params.firstname}%)
//			AND (:#{#params.lastname} IS NULL OR :#{#params.lastname} = ''   OR u.lastname  LIKE %:#{#params.lastname}%)
//			AND (:#{#params.mail} 	  IS NULL OR :#{#params.mail} = ''       OR u.mail      LIKE %:#{#params.mail}%)
//			""", nativeQuery = true)
//	List<UserCt> findWithParameters(@Param("params") UserParameters params);
}
