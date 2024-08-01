package edu.school21.chat.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(exclude = "messageId")
public class Message {
    private Long messageId;
    private User author;
    private CharRoom room;
    private String text;
    private LocalDateTime dateTime;
}
