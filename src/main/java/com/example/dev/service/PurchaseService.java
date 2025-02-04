package com.example.dev.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dev.dao.ICatalogueDao;
import com.example.dev.dao.IMoviesCartDao;
import com.example.dev.dao.IPurchaseDao;
import com.example.dev.dao.IPurchaseItemDao;
import com.example.dev.dao.IUserCtDao;
import com.example.dev.dto.CartDetailDto;
import com.example.dev.dto.PurchaseDto;
import com.example.dev.model.CartDetail;
import com.example.dev.model.Movie;
import com.example.dev.model.MoviesCart;
import com.example.dev.model.Purchase;
import com.example.dev.model.PurchaseItem;
import com.example.dev.model.UserCt;

@Service
public class PurchaseService implements IPurchaseService{

	private final IPurchaseDao purchaseDao;
	
	private IPurchaseItemDao purchaseItemDao;
	
	private IUserCtDao userCtDao;
	
	private IMoviesCartDao moviesCartDao;
	
	private ICatalogueDao catalogueDao;

    public PurchaseService(
    		IPurchaseDao purchaseDao, 
    		IPurchaseItemDao purchaseItemDao, 
    		IUserCtDao userCtDao, 
    		IMoviesCartDao moviesCartDao, 
    		ICatalogueDao catalogueDao) {
        		this.purchaseDao = purchaseDao;
        		this.purchaseItemDao = purchaseItemDao;
        		this.userCtDao = userCtDao;
        		this.moviesCartDao = moviesCartDao;
        		this.catalogueDao = catalogueDao;
    }
    
    public Page<Purchase> findAll(Pageable pageable) {
    	return purchaseDao.findAll(pageable);
    }


    @Override
    public void save(Purchase purchase) {
        purchaseDao.save(purchase);
    }

    public void deleteById(Integer id) {
    	purchaseDao.deleteById(id);
    }
    
    public void createPurchase(PurchaseDto purchaseDto) {
    	
    	UserCt user = userCtDao.findByNickname(purchaseDto.getNickname());
    	
        Purchase purchase = new Purchase();
        purchase.setNickname(purchaseDto.getNickname());
        purchase.setAddress1(purchaseDto.getAddressLine1());
        purchase.setAddress2(purchaseDto.getAddressLine2());
        purchase.setZipcode(purchaseDto.getZipcode());
        purchase.setLocation(purchaseDto.getLocation());
        purchase.setProvince(purchaseDto.getProvince());
        purchase.setCountry(purchaseDto.getCountry());
        purchase.setShippingCost(purchaseDto.getShippingCost());
        purchase.setTotalCost(purchaseDto.getTotalOrderAmount());
        purchase.setDate(LocalDateTime.now());
        
        purchaseDao.save(purchase);

        for (CartDetailDto cartDetail : purchaseDto.getCartDetails()) {
            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setPurchase(purchase);
            purchaseItem.setTitle(cartDetail.getCatalogue().getTitle());
            purchaseItem.setQuantity(cartDetail.getQuantity());

            purchaseItemDao.save(purchaseItem);
        }
        
        MoviesCart moviesCart = moviesCartDao.findByUser(user);
        
        for (CartDetail cartDetail : moviesCart.getCartDetails()) {
            Movie movie = cartDetail.getCatalogue();
            int newStock = movie.getStock() - cartDetail.getQuantity();
            if (newStock < 0) {
                throw new IllegalArgumentException("Stock insuficiente para la película: " + movie.getTitle());
            }
            movie.setStock(newStock);
            catalogueDao.save(movie);
        }
        
        moviesCart.getCartDetails().clear();
        moviesCartDao.save(moviesCart);
    }
    
    public Purchase findById(Integer purchaseId) {
        Optional<Purchase> purchase = purchaseDao.findById(purchaseId);
        return purchase.orElse(null);
    }
    
    public void initializePurchaseItems(Integer purchaseId) {
        Purchase purchase = findById(purchaseId);
        if (purchase != null) {
            purchase.getPurchaseItems().size();
        }
    }
    
    public Optional<Purchase> getPurchaseById(Integer purchaseId) {
        return purchaseDao.findById(purchaseId);
    }

	@Override
	public List<Purchase> findByNickname(String nickname) {
		 List<Purchase> purchase = purchaseDao.findByNickname(nickname);
		return purchase;
	}
}
