INSERT INTO public.users(nickname, money_spent, password)
	VALUES ('Miras', 0.00, 'abc'),('Dias', 0.00, 'cba'),('Maksat', 0.00, '123');
INSERT INTO public.developers(user_id, money_gained)
VALUES (1, 0.00);
INSERT INTO public.admins(user_id)
VALUES (2);
INSERT INTO public.games(
	name, price, genre)
	VALUES ('Dota 2', 0.00, 'MOBA'),('Hollow Knight',14.99, 'Metroidvania'),('DeadCells', 24.99, 'Roguelike'),('Enter The Gungeon',17.99, 'Roguelike'),('Cyberpunk 2077',59.99, 'RPG');