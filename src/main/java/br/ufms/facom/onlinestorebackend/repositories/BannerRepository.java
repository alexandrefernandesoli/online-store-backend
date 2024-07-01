package br.ufms.facom.onlinestorebackend.repositories;

import br.ufms.facom.onlinestorebackend.models.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}
