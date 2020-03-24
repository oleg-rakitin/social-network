delete from user_subscriptions;
delete from user_role;
delete from usr;


insert into usr(id, username,email, password, activation_code, active) values
(51, 'testuser','test@test.com', '$2a$08$un6Gb4h09Htl1Nl6VzBnZupmk8lOQnJZ3kwScC075KDq3D9OhZ8N2','test_activate', true);

insert into user_role(user_id, roles) values
(51, 'ADMIN'), (51, 'USER');
