package fr.soat.java.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDto {
    private String id;
    private List<ProductDto> productList;
    private Date creationDate;
    private Date modificationDate;

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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}
