package fr.soat.java.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    private String id;
    private List<ProductDto> products;
    private String creationDate;
    private String modificationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProductDto> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }
}
