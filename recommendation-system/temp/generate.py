import csv
import uuid
import random
from faker import Faker

fake = Faker()

NUM_USERS = 10000
NUM_STORYBOOKS = 5000
NUM_RATINGS = 50000
NUM_GENRES = 20
NUM_PREFERENCES = 20000

users = [(str(uuid.uuid4()), fake.user_name()) for _ in range(NUM_USERS)]

storybooks = [(str(uuid.uuid4()), fake.catch_phrase(), fake.text(max_nb_chars=200), random.randint(5, 50), random.randint(50, 1000)) for _ in range(NUM_STORYBOOKS)]

ratings = [(str(uuid.uuid4()), random.choice(users)[0], random.choice(storybooks)[0], random.randint(1, 5)) for _ in range(NUM_RATINGS)]

genres = [(str(uuid.uuid4()), fake.word()) for _ in range(NUM_GENRES)]

preferences = [(str(uuid.uuid4()), random.choice(users)[0], random.choice(genres)[0]) for _ in range(NUM_PREFERENCES)]
def write_to_csv(filename, data, header):
    with open(filename, mode='w', newline='') as file:
        writer = csv.writer(file)
        writer.writerow(header)
        writer.writerows(data)

write_to_csv('users.csv', users, ['id', 'username'])
write_to_csv('storybooks.csv', storybooks, ['id', 'name', 'plot', 'min_chapter_length', 'max_chapter_length'])
write_to_csv('ratings.csv', ratings, ['id', 'user_id', 'storybook_id', 'rating'])
write_to_csv('genres.csv', genres, ['id', 'name'])
write_to_csv('preferences.csv', preferences, ['id', 'user_id', 'genre_id'])

print("CSV files generated successfully!")

