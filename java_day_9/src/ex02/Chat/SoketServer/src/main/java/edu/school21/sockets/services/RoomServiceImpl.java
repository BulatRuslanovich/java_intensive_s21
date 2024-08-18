package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void createRoom(Chatroom room) {
        if (roomRepository.findByTitle(room.getTitle()).isPresent()) {
            throw new RuntimeException("Room: " + room.getTitle() + " already exist");
        }

        roomRepository.save(room);
    }

    @Override
    public List<Chatroom> showAllRooms() {
        return roomRepository.findAll();
    }
}
