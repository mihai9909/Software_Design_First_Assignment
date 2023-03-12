CREATE TABLE public.tickets (
     id SERIAL PRIMARY KEY,
     show_id INTEGER REFERENCES shows (id),
     unit_price DECIMAL(10, 2) NOT NULL,
     places INTEGER NOT NULL
);