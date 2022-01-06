package com.lpwanw.appui.reponsitory;

import com.lpwanw.appui.Entity.DanhSachDangKy;
import com.lpwanw.appui.Entity.SoBaoDanh;
import com.lpwanw.appui.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DanhSachDangKyRepository extends JpaRepository<DanhSachDangKy, Integer> {
    DanhSachDangKy findDanhSachDangKyBySbd(SoBaoDanh sbd);
}