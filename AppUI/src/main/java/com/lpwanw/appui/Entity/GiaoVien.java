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
public class GiaoVien {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String hoten;
    private String gioitinh;
    private LocalDate NgaySinh;
    private String email;
    private String sdt;
    @ManyToMany
    @JoinTable(
            name = "phongthis_giaoviens",
            joinColumns = @JoinColumn(name = "giaovien_id"),
            inverseJoinColumns = @JoinColumn(name = "phongthi_id"))
    Set<PhongThi> phongthi;
}
