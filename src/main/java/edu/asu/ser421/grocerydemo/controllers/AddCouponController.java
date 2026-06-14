package edu.asu.ser421.grocerydemo.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.ser421.grocerydemo.model.Coupon;
import edu.asu.ser421.grocerydemo.services.CouponService;
import edu.asu.ser421.grocerydemo.services.GroceryItemService;

@Controller
@RequestMapping("addcoupon")
public class AddCouponController {
     private final GroceryItemService groceryItemService;
    private final CouponService couponService;

    public AddCouponController(GroceryItemService groceryItemService, CouponService couponService) {
        this.groceryItemService = groceryItemService;
        this.couponService = couponService;
    }

    @GetMapping
    public String showAddCouponForm(Model model) {
        model.addAttribute("coupon", new Coupon());
        model.addAttribute("groceryItems", groceryItemService.getGroceryList());
        return "addcoupon";
    }

    @PostMapping
    public String handleAddCoupon(
            @RequestParam String action,
            @ModelAttribute Coupon coupon,
            Model model,
            RedirectAttributes redirectAttributes) {

        if ("goback".equals(action)) {
            return "redirect:/groceries";
        } else if ("add".equals(action)) {
            try {
                // Validate coupon
                if (coupon.getSavings() < 0 || coupon.getSavings() > coupon.getGroceryItem().getPrice()) {
                    throw new IllegalArgumentException("Savings must be between 0 and the item's price.");
                }

                // Save the coupon
                couponService.saveCoupon(coupon);
                redirectAttributes.addFlashAttribute("successMessage", "Coupon added successfully!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Failed to add coupon: " + e.getMessage());
            }
            return "redirect:/addcoupon";
        }
        return "redirect:/addcoupon";
    }
    
}
