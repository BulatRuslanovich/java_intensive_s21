package edu.school21.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = "userId")
@AllArgsConstructor(staticName = "of")
public class User {
    private Long userId;
    private String login;
    private String password;
    private Boolean status;
}
