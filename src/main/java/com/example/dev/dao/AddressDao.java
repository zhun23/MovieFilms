package com.example.dev.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.dev.model.Address;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class AddressDao implements IAddressDao {

	@PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Address save(Address address) {
        if (address.getAddressid() == null) {
            entityManager.persist(address);
        } else {
            entityManager.merge(address);
        }
        return address;
    }

    @Override
    public Optional<Address> findByUser_Userid(Integer userid) {
        String query = "SELECT A FROM Address A WHERE A.user.userid = :userid";
        Address address = entityManager.createQuery(query, Address.class)
                                       .setParameter("userid", userid)
                                       .getSingleResult();
        return Optional.ofNullable(address);
    }

    @Override
    public Optional<Address> findById(Integer addressid) {
        Address address = entityManager.find(Address.class, addressid);
        return Optional.ofNullable(address);
    }
}
