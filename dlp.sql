--
-- PostgreSQL database dump
--

-- Dumped from database version 14.12 (Ubuntu 14.12-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.12 (Ubuntu 14.12-0ubuntu0.22.04.1)

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
-- Name: alert_levels; Type: TYPE; Schema: public; Owner: oliver
--

CREATE TYPE public.alert_levels AS ENUM (
    'High',
    'Medium',
    'Low'
);


ALTER TYPE public.alert_levels OWNER TO oliver;

--
-- Name: detection_types; Type: TYPE; Schema: public; Owner: oliver
--

CREATE TYPE public.detection_types AS ENUM (
    'Dictionary',
    'Pattern',
    'Tag'
);


ALTER TYPE public.detection_types OWNER TO oliver;

--
-- Name: dlp_actions; Type: TYPE; Schema: public; Owner: oliver
--

CREATE TYPE public.dlp_actions AS ENUM (
    'Alert',
    'Redact',
    'Block'
);


ALTER TYPE public.dlp_actions OWNER TO oliver;

--
-- Name: user_status; Type: TYPE; Schema: public; Owner: oliver
--

CREATE TYPE public.user_status AS ENUM (
    'BLOCKED',
    'SUSPICIOUS',
    'NORMAL'
);


ALTER TYPE public.user_status OWNER TO oliver;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: custom_context_words; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.custom_context_words (
    id bigint NOT NULL,
    entity_type_id bigint,
    word character varying(255) NOT NULL
);


ALTER TABLE public.custom_context_words OWNER TO oliver;

--
-- Name: custom_context_words_id_seq; Type: SEQUENCE; Schema: public; Owner: oliver
--

CREATE SEQUENCE public.custom_context_words_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.custom_context_words_id_seq OWNER TO oliver;

--
-- Name: custom_context_words_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: oliver
--

ALTER SEQUENCE public.custom_context_words_id_seq OWNED BY public.custom_context_words.id;


--
-- Name: custom_deny_list; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.custom_deny_list (
    id bigint NOT NULL,
    entity_type_id bigint,
    value character varying(255) NOT NULL
);


ALTER TABLE public.custom_deny_list OWNER TO oliver;

--
-- Name: custom_deny_list_id_seq; Type: SEQUENCE; Schema: public; Owner: oliver
--

CREATE SEQUENCE public.custom_deny_list_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.custom_deny_list_id_seq OWNER TO oliver;

--
-- Name: custom_deny_list_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: oliver
--

ALTER SEQUENCE public.custom_deny_list_id_seq OWNED BY public.custom_deny_list.id;


--
-- Name: custom_entity_types; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.custom_entity_types (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255) DEFAULT ''::text NOT NULL,
    detection_type character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by bigint,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by integer
);


ALTER TABLE public.custom_entity_types OWNER TO oliver;

--
-- Name: custom_entity_types_id_seq; Type: SEQUENCE; Schema: public; Owner: oliver
--

CREATE SEQUENCE public.custom_entity_types_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.custom_entity_types_id_seq OWNER TO oliver;

--
-- Name: custom_entity_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: oliver
--

ALTER SEQUENCE public.custom_entity_types_id_seq OWNED BY public.custom_entity_types.id;


--
-- Name: custom_patterns; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.custom_patterns (
    id bigint NOT NULL,
    entity_type_id bigint,
    name character varying(255) NOT NULL,
    regex character varying(255) NOT NULL,
    score real NOT NULL
);


ALTER TABLE public.custom_patterns OWNER TO oliver;

--
-- Name: custom_patterns_id_seq; Type: SEQUENCE; Schema: public; Owner: oliver
--

CREATE SEQUENCE public.custom_patterns_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.custom_patterns_id_seq OWNER TO oliver;

--
-- Name: custom_patterns_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: oliver
--

ALTER SEQUENCE public.custom_patterns_id_seq OWNED BY public.custom_patterns.id;


--
-- Name: default_entity_types; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.default_entity_types (
    name character varying(255) NOT NULL,
    description text
);


