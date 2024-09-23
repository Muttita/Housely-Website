package com.cp.kku.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "description", length = 1000)
    private String description;

    @Lob
    @Column(name = "image_base64")
    private String imageBase64;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<Product> productsInCategory = new ArrayList<>();

	public Category(Long categoryId, String categoryName, String description, String imageBase64,
			List<Product> productsInCategory) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.description = description;
		this.imageBase64 = imageBase64;
		this.productsInCategory = productsInCategory;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}

	public List<Product> getProductsInCategory() {
		return productsInCategory;
	}

	public void setProductsInCategory(List<Product> productsInCategory) {
		this.productsInCategory = productsInCategory;
	}

	public Category() {
		super();
	}



}
