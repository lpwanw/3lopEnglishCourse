package com.lpwanw.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPice {
    private String symbol;
    private Double price;
    private LocalDate time;

}
