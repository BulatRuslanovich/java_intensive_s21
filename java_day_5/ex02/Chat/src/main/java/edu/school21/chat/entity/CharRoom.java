package edu.school21.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(exclude = "chatRoomId")
public class CharRoom {
    private Long chatRoomId;
    private String name;
    private User owner;
    private List<Message> messages;
}
