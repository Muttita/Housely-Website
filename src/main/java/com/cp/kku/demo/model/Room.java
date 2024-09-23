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
@Table(name = "room")
public class Room {

    public Room() {
		super();
	}

	public Room(Long id, String description, String imageBase64, String roomName, List<Product> productsInRoom) {
		super();
		this.id = id;
		this.description = description;
		this.imageBase64 = imageBase64;
		this.roomName = roomName;
		this.productsInRoom = productsInRoom;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public List<Product> getProductsInRoom() {
		return productsInRoom;
	}

	public void setProductsInRoom(List<Product> productsInRoom) {
		this.productsInRoom = productsInRoom;
	}

	@Column(name = "description", length = 1000)
    private String description;

    @Lob
    @Column(name = "image_base64")
    private String imageBase64;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @ManyToMany(mappedBy = "rooms", fetch = FetchType.LAZY)
    private List<Product> productsInRoom = new ArrayList<>();
}

