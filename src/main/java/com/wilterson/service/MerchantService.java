package com.wilterson.service;

import com.wilterson.domain.Merchant;
import com.wilterson.repository.MerchantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public Merchant addMerchant(Merchant merchant) {
        return merchantRepository.save(merchant);
    }
}
