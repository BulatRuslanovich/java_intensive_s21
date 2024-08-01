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
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "userId")
public class User {
    private Long userId;
    private String login;
    private String password;
    private List<CharRoom> createdRooms;
    private List<CharRoom> socializeRooms;
}
