package com.lpwanw.appui.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class DanhSachDangKy {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private SoBaoDanh sbd;
    @ManyToOne
    @JoinColumn(name="phongthi_id", nullable=false)
    private PhongThi phongthi;
    private int nghe;
    private int noi;
    private int doc;
    private int viet;
}
