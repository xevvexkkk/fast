package com.frame.fast.cms.coupon;

import com.frame.fast.CouponFacade;
import com.frame.fast.model.*;
import com.frame.fast.service.channel.IChannelService;
import com.frame.fast.service.coupon.ICouponBatchService;
import com.frame.fast.service.coupon.ICouponService;
import com.frame.fast.util.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/coupon")
public class CmsCouponController {

    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    @Resource
    private ICouponService couponService;
    @Resource
    private ICouponBatchService couponBatchService;
    @Resource
    private CouponFacade couponFacade;
    @Resource
    private IChannelService channelService;
    @GetMapping("/toList")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String toList(Model model){
        model.addAttribute("products", ProductSort.values());
        model.addAttribute("types", CouponType.values());
        model.addAttribute("statuses", CouponStatus.values());
        List<Channel> channels = channelService.list();
        model.addAttribute("channels", channels);

        return "coupon_list";
    }

    @GetMapping("/list")
    @ResponseBody
    public String list(Model model)  {
        List<Coupon> list = couponService.list();

        model.addAttribute("products", ProductSort.values());
        model.addAttribute("types", CouponType.values());
        model.addAttribute("statuses", CouponStatus.values());
        return JsonUtils.toJson(CouponVo.tranFromOrigin(list));
    }

    @GetMapping("/toEdit")
    @ResponseBody
    public String toEdit(Long id, Model model){
        Coupon coupon = couponService.getById(id);
        model.addAttribute("products", ProductSort.values());
        model.addAttribute("types", CouponType.values());
        model.addAttribute("statuses", CouponStatus.values());
        return JsonUtils.toJson(coupon);
    }

    @GetMapping("/doEdit")
    @ResponseBody
    public ResponseVo doEdit(Coupon coupon){
        couponService.saveOrUpdate(coupon);
        return ResponseVo.successVo();
    }

    @GetMapping("/batch/toList")
    public String toBatchList(Model model){
        model.addAttribute("products", ProductSort.values());
        model.addAttribute("types", CouponType.values());
        model.addAttribute("statuses", CouponStatus.values());
        return "coupon_batch_list";
    }

    @GetMapping("/batch/list")
    @ResponseBody
    public String batchList(Model model)  {
        List<CouponBatch> list = couponBatchService.list();
        model.addAttribute("products", ProductSort.values());
        model.addAttribute("types", CouponType.values());
        model.addAttribute("statuses", CouponStatus.values());
        return JsonUtils.toJson(CouponBatchVo.tranFromOrigin(list));
    }

    @PostMapping("/doBatchEdit")
    @ResponseBody
    public ResponseVo doBatchEdit(CouponBatch coupon){
        couponBatchService.saveOrUpdate(coupon);
        return ResponseVo.successVo();
    }

    @GetMapping("/resetBatchStatus")
    @ResponseBody
    public ResponseVo resetBatchStatus(CouponBatch couponBatch){
        couponBatch = couponBatchService.getById(couponBatch.getId());
        Coupon coupon = couponService.getById(couponBatch.getCouponId());
        couponBatch.setCustomId(null);
        couponBatch.setOpenId(null);
        couponBatch.setStatus(coupon.getStatus());
        couponBatchService.reset(couponBatch);
        return ResponseVo.successVo();
    }

    @GetMapping("/generate")
    @ResponseBody
    public ResponseVo generateCoupons(Long id, Integer genCount){
        if(genCount == null || genCount <=0){
            return ResponseVo.failVo("生成数量不能为空");
        }
        if(genCount > 10000){
            return ResponseVo.failVo("生成数量不能超过10000");
        }
        couponFacade.batchGenerateUnbindCoupons(id,genCount);
        return ResponseVo.successVo();
    }
}
