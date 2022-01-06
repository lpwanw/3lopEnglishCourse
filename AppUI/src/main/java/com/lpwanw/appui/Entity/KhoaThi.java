package com.lpwanw.appui.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@ToString
@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class KhoaThi {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ten;

    private LocalDate ngaythi;
}
