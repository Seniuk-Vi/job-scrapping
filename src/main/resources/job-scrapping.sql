--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1 (Debian 16.1-1.pgdg120+1)
-- Dumped by pg_dump version 16.1

-- Started on 2024-01-05 14:15:31 UTC

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
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3368 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16387)
-- Name: filters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.filters (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.filters OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16403)
-- Name: filters_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.filters_id_seq
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.filters_id_seq OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16392)
-- Name: jobs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.jobs (
    id bigint NOT NULL,
    description text,
    job_page_url character varying(255),
    labor_function character varying(255),
    location character varying(255),
    logo character varying(255),
    organization_title character varying(255),
    position_name character varying(255),
    posted_date bigint NOT NULL,
    tag_names character varying(255),
    url_to_organization character varying(255)
);


ALTER TABLE public.jobs OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16404)
-- Name: jobs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.jobs_id_seq
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.jobs_id_seq OWNER TO postgres;

--
-- TOC entry 3359 (class 0 OID 16387)
-- Dependencies: 215
-- Data for Name: filters; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.filters VALUES (1, 'Administrative Services');
INSERT INTO public.filters VALUES (2, 'Advertising');
INSERT INTO public.filters VALUES (3, 'Agriculture and Farming');
INSERT INTO public.filters VALUES (4, 'Biotechnology');
INSERT INTO public.filters VALUES (5, 'Blockchain and Crypto');
INSERT INTO public.filters VALUES (6, 'Clothing and Apparel');
INSERT INTO public.filters VALUES (7, 'Commerce and Shopping');
INSERT INTO public.filters VALUES (8, 'Community and Lifestyle');
INSERT INTO public.filters VALUES (9, 'Construction');
INSERT INTO public.filters VALUES (10, 'Consulting');
INSERT INTO public.filters VALUES (11, 'Consumer Products');
INSERT INTO public.filters VALUES (12, 'Content and Publishing');
INSERT INTO public.filters VALUES (13, 'Accounting & Finance');
INSERT INTO public.filters VALUES (14, 'Administration');
INSERT INTO public.filters VALUES (15, 'Customer Service');
INSERT INTO public.filters VALUES (16, 'Data Science');
INSERT INTO public.filters VALUES (17, 'Design');
INSERT INTO public.filters VALUES (18, 'IT');
INSERT INTO public.filters VALUES (19, 'Legal');
INSERT INTO public.filters VALUES (20, 'Marketing & Communications');
INSERT INTO public.filters VALUES (21, 'Operations');
INSERT INTO public.filters VALUES (22, 'Other Engineering');
INSERT INTO public.filters VALUES (23, 'People & HR');
INSERT INTO public.filters VALUES (24, 'Product');
INSERT INTO public.filters VALUES (25, 'Remote');
INSERT INTO public.filters VALUES (26, 'United States');
INSERT INTO public.filters VALUES (27, 'New York, NY, USA');
INSERT INTO public.filters VALUES (28, 'El Paso, TX, USA');
INSERT INTO public.filters VALUES (29, 'Bengaluru, Karnataka, India');


--
-- TOC entry 3360 (class 0 OID 16392)
-- Dependencies: 216
-- Data for Name: jobs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3369 (class 0 OID 0)
-- Dependencies: 217
-- Name: filters_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.filters_id_seq', 31, true);


--
-- TOC entry 3370 (class 0 OID 0)
-- Dependencies: 218
-- Name: jobs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.jobs_id_seq', 1, false);


--
-- TOC entry 3209 (class 2606 OID 16391)
-- Name: filters filters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.filters
    ADD CONSTRAINT filters_pkey PRIMARY KEY (id);


--
-- TOC entry 3213 (class 2606 OID 16398)
-- Name: jobs jobs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jobs
    ADD CONSTRAINT jobs_pkey PRIMARY KEY (id);


--
-- TOC entry 3211 (class 2606 OID 16400)
-- Name: filters uk_8u9la6i5ymabqkdfkx7sxv3e1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.filters
    ADD CONSTRAINT uk_8u9la6i5ymabqkdfkx7sxv3e1 UNIQUE (name);


--
-- TOC entry 3215 (class 2606 OID 16402)
-- Name: jobs uk_q7cskiy7v0mwh660teqw09pj1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jobs
    ADD CONSTRAINT uk_q7cskiy7v0mwh660teqw09pj1 UNIQUE (job_page_url);


-- Completed on 2024-01-05 14:15:31 UTC

--
-- PostgreSQL database dump complete
--

