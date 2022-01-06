package com.lpwanw.appui.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class SoBaoDanh {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Student nguoidangky;
    @ManyToOne
    private KhoaThi khoaThi;
    @ManyToOne
    private TrinhDo trinhDo;
    private String soBaoDanh;
}
