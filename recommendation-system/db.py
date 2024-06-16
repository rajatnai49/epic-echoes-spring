import psycopg2

DB_NAME = "epic_echoes_dev_temp_2"
DB_USER = "postgres"
DB_PASSWORD = "postgres"
DB_HOST = "localhost"
DB_PORT = "5432"

conn = psycopg2.connect(
    dbname=DB_NAME,
    user=DB_USER,
    password=DB_PASSWORD,
    host=DB_HOST,
    port=DB_PORT
)

def fetch_data(query):
    with conn.cursor() as cursor:
        cursor.execute(query)
        return cursor.fetchall()

