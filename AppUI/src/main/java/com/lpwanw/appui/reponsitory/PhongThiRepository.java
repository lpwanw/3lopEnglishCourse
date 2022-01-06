package com.lpwanw.appui.reponsitory;

import com.lpwanw.appui.Entity.KhoaThi;
import com.lpwanw.appui.Entity.PhongThi;
import com.lpwanw.appui.Entity.TrinhDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhongThiRepository extends JpaRepository<PhongThi, Integer> {
    List<PhongThi> getPhongThisByKhoathi(KhoaThi khoathi);
    List<PhongThi> findPhongThiByKhoathiAndTrinhdo(KhoaThi khoathi, TrinhDo trinhdo);
}