ALTER TABLE public.default_entity_types OWNER TO oliver;

--
-- Name: file_metadata; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.file_metadata (
    id integer NOT NULL,
    key character varying(24) NOT NULL,
    value character varying(24) NOT NULL,
    filetype character varying(24),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp without time zone
);


ALTER TABLE public.file_metadata OWNER TO oliver;

--
-- Name: file_metadata_id_seq; Type: SEQUENCE; Schema: public; Owner: oliver
--

CREATE SEQUENCE public.file_metadata_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.file_metadata_id_seq OWNER TO oliver;

--
-- Name: file_metadata_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: oliver
--

ALTER SEQUENCE public.file_metadata_id_seq OWNED BY public.file_metadata.id;


--
-- Name: groups_rules; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.groups_rules (
    rule_id bigint NOT NULL,
    network_id bigint NOT NULL
);


ALTER TABLE public.groups_rules OWNER TO oliver;

--
-- Name: history; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.history (
    id integer NOT NULL,
    code character varying(255) DEFAULT gen_random_uuid() NOT NULL,
    originated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    origin character varying(255) NOT NULL,
    destination character varying(255) NOT NULL,
    sensitive_data character varying(255) NOT NULL,
    results jsonb NOT NULL,
    level character varying(255) NOT NULL,
    action character varying(255) NOT NULL,
    text character varying(255) NOT NULL,
    text_redacted character varying(255) NOT NULL,
    file boolean DEFAULT false NOT NULL,
    metadata jsonb
);


ALTER TABLE public.history OWNER TO oliver;

--
-- Name: history_id_seq; Type: SEQUENCE; Schema: public; Owner: oliver
--

CREATE SEQUENCE public.history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.history_id_seq OWNER TO oliver;

--
-- Name: history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: oliver
--

ALTER SEQUENCE public.history_id_seq OWNED BY public.history.id;


--
-- Name: network_endpoint; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.network_endpoint (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    network_id bigint,
    ip character varying(255) NOT NULL,
    status character varying(255) DEFAULT 'NORMAL'::public.user_status NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.network_endpoint OWNER TO oliver;

--
-- Name: networks; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.networks (
    id bigint NOT NULL,
    subnet character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by bigint,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by bigint
);


ALTER TABLE public.networks OWNER TO oliver;

--
-- Name: networks_id_seq; Type: SEQUENCE; Schema: public; Owner: oliver
--

CREATE SEQUENCE public.networks_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.networks_id_seq OWNER TO oliver;

--
-- Name: networks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: oliver
--

ALTER SEQUENCE public.networks_id_seq OWNED BY public.networks.id;


--
-- Name: permissions; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.permissions (
    id integer NOT NULL,
    permission character varying(255) NOT NULL
);


ALTER TABLE public.permissions OWNER TO oliver;

--
-- Name: permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: oliver
--

CREATE SEQUENCE public.permissions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.permissions_id_seq OWNER TO oliver;

--
-- Name: permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: oliver
--

ALTER SEQUENCE public.permissions_id_seq OWNED BY public.permissions.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.roles (
    id bigint NOT NULL,
    role character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by integer,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by integer
);


ALTER TABLE public.roles OWNER TO oliver;

--
-- Name: roles_permissions; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.roles_permissions (
    role_id bigint NOT NULL,
    permission_id integer NOT NULL
);


ALTER TABLE public.roles_permissions OWNER TO oliver;

--
-- Name: rols_id_seq; Type: SEQUENCE; Schema: public; Owner: oliver
--

CREATE SEQUENCE public.rols_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rols_id_seq OWNER TO oliver;

--
-- Name: rols_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: oliver
--

ALTER SEQUENCE public.rols_id_seq OWNED BY public.roles.id;


--
-- Name: rules; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.rules (
    id bigint NOT NULL,
    codigo character varying(255) DEFAULT gen_random_uuid() NOT NULL,
    name character varying(255) NOT NULL,
    entity_id bigint NOT NULL,
    action character varying(255) NOT NULL,
    level character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    status boolean DEFAULT true NOT NULL,
    confidence_level double precision NOT NULL,
    hits_lower integer NOT NULL,
    hits_upper integer NOT NULL,
    alerts boolean DEFAULT false NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by bigint,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by bigint
);


ALTER TABLE public.rules OWNER TO oliver;

--
-- Name: rules_id_seq; Type: SEQUENCE; Schema: public; Owner: oliver
--

CREATE SEQUENCE public.rules_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rules_id_seq OWNER TO oliver;

--
-- Name: rules_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: oliver
--

ALTER SEQUENCE public.rules_id_seq OWNED BY public.rules.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: oliver
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role_id bigint NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by bigint,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by bigint
);


