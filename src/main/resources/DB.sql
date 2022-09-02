CREATE DATABASE db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Ukraine.1251'
    LC_CTYPE = 'Russian_Ukraine.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

    CREATE TABLE IF NOT EXISTS public.invoice
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    sum double precision NOT NULL,
    "time" date NOT NULL,
    CONSTRAINT invoice_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.invoice
    OWNER to postgres;



    CREATE TABLE IF NOT EXISTS public.phone
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    count integer NOT NULL,
    manufacturer character varying COLLATE pg_catalog."default" NOT NULL,
    model character varying COLLATE pg_catalog."default" NOT NULL,
    price double precision NOT NULL,
    title character varying COLLATE pg_catalog."default" NOT NULL,
    invoice_id character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT phone_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.phone
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.headphones
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    count integer NOT NULL,
    headphones_type character varying COLLATE pg_catalog."default" NOT NULL,
    manufacturer character varying COLLATE pg_catalog."default" NOT NULL,
    model character varying COLLATE pg_catalog."default" NOT NULL,
    price double precision NOT NULL,
    title character varying COLLATE pg_catalog."default" NOT NULL,
    invoice_id character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT headphones_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.headphones
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.laptop
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    count integer NOT NULL,
    manufacturer character varying COLLATE pg_catalog."default" NOT NULL,
    model character varying COLLATE pg_catalog."default" NOT NULL,
    price double precision NOT NULL,
    title character varying COLLATE pg_catalog."default" NOT NULL,
    laptop_type character varying COLLATE pg_catalog."default" NOT NULL,
    invoice_id character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT laptop_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.laptop
    OWNER to postgres;


