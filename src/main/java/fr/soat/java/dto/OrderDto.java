package fr.soat.java.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDto {
    private String id;
    private List<ProductDto> productList;
    private String creationDate;
    private String modificationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProductDto> getProductList() {
        if (productList == null) {
            productList = new ArrayList<>();
        }
        return productList;
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
