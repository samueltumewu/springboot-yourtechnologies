drop table if exists user_information;
drop table if exists form_questions;
drop table if exists forms;

create table user_information  (
	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);


create table forms  (
	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT null UNIQUE,
    description VARCHAR(255),
    allow_domain VARCHAR(255),
    limit_one_response INT2,
    creator_id BIGINT
);

create table form_questions  (
	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    choice_type VARCHAR(255) NOT NULL,
    choices VARCHAR(255),
    is_required INT2,
    form_id BIGINT,
    foreign key (form_id) references forms(id)
);

create table responses  (
	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    form_id BIGINT,
    user_id BIGINT,
    date TIMESTAMP,
    foreign key (form_id) references forms(id),
    foreign key (user_id) references user_information(id)
);
create table answers  (
	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    response_id BIGINT,
    question_id BIGINT,
    value VARCHAR(255),
    foreign key (response_id) references responses(id),
    foreign key (question_id) references form_questions(id)
);