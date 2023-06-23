package com.wilterson.controller;

import com.wilterson.domain.Merchant;
import com.wilterson.dto.MerchantDto;
import com.wilterson.repository.MerchantRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class BusinessController {

    @PostMapping("/merchants")
    public ResponseEntity<Void> addMerchant(@RequestBody MerchantDto merchantDto) {
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(merchantDto, merchant);
        return ResponseEntity.noContent().build();
    }

}
