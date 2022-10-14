CREATE TABLE reservation(
                id IDENTITY NOT NULL,
                date DATE,
                time TIME,
                name VARCHAR(20),
                PRIMARY KEY (id)
                );

CREATE TABLE theme(
                            id IDENTITY NOT NULL,
                            name VARCHAR(100),
                            desc VARCHAR(500),
                            price INTEGER,
                            PRIMARY KEY (id)
);
