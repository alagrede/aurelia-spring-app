
--
-- Sample dataset containing a users 
--
-- =================================================================================================
insert into user(id, firstname, lastname, birthdate, active) values (1, "Kanako", "Momota", "1995-05-02", false);
insert into user(id, firstname, lastname, birthdate, active) values (2, "Shiori", "Tamai", "1952-08-22", false);
insert into user(id, firstname, lastname, birthdate, active) values (3, "Ayaka", "Sasaki", "1984-09-09", false);
insert into user(id, firstname, lastname, birthdate, active) values (4, "Momoka", "Ariyasu", "1985-11-15", false);
insert into user(id, firstname, lastname, birthdate, active) values (5, "Reni", "Takagi", "1990-12-12", false);

insert into user(id, firstname, lastname, birthdate, active) values (6, "Hsjdl", "maeko", "1995-05-02", false);
insert into user(id, firstname, lastname, birthdate, active) values (7, "hang", "tuinai", "1952-08-22", false);
insert into user(id, firstname, lastname, birthdate, active) values (8, "slo", "Suzuki", "1984-09-09", false);
insert into user(id, firstname, lastname, birthdate, active) values (9, "Rtil", "Arishu", "1985-11-15", false);
insert into user(id, firstname, lastname, birthdate, active) values (10, "Ray", "Takiki", "1990-12-12", false);

insert into user(id, firstname, lastname, birthdate, active) values (11, "Anthony", "LAGREDE", "1985-11-15", true);
insert into user(id, firstname, lastname, birthdate, active) values (12, "Alphonse", "Colini", "1976-12-12", false);
insert into user(id, firstname, lastname, birthdate, active) values (13, "Alberto", "Morando", "1971-10-08", false);


-- Washington
--insert into city(country, name, state, map) values ('USA', 'Washington', 'DC', '38.895112, -77.036366')
--insert into hotel(city_id, name, address, zip) values (21, 'Hotel Rouge', '1315 16th Street NW', '20036')


insert into auth_user(id, username, password, email, active, staff, superUser) values (1, "admin", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "anthony.lagrede@berger-levrault.fr", true, true, false);
insert into auth_user(id, username, password, email, active, staff, superUser) values (2, "flo1", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "flo1@flo.com", true, false, false);
insert into auth_user(id, username, password, email, active, staff, superUser) values (3, "flo2", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "flo2@flo.com", true, false, false);
insert into auth_user(id, username, password, email, active, staff, superUser) values (4, "flo3", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "flo3@flo.com", true, false, false);
insert into auth_user(id, username, password, email, active, staff, superUser) values (5, "flo4", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "flo4@flo.com", true, false, false);
insert into auth_user(id, username, password, email, active, staff, superUser) values (6, "flo5", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "flo5@flo.com", true, false, false);
insert into auth_user(id, username, password, email, active, staff, superUser) values (7, "flo6", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "flo6@flo.com", true, false, false);
insert into auth_user(id, username, password, email, active, staff, superUser) values (8, "flo7", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "flo7@flo.com", true, false, false);
insert into auth_user(id, username, password, email, active, staff, superUser) values (9, "flo8", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "flo8@flo.com", true, false, false);
insert into auth_user(id, username, password, email, active, staff, superUser) values (10, "flo9", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "flo9@flo.com", true, false, false);
insert into auth_user(id, username, password, email, active, staff, superUser) values (11, "flo10", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "flo10@flo.com", true, false, false);
insert into auth_user(id, username, password, email, active, staff, superUser) values (12, "flo11", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "flo11@flo.com", true, false, false);
insert into auth_user(id, username, password, email, active, staff, superUser) values (13, "flo12", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "flo12@flo.com", true, false, false);
insert into auth_user(id, username, password, email, active, staff, superUser) values (14, "flo13", "$2a$10$3tYHl.HotYZXeWlbJN7BDOK.GE1S7MhRLOjAK4/KeWd2d3jSBHgCy", "flo13@flo.com", true, false, false);

insert into auth_group(id, name) values (1, "visiteur");
insert into auth_group(id, name) values (2, "consultant");
insert into auth_group(id, name) values (3, "manager");

insert into auth_permission(id, name, contentTypeId, codename) values(1, "Can add auth_user", 1, "add_auth_user");
insert into auth_permission(id, name, contentTypeId, codename) values(2, "Can change auth_user", 1, "change_auth_user");
insert into auth_permission(id, name, contentTypeId, codename) values(3, "Can delete auth_user", 1, "delete_auth_user");

insert into auth_permission(id, name, contentTypeId, codename) values(4, "Can add auth_group", 2, "add_auth_group");
insert into auth_permission(id, name, contentTypeId, codename) values(5, "Can change auth_group", 2, "change_auth_group");
insert into auth_permission(id, name, contentTypeId, codename) values(6, "Can delete auth_group", 2, "delete_auth_group");

insert into auth_permission(id, name, contentTypeId, codename) values(7, "Can connect", 3, "connected_role");

insert into auth_group_permissions(group_id, permission_id) values(3, 1); -- role add_auth_user
insert into auth_group_permissions(group_id, permission_id) values(3, 2); -- role change_auth_user
insert into auth_group_permissions(group_id, permission_id) values(3, 3); -- role delete_auth_user
insert into auth_group_permissions(group_id, permission_id) values(3, 7); -- role de connexion

insert into auth_group_permissions(group_id, permission_id) values(3, 4); -- role add_auth_group
insert into auth_group_permissions(group_id, permission_id) values(3, 5); -- role change_auth_group
insert into auth_group_permissions(group_id, permission_id) values(3, 6); -- role delete_auth_group

insert into auth_user_groups(user_id, group_id) values(1, 3); -- groupe manager pour admin

insert into auth_user_user_permissions(user_id, permission_id) values(2, 7);-- role de connexion pour flo

-- Données mini pour la gestion du multi-tenant 
insert into domaingroup values(1, 'tenant1', 'admin');
insert into domaingroup values(2,'tenant2', 'flo');
