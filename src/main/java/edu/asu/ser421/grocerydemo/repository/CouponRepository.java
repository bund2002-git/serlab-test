package edu.asu.ser421.grocerydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.asu.ser421.grocerydemo.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
