INSERT INTO chat.user (login, password)
VALUES
    ('Ryan Gosling 1', '1234'),
    ('Ryan Gosling 2', '4567'),
    ('Ryan Gosling 3', '7890'),
    ('Ryan Gosling 4', '0123'),
    ('Ryan Gosling 5', '4567');

INSERT INTO chat.chatroom (name, user_id)
VALUES
    ('general', (SELECT user_id FROM chat.user WHERE login = 'Ryan Gosling 1')),
    ('random', (SELECT user_id FROM chat.user WHERE login = 'Ryan Gosling 2')),
    ('memes', (SELECT user_id FROM chat.user WHERE login = 'Ryan Gosling 3')),
    ('musicBot', (SELECT user_id FROM chat.user WHERE login = 'Ryan Gosling 4')),
    ('art', (SELECT user_id FROM chat.user WHERE login = 'Ryan Gosling 5'));

INSERT INTO chat.message (user_id, chatroom_id, text, date_time)
VALUES
    ((SELECT user_id FROM chat.user WHERE login = 'Ryan Gosling 1'), 1, 'This is general channel.', (SELECT NOW()::timestamp)),
    ((SELECT user_id FROM chat.user WHERE login = 'Ryan Gosling 2'), 2, 'This is random channel.', (SELECT NOW()::timestamp)),
    ((SELECT user_id FROM chat.user WHERE login = 'Ryan Gosling 3'), 3, 'This is memes channel.', (SELECT NOW()::timestamp)),
    ((SELECT user_id FROM chat.user WHERE login = 'Ryan Gosling 4'), 4, 'This is musicBot channel.', (SELECT NOW()::timestamp)),
    ((SELECT user_id FROM chat.user WHERE login = 'Ryan Gosling 5'), 5, 'This is art channel.', (SELECT NOW()::timestamp));



