--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: utilizator; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utilizator (
    id bytea NOT NULL,
    password character varying(255),
    user_role character varying(255),
    username character varying(255)
);


ALTER TABLE public.utilizator OWNER TO postgres;

--
-- Data for Name: utilizator; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.utilizator (id, password, user_role, username) FROM stdin;
\\x6513a7fb8b634f2394cfe4f9994079b9	lala	ADMIN	lala
\\x451e166a117f40f9b3f76f86152f9889	123	ADMIN	lalala
\\x752383ba32c74eccb01a9b1d5b7c8f5e	123	USER	123
\\x7d6c2d0ead6d46db8e675ee8960c8f3c	qwe	USER	qwe
\.


--
-- Name: utilizator uk_d2wc54g88nka04vi4nhe9hdn9; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utilizator
    ADD CONSTRAINT uk_d2wc54g88nka04vi4nhe9hdn9 UNIQUE (username);


--
-- Name: utilizator utilizator_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utilizator
    ADD CONSTRAINT utilizator_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

