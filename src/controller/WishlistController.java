package controller;

import java.util.ArrayList;
import model.Wishlist;

public class WishlistController {
	
	// Method wajib: untuk BuyerViewWishlistPage, fetch semua item yang di wishlist oleh user
	// Parameter String wishlistId diremove karena tidak diperlukan, hanya perlu userId untuk fetch semua user's wishlist
	public ArrayList<Wishlist> viewWishlist(String userId) {
		return Wishlist.ViewWishlist(userId);
	}	
	
	// Method wajib: untuk insert ke db wishlist
	public void addWishlist(String itemId, String userId) {
		String wishlistNewId = generateNewWishlistId();
		Wishlist.AddWishlist(wishlistNewId, itemId, userId);
	}
	
	// Method tambahan untuk mendapat id terakhir dari wishlist, yang nantinya digunakan untuk mengenerate id wishlist baru.
	public String getLastId() {
		return Wishlist.GetLastId();
	}
	
	// Method tambahan untuk mengenerate id wishlist baru.
	public String generateNewWishlistId() {
		String lastId = getLastId();
		String numberId = lastId.substring(1);
		try {
			int num = Integer.parseInt(numberId);
			num++;
			return String.format("W%03d", num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "W001";
	}
	
	// Method wajib: Method untuk meremove wishlist dari database wishlists.
	public void removeWishlist(String wishlistId) {
		Wishlist.RemoveWishlist(wishlistId);
	}
	
	// Method tambahan untuk fetch wishlistId berdasarkan itemId dan userId
	public String getWishlistIdByItemIdAndUserId(String itemId, String userId) {
	    return Wishlist.GetWishlistIdByItemIdAndUserId(itemId, userId);
	}

	// Method tambahan untuk cek apakah item sudah di wishlist oleh user
	public boolean isItemInWishlist(String itemId, String userId) {
	    return Wishlist.IsItemInWishlist(itemId, userId);
	}

}
