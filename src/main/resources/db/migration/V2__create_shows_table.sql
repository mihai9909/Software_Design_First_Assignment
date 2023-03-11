CREATE TABLE public.shows (
   id SERIAL PRIMARY KEY,
   title VARCHAR(255) NOT NULL,
   genre VARCHAR(255) NOT NULL,
   date_time TIMESTAMP NOT NULL,
   max_tickets INTEGER NOT NULL
);
