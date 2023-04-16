CREATE TABLE IF NOT EXISTS public.delivery
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    delivery_address character varying(255) COLLATE pg_catalog."default",
    delivery_time character varying(255) COLLATE pg_catalog."default",
    receiver_name character varying(255) COLLATE pg_catalog."default",
    receiver_phone character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT delivery_pkey PRIMARY KEY (id)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS public.delivery
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.users
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    age integer NOT NULL,
    email character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    password character varying(100) COLLATE pg_catalog."default",
    phone_number character varying(255) COLLATE pg_catalog."default",
    role character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.souse
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    pizza_parts_type integer,
    price integer NOT NULL,
    weight integer NOT NULL,
    spice boolean NOT NULL,
    CONSTRAINT souse_pkey PRIMARY KEY (id),
    CONSTRAINT uk_4bpa0mw4v9u8ym66toufkf01y UNIQUE (name)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS public.souse
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.ingredient
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    pizza_parts_type integer,
    price integer NOT NULL,
    weight integer NOT NULL,
    vegetarian boolean NOT NULL,
    CONSTRAINT ingredient_pkey PRIMARY KEY (id),
    CONSTRAINT uk_bcuaj97y3iu3t2vj26jg6hijj UNIQUE (name)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS public.ingredient
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.default_pizza
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    available_status boolean NOT NULL,
    dish_type character varying(255) COLLATE pg_catalog."default",
    price integer NOT NULL,
    pizza_type character varying(255) COLLATE pg_catalog."default",
    size integer NOT NULL,
    weight integer NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    image character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT default_pizza_pkey PRIMARY KEY (id)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS public.default_pizza
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.drinks
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    available_status boolean NOT NULL,
    dish_type character varying(255) COLLATE pg_catalog."default",
    price integer NOT NULL,
    drinks_type character varying(255) COLLATE pg_catalog."default",
    image character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    volume integer NOT NULL,
    CONSTRAINT drinks_pkey PRIMARY KEY (id)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS public.drinks
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.own_pizza
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    available_status boolean NOT NULL,
    dish_type character varying(255) COLLATE pg_catalog."default",
    price integer NOT NULL,
    pizza_type character varying(255) COLLATE pg_catalog."default",
    size integer NOT NULL,
    weight integer NOT NULL,
    souse_id character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT own_pizza_pkey PRIMARY KEY (id),
    CONSTRAINT fkmp70wn1pos65dvyjywhojel2t FOREIGN KEY (souse_id)
        REFERENCES public.souse (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS public.own_pizza
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.own_pizza_ingredients
(
    order_identifier character varying(255) COLLATE pg_catalog."default" NOT NULL,
    dish_identifier character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT fk6u41xxsasfm18bqg3xrioj39t FOREIGN KEY (order_identifier)
        REFERENCES public.own_pizza (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fka4iobaghqiymyqdqg93fd1jla FOREIGN KEY (dish_identifier)
        REFERENCES public.ingredient (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS public.own_pizza_ingredients
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.orders
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_creation date,
    price integer NOT NULL,
    delivery_id character varying(255) COLLATE pg_catalog."default",
    user_id character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT orders_pkey PRIMARY KEY (id),
    CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fktkrur7wg4d8ax0pwgo0vmy20c FOREIGN KEY (delivery_id)
        REFERENCES public.delivery (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS public.orders
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.orders_dishes
(
    order_identifier character varying(255) COLLATE pg_catalog."default" NOT NULL,
    dish_identifier character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT fkcgiok63ru2ixvu9g53w38eoui FOREIGN KEY (order_identifier)
        REFERENCES public.orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS public.orders_dishes
    OWNER to postgres;