ALTER TABLE public.users OWNER TO oliver;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: oliver
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO oliver;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: oliver
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.network_endpoint.id;


--
-- Name: users_id_seq1; Type: SEQUENCE; Schema: public; Owner: oliver
--

CREATE SEQUENCE public.users_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq1 OWNER TO oliver;

--
-- Name: users_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: oliver
--

ALTER SEQUENCE public.users_id_seq1 OWNED BY public.users.id;


--
-- Name: custom_context_words id; Type: DEFAULT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_context_words ALTER COLUMN id SET DEFAULT nextval('public.custom_context_words_id_seq'::regclass);


--
-- Name: custom_deny_list id; Type: DEFAULT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_deny_list ALTER COLUMN id SET DEFAULT nextval('public.custom_deny_list_id_seq'::regclass);


--
-- Name: custom_entity_types id; Type: DEFAULT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_entity_types ALTER COLUMN id SET DEFAULT nextval('public.custom_entity_types_id_seq'::regclass);


--
-- Name: custom_patterns id; Type: DEFAULT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_patterns ALTER COLUMN id SET DEFAULT nextval('public.custom_patterns_id_seq'::regclass);


--
-- Name: file_metadata id; Type: DEFAULT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.file_metadata ALTER COLUMN id SET DEFAULT nextval('public.file_metadata_id_seq'::regclass);


--
-- Name: history id; Type: DEFAULT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.history ALTER COLUMN id SET DEFAULT nextval('public.history_id_seq'::regclass);


--
-- Name: network_endpoint id; Type: DEFAULT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.network_endpoint ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: networks id; Type: DEFAULT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.networks ALTER COLUMN id SET DEFAULT nextval('public.networks_id_seq'::regclass);


--
-- Name: permissions id; Type: DEFAULT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.permissions ALTER COLUMN id SET DEFAULT nextval('public.permissions_id_seq'::regclass);


--
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.rols_id_seq'::regclass);


--
-- Name: rules id; Type: DEFAULT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.rules ALTER COLUMN id SET DEFAULT nextval('public.rules_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq1'::regclass);


--
-- Data for Name: custom_context_words; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.custom_context_words (id, entity_type_id, word) FROM stdin;
1	3	telefono
3	3	movil
7	3	número
5	3	teléfono
4	3	numero
2	3	celular
9	3	mobile
6	3	móvil
8	3	phone
10	4	social security
11	4	social security number
12	4	ssn
13	4	ss#
14	4	ssn#
15	4	social security #
16	4	socialsecurity
\.


--
-- Data for Name: custom_deny_list; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.custom_deny_list (id, entity_type_id, value) FROM stdin;
3	2	DENY_VALUE_3
\.


--
-- Data for Name: custom_entity_types; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.custom_entity_types (id, name, description, detection_type, created_at, created_by, updated_at, updated_by) FROM stdin;
2	ENTITY_DNI	Identificar Documento Nacional de Identidad	Pattern	2024-05-08 05:51:30.886296	\N	2024-05-08 05:53:05.298662	\N
3	Peru_Phone_Number	Custom entity for detecting Peru phone numbers	Pattern	2024-06-01 02:49:37.245984	\N	2024-06-01 02:49:37.245984	\N
4	USSocialSecurityNumber	Custom entity for detecting U.S. Social Security Numbers (SSNs)	Dictionary	2024-06-01 03:15:11.999045	\N	2024-06-01 03:15:11.999045	\N
\.


