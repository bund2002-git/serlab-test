package edu.asu.ser421.grocerydemo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.asu.ser421.grocerydemo.model.Coupon;
import edu.asu.ser421.grocerydemo.repository.CouponRepository;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public void saveCoupon(Coupon coupon) {
        couponRepository.save(coupon);
    }

    public List<Coupon> getApplicableCoupons(List<String> groceryIds) {
        return couponRepository.findAll().stream()
                .filter(coupon -> groceryIds.contains(coupon.getGroceryItem().getId()))
                .collect(Collectors.toList());
    }
}
