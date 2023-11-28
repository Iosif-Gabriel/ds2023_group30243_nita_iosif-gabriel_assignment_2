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

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: device; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.device (
    id bytea NOT NULL,
    address character varying(255),
    description character varying(255),
    maxhconsumption integer,
    owner_id bytea
);


ALTER TABLE public.device OWNER TO postgres;

--
-- Name: mapper; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mapper (
    id bytea NOT NULL
);


ALTER TABLE public.mapper OWNER TO postgres;

--
-- Data for Name: device; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.device (id, address, description, maxhconsumption, owner_id) FROM stdin;
\\xe5b1c5c1e01b47f0a823974d8f8fadae	1123	123	12	\\x752383ba32c74eccb01a9b1d5b7c8f5e
\\x0910342ae2964a2faf05819ace553f51	qwe	123qwe	12	\\x7d6c2d0ead6d46db8e675ee8960c8f3c
\\x2fdb679991254319ac13dd5c27f96990	a	a	123	\\x752383ba32c74eccb01a9b1d5b7c8f5e
\\x6dc838b42b3c414b8286a84026c3a866	qwe	qweee	123	\\x7d6c2d0ead6d46db8e675ee8960c8f3c
\\x520ad0b0eaac4037832b61653269d7d4	1234	1234	12	\\x752383ba32c74eccb01a9b1d5b7c8f5e
\.


--
-- Data for Name: mapper; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mapper (id) FROM stdin;
\\x6513a7fb8b634f2394cfe4f9994079b9
\\x451e166a117f40f9b3f76f86152f9889
\\x752383ba32c74eccb01a9b1d5b7c8f5e
\\x7d6c2d0ead6d46db8e675ee8960c8f3c
\.


--
-- Name: device device_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device
    ADD CONSTRAINT device_pkey PRIMARY KEY (id);


--
-- Name: mapper mapper_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mapper
    ADD CONSTRAINT mapper_pkey PRIMARY KEY (id);


--
-- Name: device fkmmmlgfgcp7pl7s6pauetwqbpk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.device
    ADD CONSTRAINT fkmmmlgfgcp7pl7s6pauetwqbpk FOREIGN KEY (owner_id) REFERENCES public.mapper(id);


--
-- PostgreSQL database dump complete
--

