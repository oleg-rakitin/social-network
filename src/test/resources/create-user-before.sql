delete from message_likes;
delete from message_likes;

delete from user_subscriptions;
delete from user_role;
delete from usr;

insert into usr(id, username,email, password, activation_code, active) values
(1, 'dru','test@test', '$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h.oFRzC','dru_activate', true),
(2, 'mike','test1@test', '$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h.oFRzC','mike_activate', true);

insert into user_role(user_id, roles) values
(1, 'ADMIN'), (1, 'USER'),
(2, 'USER');
