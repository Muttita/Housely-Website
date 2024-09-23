package com.cp.kku.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cp.kku.demo.model.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
	List<Room> findAll();
}

