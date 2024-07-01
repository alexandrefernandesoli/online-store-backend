package br.ufms.facom.onlinestorebackend.services;

import br.ufms.facom.onlinestorebackend.repositories.BannerRepository;
import org.springframework.stereotype.Service;

@Service
public class BannerService {
    private final BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public void createBanner() {

    }

}
