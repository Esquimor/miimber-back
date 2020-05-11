create table users (
  id bigserial Not Null Primary key,
  email Varchar(60) Not null,
  password Varchar(255),
  first_name Varchar(40) not null,
  last_name Varchar(40) not null,
  status Varchar(2) not null,
  lang Varchar(3) not null,
  new_email Varchar(60),
  token_creation Varchar(255),
  token_password_forgotten Varchar(255),
  token_change_email Varchar(255)
);

create table organizations (
  id bigserial Not null Primary key,
  name Varchar(100) not null,
  stripe Varchar(50) not null,
  stripe_end timestamp without time zone,
  state Varchar(2)
);

create table members (
  id bigserial Not null Primary key,
  role Varchar(3),
  organization_id bigint REFERENCES organizations (id),
  user_id bigint REFERENCES users (id)
);

create table template_sessions (
  id bigserial not null Primary key
);

create table type_sessions (
  id bigserial not null Primary key,
  name Varchar(100) not null,
  organization_id bigint REFERENCES organizations (id)
);

create table sessions (
  id bigserial not null primary key,
  description text,
  end_date timestamp with time zone not null,
  limit_users int not null,
  start_date timestamp with time zone not null,
  title Varchar(255) not null,
  organization_id bigint REFERENCES organizations (id),
  template_session_id bigint REFERENCES template_sessions (id),
  type_session_id bigint REFERENCES type_sessions (id)
);

create table comments (
  id bigserial not null primary key,
  comment text,
  date_comment timestamp without time zone,
  comment_parent_id bigint,
  session_id bigint REFERENCES sessions (id),
  user_id bigint REFERENCES users (id)
);

create table attendees (
  id bigserial not null primary key,
  date_check timestamp without time zone,
  session_id bigint REFERENCES sessions (id),
  user_id bigint REFERENCES users (id)
);

create table registered (
  id bigserial not null primary key,
  date_registered timestamp without time zone,
  session_id bigint REFERENCES sessions (id),
  user_id bigint REFERENCES users (id)
);

create table categories_forum (
  id bigserial not null primary key,
  position int not null,
  title Varchar(255) not null,
  organization_id bigint REFERENCES organizations (id)
);

create table subjects_forum (
  id bigserial not null primary key,
  position int not null,
  title Varchar(255) not null,
  category_id bigint REFERENCES categories_forum (id)
);

create table talks_forum (
  id bigserial not null primary key,
  subject_id bigint REFERENCES subjects_forum (id),
  user_id bigint REFERENCES users (id),
  title Varchar(255) not null,
  date_talk timestamp without time zone
);

create table messages_forum (
  id bigserial not null primary key,
  date_message timestamp without time zone,
  message text,
  talk_id bigint REFERENCES talks_forum (id),
  user_id bigint REFERENCES users (id)
);