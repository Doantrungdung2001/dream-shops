package com.biprogy.dreamshops.request;

import com.biprogy.dreamshops.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int iventory;
    private String description;
    private Category category;
}
