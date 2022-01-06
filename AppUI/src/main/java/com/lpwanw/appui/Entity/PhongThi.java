package com.lpwanw.appui.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@ToString
@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PhongThi {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private TrinhDo trinhdo;
    @ManyToOne
    private KhoaThi khoathi;
    @ManyToMany
    @JoinTable(
            name = "phongthis_giaoviens",
            joinColumns = @JoinColumn(name = "phongthi_id"),
            inverseJoinColumns = @JoinColumn(name = "giaovien_id"))
    private Set<GiaoVien> giaovien;
    @OneToMany(fetch = FetchType.EAGER,mappedBy="phongthi")
    Set<DanhSachDangKy> danhsach;
    private String tenphong;
    private String batDau;
    private String ketThuc;
}
