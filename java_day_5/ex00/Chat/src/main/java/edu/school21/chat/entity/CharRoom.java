package edu.school21.chat.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode(exclude = "chatRoomId")
public class CharRoom {
    private Long chatRoomId;
    private String name;
    private User owner;
    private List<Message> messages;
}
