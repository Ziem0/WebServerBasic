create table if not exists entries(
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	NAME TXT NOT NULL,
	MESSAGE TXT NOT NULL,
	DATE TXT NOT NULL
);
