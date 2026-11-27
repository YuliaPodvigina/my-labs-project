-- Выполняется один раз, при первом создании базы в контейнере.
-- Создаём своего пользователя и свою схему, чтобы не работать под postgres и в public.
CREATE USER course_user WITH PASSWORD 'course_password';
CREATE SCHEMA course_schema AUTHORIZATION course_user;
