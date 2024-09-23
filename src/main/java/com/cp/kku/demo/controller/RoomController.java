package com.cp.kku.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cp.kku.demo.model.Room;
import com.cp.kku.demo.service.RoomService;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public String listRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();
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
        roomService.saveRoom(room);
        return "redirect:/rooms"; // Redirect to the room list after saving
    }

    @GetMapping("/edit/{id}")
    public String showEditRoomForm(@PathVariable Long id, Model model) {
        Room room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        return "edit-room-form"; // Return the template for editing a room
    }
    
    @PostMapping("/save/{id}")
    public String saveRoom(@ModelAttribute("room") Room room, @PathVariable Long id) {
    	room.setId(id);
        roomService.saveRoom(room);
        return "redirect:/rooms"; // Redirect to the room list after saving
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/rooms"; // Redirect to the room list after deletion
    }
}
