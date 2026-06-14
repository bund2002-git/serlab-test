package edu.asu.ser421.grocerydemo.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import edu.asu.ser421.grocerydemo.dto.GroceryFormList;
import edu.asu.ser421.grocerydemo.model.Coupon;
import edu.asu.ser421.grocerydemo.services.CouponService;


@Controller
@RequestMapping("/applydiscounts")
public class ApplyDiscountsController {

    private final CouponService couponService;

    public ApplyDiscountsController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping
    public String showApplyDiscountsPage(Model model, HttpSession session) {
        GroceryFormList selectedItems = (GroceryFormList) session.getAttribute("selecteditems");
        if (selectedItems == null) {
            return "redirect:/groceries"; // Redirect to groceries page if no items selected
        }

        // Fetch applicable coupons
        List<Coupon> applicableCoupons = couponService.getApplicableCoupons(selectedItems.getGroceryIds());
        model.addAttribute("applicableCoupons", applicableCoupons);

        return "applydiscounts"; // Render applydiscounts.html
    }

    @PostMapping
    public String applySelectedDiscounts(@RequestParam List<String> selectedCoupons, HttpSession session) {
        session.setAttribute("selectedCoupons", selectedCoupons);
        return "redirect:/checkout"; // Redirect to Checkout page
    }
}