--
-- Data for Name: custom_patterns; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.custom_patterns (id, entity_type_id, name, regex, score) FROM stdin;
3	2	PATTERN_3	\\b[0-9]{8}\\b	1
4	3	PeruPhoneNumberPattern	\\b(?:\\+51|0051)?(?:1|9\\d)\\d{8}\\b	0.9
5	4	SSNPattern1	\\b(?!000|666|9\\d{2})\\d{3}-(?!00)\\d{2}-(?!0000)\\d{4}\\b	1
6	4	SSNPattern2	\\b(?!000|666|9\\d{2})\\d{3}(?!00)\\d{2}(?!0000)\\d{4}\\b	1
\.


--
-- Data for Name: default_entity_types; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.default_entity_types (name, description) FROM stdin;
CREDIT_CARD	Número de tarjeta de crédito
CRYPTO	Número de monedero electrónico
DATE_TIME	Fechas u horas absolutas o relativas, o períodos menores a un día
EMAIL_ADDRESS	Una dirección de correo electrónico
IBAN_CODE	El Número Internacional de Cuenta Bancaria
IP_ADDRESS	Una dirección de Protocolo de Internet (IP)
NRP	Nacionalidad, religión o grupo político de una persona
LOCATION	Nombre de una ubicación definida política o geográficamente
PERSON	Nombre completo de una persona
PHONE_NUMBER	Un número de teléfono
\.


