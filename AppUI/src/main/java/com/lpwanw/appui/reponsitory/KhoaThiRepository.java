package com.lpwanw.appui.reponsitory;

import com.lpwanw.appui.Entity.KhoaThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface KhoaThiRepository extends JpaRepository<KhoaThi, Integer> {
    @Query("select k from KhoaThi k where k.ngaythi > ?1")
    KhoaThi getTopByNgaythiIsAfter(LocalDate ngaythi);
}