package com.cp.kku.housely.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cp.kku.housely.model.Room;
import com.cp.kku.housely.service.RoomService;

import java.util.List;

@Controller
@RequestMapping("/admin/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public String listRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms().collectList().block();
        model.addAttribute("rooms", rooms);
        return "room-list"; // Return the template for displaying rooms
    }

    @GetMapping("/add")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "add-room-form"; // Return the template for adding a room
    }

    @PostMapping("/save")
    public String saveRoom(@ModelAttribute("room") Room room) {
        System.out.println("here");
        roomService.createRoom(room).block();
        return "redirect:/rooms"; // Redirect to the room list after saving
    }

    @GetMapping("/edit/{id}")
    public String showEditRoomForm(@PathVariable Long id, Model model) {
        Room room = roomService.getRoomById(id).block();
        model.addAttribute("room", room);
        return "edit-room-form"; // Return the template for editing a room
    }
    
    @PostMapping("/save/{id}")
    public String saveRoom(@ModelAttribute("room") Room room, @PathVariable Long id) {
    	room.setId(id);
        roomService.createRoom(room).block();
        return "redirect:/rooms"; // Redirect to the room list after saving
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id).block();
        return "redirect:/rooms"; // Redirect to the room list after deletion
    }
}