--
-- Data for Name: file_metadata; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.file_metadata (id, key, value, filetype, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: groups_rules; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.groups_rules (rule_id, network_id) FROM stdin;
3	2
\.


--
-- Data for Name: history; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.history (id, code, originated_at, origin, destination, sensitive_data, results, level, action, text, text_redacted, file, metadata) FROM stdin;
4	cbef17a4-c858-4d3a-93cd-fd7ed365a4b0	2024-06-01 06:41:23.3552	127.0.0.1	127.0.0.1	{'87654321': 'ENTITY_DNI'}	[{"rule": {"id": 1, "level": "Medium", "action": "Redact", "codigo": "c638d551-4b51-4589-a5c1-cc110068f135", "entity": "ENTITY_DNI", "hits_lower": 1, "hits_upper": 10, "description": "Alert of potential users data leak", "confidence_level": 0.7}, "matches": [{"end": 214, "data": "87654321", "score": 1.0, "start": 206, "entity_type": "ENTITY_DNI", "analysis_explanation": null, "recognition_metadata": {"recognizer_name": "PatternRecognizer", "recognizer_identifier": "PatternRecognizer_140530772171488"}}]}]	Medium	Redact	Estimado Sr. Rodríguez, le escribo para confirmar nuestra cita de negocios. Me puede contactar a mi celular 987 654 321. También necesito que me envíe una copia de su DNI para nuestros registros. El mío es 87654321. Quedo atento a su respuesta.\n	Estimado Sr. Rodríguez, le escribo para confirmar nuestra cita de negocios. Me puede contactar a mi celular 987 654 321. También necesito que me envíe una copia de su DNI para nuestros registros. El mío es ENTITY_DNI. Quedo atento a su respuesta.\n	f	\N
5	0d072170-ff1d-4112-b176-3e907eb96a12	2024-06-01 06:42:18.49305	127.0.0.1	127.0.0.1	{'87654321': 'ENTITY_DNI'}	[{"rule": {"id": 1, "level": "Medium", "action": "Redact", "codigo": "c638d551-4b51-4589-a5c1-cc110068f135", "entity": "ENTITY_DNI", "hits_lower": 1, "hits_upper": 10, "description": "Alert of potential users data leak", "confidence_level": 0.7}, "matches": [{"end": 214, "data": "87654321", "score": 1.0, "start": 206, "entity_type": "ENTITY_DNI", "analysis_explanation": null, "recognition_metadata": {"recognizer_name": "PatternRecognizer", "recognizer_identifier": "PatternRecognizer_140302450023136"}}]}]	Medium	Redact	Estimado Sr. Rodríguez, le escribo para confirmar nuestra cita de negocios. Me puede contactar a mi celular 987 654 321. También necesito que me envíe una copia de su DNI para nuestros registros. El mío es 87654321. Quedo atento a su respuesta\n	Estimado Sr. Rodríguez, le escribo para confirmar nuestra cita de negocios. Me puede contactar a mi celular 987 654 321. También necesito que me envíe una copia de su DNI para nuestros registros. El mío es ENTITY_DNI. Quedo atento a su respuesta\n	f	\N
6	160efabd-ef00-41f2-8a0f-12bd4367aca9	2024-06-01 06:42:29.40195	127.0.0.1	127.0.0.1	{'24681012': 'ENTITY_DNI'}	[{"rule": {"id": 1, "level": "Medium", "action": "Redact", "codigo": "c638d551-4b51-4589-a5c1-cc110068f135", "entity": "ENTITY_DNI", "hits_lower": 1, "hits_upper": 10, "description": "Alert of potential users data leak", "confidence_level": 0.7}, "matches": [{"end": 217, "data": "24681012", "score": 1.0, "start": 209, "entity_type": "ENTITY_DNI", "analysis_explanation": null, "recognition_metadata": {"recognizer_name": "PatternRecognizer", "recognizer_identifier": "PatternRecognizer_139867999836896"}}]}]	Medium	Redact	¡Hola amigos! Estoy organizando una reunión este fin de semana. Si desean asistir, por favor envíenme un mensaje al 951 753 852 con su nombre y número de DNI para poder hacer una lista de invitados. Mi DNI es 24681012. ¡Nos vemos pronto!\n	¡Hola amigos! Estoy organizando una reunión este fin de semana. Si desean asistir, por favor envíenme un mensaje al 951 753 852 con su nombre y número de DNI para poder hacer una lista de invitados. Mi DNI es ENTITY_DNI. ¡Nos vemos pronto!\n	f	\N
\.


--
-- Data for Name: network_endpoint; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.network_endpoint (id, name, email, network_id, ip, status, created_at) FROM stdin;
\.


--
-- Data for Name: networks; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.networks (id, subnet, name, description, created_at, created_by, updated_at, updated_by) FROM stdin;
2	192.168.1.0/24	OfficeNetwork	Isolated network for office devices	2024-06-01 03:39:02.553689	\N	2024-06-01 03:39:02.553689	\N
3	192.168.2.0/24	Management Network	Network for workers	2024-06-01 03:56:19.61394	\N	2024-06-01 03:56:19.61394	\N
\.


--
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.permissions (id, permission) FROM stdin;
1	Context-Viewer
2	Dashboard-Viewer
3	Event-Viewer
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.roles (id, role, created_at, created_by, updated_at, updated_by) FROM stdin;
11	admin	2024-05-31 09:33:03.712	0	2024-05-31 09:33:03.712	0
13	Low level	2024-06-01 03:20:10.268291	\N	2024-06-01 03:20:10.268291	\N
\.


--
-- Data for Name: roles_permissions; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.roles_permissions (role_id, permission_id) FROM stdin;
11	2
11	3
11	1
13	2
\.


--
-- Data for Name: rules; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.rules (id, codigo, name, entity_id, action, level, description, status, confidence_level, hits_lower, hits_upper, alerts, created_at, created_by, updated_at, updated_by) FROM stdin;
1	c638d551-4b51-4589-a5c1-cc110068f135	DNI Leak Prevention	2	Redact	Medium	Alert of potential users data leak	t	0.7	1	10	t	2024-05-29 08:45:02.24132	\N	2024-05-29 08:45:02.24132	\N
3	RU-0003	Identify possible leaks of phone numbers	3	Alert	High	string	t	0.8	1	100	t	2024-06-01 04:27:53.872186	\N	2024-06-01 04:27:53.872186	\N
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: oliver
--

COPY public.users (id, first_name, last_name, email, password, role_id, created_at, created_by, updated_at, updated_by) FROM stdin;
3	Oliver	Bustamante	oliver@pucp.edu.pe	$2a$10$HQQwLIzSm7QgHfr60703a.hxJ1GyLPaL1W5PvPoT0KxWnUosQwyr2	11	2024-06-01 02:37:02.273052	\N	2024-06-01 02:37:02.273052	\N
4	Angel	Bravo	Angel@pucp.edu.pe	$2a$10$O22VyekxpfvZazkXozB3r.x.KLyL6Kg7iKTXoPUZUTWFIyeE7Pwme	11	2024-06-01 02:38:28.325497	\N	2024-06-01 02:38:28.325497	\N
\.


--
-- Name: custom_context_words_id_seq; Type: SEQUENCE SET; Schema: public; Owner: oliver
--

SELECT pg_catalog.setval('public.custom_context_words_id_seq', 16, true);


--
-- Name: custom_deny_list_id_seq; Type: SEQUENCE SET; Schema: public; Owner: oliver
--

SELECT pg_catalog.setval('public.custom_deny_list_id_seq', 3, true);


--
-- Name: custom_entity_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: oliver
--

SELECT pg_catalog.setval('public.custom_entity_types_id_seq', 4, true);


--
-- Name: custom_patterns_id_seq; Type: SEQUENCE SET; Schema: public; Owner: oliver
--

SELECT pg_catalog.setval('public.custom_patterns_id_seq', 6, true);


--
-- Name: file_metadata_id_seq; Type: SEQUENCE SET; Schema: public; Owner: oliver
--

SELECT pg_catalog.setval('public.file_metadata_id_seq', 1, false);


--
-- Name: history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: oliver
--

SELECT pg_catalog.setval('public.history_id_seq', 6, true);


--
-- Name: networks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: oliver
--

SELECT pg_catalog.setval('public.networks_id_seq', 3, true);


--
-- Name: permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: oliver
--

SELECT pg_catalog.setval('public.permissions_id_seq', 3, true);


--
-- Name: rols_id_seq; Type: SEQUENCE SET; Schema: public; Owner: oliver
--

SELECT pg_catalog.setval('public.rols_id_seq', 13, true);


--
-- Name: rules_id_seq; Type: SEQUENCE SET; Schema: public; Owner: oliver
--

SELECT pg_catalog.setval('public.rules_id_seq', 3, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: oliver
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


--
-- Name: users_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: oliver
--

SELECT pg_catalog.setval('public.users_id_seq1', 4, true);


--
-- Name: custom_context_words custom_context_words_entity_type_id_word_key; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_context_words
    ADD CONSTRAINT custom_context_words_entity_type_id_word_key UNIQUE (entity_type_id, word);


--
-- Name: custom_context_words custom_context_words_pkey; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_context_words
    ADD CONSTRAINT custom_context_words_pkey PRIMARY KEY (id);


--
-- Name: custom_deny_list custom_deny_list_entity_type_id_value_key; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_deny_list
    ADD CONSTRAINT custom_deny_list_entity_type_id_value_key UNIQUE (entity_type_id, value);


--
-- Name: custom_deny_list custom_deny_list_pkey; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_deny_list
    ADD CONSTRAINT custom_deny_list_pkey PRIMARY KEY (id);


--
-- Name: custom_entity_types custom_entity_types_name_key; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_entity_types
    ADD CONSTRAINT custom_entity_types_name_key UNIQUE (name);


--
-- Name: custom_entity_types custom_entity_types_pkey; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_entity_types
    ADD CONSTRAINT custom_entity_types_pkey PRIMARY KEY (id);


--
-- Name: custom_patterns custom_patterns_entity_type_id_name_key; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_patterns
    ADD CONSTRAINT custom_patterns_entity_type_id_name_key UNIQUE (entity_type_id, name);


--
-- Name: custom_patterns custom_patterns_pkey; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_patterns
    ADD CONSTRAINT custom_patterns_pkey PRIMARY KEY (id);


--
-- Name: default_entity_types default_entity_types_pkey; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.default_entity_types
    ADD CONSTRAINT default_entity_types_pkey PRIMARY KEY (name);


--
-- Name: file_metadata file_metadata_pk; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.file_metadata
    ADD CONSTRAINT file_metadata_pk PRIMARY KEY (id);


--
-- Name: groups_rules groups_rules_pk; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.groups_rules
    ADD CONSTRAINT groups_rules_pk PRIMARY KEY (rule_id, network_id);


--
-- Name: history history_pk; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.history
    ADD CONSTRAINT history_pk PRIMARY KEY (id);


--
-- Name: network_endpoint network_endpoint_pk; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.network_endpoint
    ADD CONSTRAINT network_endpoint_pk PRIMARY KEY (id);


--
-- Name: networks networks_pk; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.networks
    ADD CONSTRAINT networks_pk PRIMARY KEY (id);


--
-- Name: permissions permissions_pk; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT permissions_pk PRIMARY KEY (id);


--
-- Name: roles_permissions roles_permissions_pk; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.roles_permissions
    ADD CONSTRAINT roles_permissions_pk PRIMARY KEY (role_id, permission_id);


--
-- Name: roles roles_pk; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pk PRIMARY KEY (id);


--
-- Name: rules rules_pk; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.rules
    ADD CONSTRAINT rules_pk PRIMARY KEY (id);


--
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- Name: custom_context_words custom_context_words_entity_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_context_words
    ADD CONSTRAINT custom_context_words_entity_type_id_fkey FOREIGN KEY (entity_type_id) REFERENCES public.custom_entity_types(id) ON DELETE CASCADE;


--
-- Name: custom_deny_list custom_deny_list_entity_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_deny_list
    ADD CONSTRAINT custom_deny_list_entity_type_id_fkey FOREIGN KEY (entity_type_id) REFERENCES public.custom_entity_types(id) ON DELETE CASCADE;


--
-- Name: custom_patterns custom_patterns_entity_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.custom_patterns
    ADD CONSTRAINT custom_patterns_entity_type_id_fkey FOREIGN KEY (entity_type_id) REFERENCES public.custom_entity_types(id) ON DELETE CASCADE;


--
-- Name: groups_rules groups_rules_networks_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.groups_rules
    ADD CONSTRAINT groups_rules_networks_id_fk FOREIGN KEY (network_id) REFERENCES public.networks(id) ON DELETE CASCADE;


--
-- Name: groups_rules groups_rules_rules_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.groups_rules
    ADD CONSTRAINT groups_rules_rules_id_fk FOREIGN KEY (rule_id) REFERENCES public.rules(id) ON DELETE CASCADE;


--
-- Name: network_endpoint network_endpoint_networks_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.network_endpoint
    ADD CONSTRAINT network_endpoint_networks_id_fk FOREIGN KEY (network_id) REFERENCES public.networks(id);


--
-- Name: roles_permissions roles_permissions_permissions_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.roles_permissions
    ADD CONSTRAINT roles_permissions_permissions_id_fk FOREIGN KEY (permission_id) REFERENCES public.permissions(id) ON DELETE CASCADE;


--
-- Name: roles_permissions roles_permissions_roles_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.roles_permissions
    ADD CONSTRAINT roles_permissions_roles_id_fk FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE;


--
-- Name: rules rules_custom_entity_types_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.rules
    ADD CONSTRAINT rules_custom_entity_types_id_fk FOREIGN KEY (entity_id) REFERENCES public.custom_entity_types(id);


--
-- Name: users users_roles_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: oliver
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_roles_id_fk FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- PostgreSQL database dump complete
--

