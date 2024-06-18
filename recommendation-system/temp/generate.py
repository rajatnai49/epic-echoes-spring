import pandas as pd
import numpy as np
import uuid
from faker import Faker

fake = Faker()

# # Parameters
# num_users = 1000
# num_storybooks = 500
# num_genres = 20
# num_interactions = 5000
# num_preferences = 2000
# num_votes = 3000

# # Generate random users
# users = pd.DataFrame({
#     'user_id': [str(uuid.uuid4()) for _ in range(num_users)],
#     'username': [fake.user_name() for _ in range(num_users)],
#     'password': [fake.password() for _ in range(num_users)]
# })

# # Generate random storybooks
# storybooks = pd.DataFrame({
#     'storybook_id': [str(uuid.uuid4()) for _ in range(num_storybooks)],
#     'name': [fake.sentence(nb_words=4) for _ in range(num_storybooks)],
#     'plot': [fake.text(max_nb_chars=200) for _ in range(num_storybooks)],
#     'maxChapterLength': np.random.randint(1000, 10000, num_storybooks),
#     'minChapterLength': np.random.randint(100, 1000, num_storybooks),
#     'privacy': np.random.choice(['EVERYONE_VIEW', 'PERMITTED_USERS_VIEW', 'EVERYONE_EDIT', 'PERMITTED_USERS_EDIT'], num_storybooks)
# })

# # Generate random genres
# genres = pd.DataFrame({
#     'genre_id': [str(uuid.uuid4()) for _ in range(num_genres)],
#     'name': [fake.word() for _ in range(num_genres)]
# })

# # Generate random interactions
# interactions = pd.DataFrame({
#     'interaction_id': [str(uuid.uuid4()) for _ in range(num_interactions)],
#     'user_id': np.random.choice(users['user_id'], num_interactions),
#     'storybook_id': np.random.choice(storybooks['storybook_id'], num_interactions),
#     'interaction_type': np.random.choice(['VIEW', 'LIKE', 'COMMENT', 'SHARE', 'RATE', 'BOOKMARK', 'DOWNLOAD', 'START_READING', 'FINISH_READING', 'FOLLOW'], num_interactions),
#     'interaction_time': [fake.date_time_this_year() for _ in range(num_interactions)]
# })

# # Generate random preferences
# preferences = pd.DataFrame({
#     'preference_id': [str(uuid.uuid4()) for _ in range(num_preferences)],
#     'user_id': np.random.choice(users['user_id'], num_preferences),
#     'genre_id': np.random.choice(genres['genre_id'], num_preferences)
# })

# # Generate random votes
# votes = pd.DataFrame({
#     'vote_id': [str(uuid.uuid4()) for _ in range(num_votes)],
#     'chapter_id': np.random.choice(storybooks['storybook_id'], num_votes),  # Assuming chapters belong to storybooks
#     'user_id': np.random.choice(users['user_id'], num_votes),
#     'vote_type': np.random.choice(['UPVOTE', 'DOWNVOTE'], num_votes)
# })

# # Save data to CSV
# users.to_csv('users.csv', index=False)
# storybooks.to_csv('storybooks.csv', index=False)
# genres.to_csv('genres.csv', index=False)
# interactions.to_csv('interactions.csv', index=False)
# preferences.to_csv('preferences.csv', index=False)
# votes.to_csv('votes.csv', index=False)

import pandas as pd
import random
from faker import Faker

fake = Faker()

# Parameters for data generation
num_users = 100
num_items = 50
num_interactions = 500
ratings = [1, 2, 3, 4, 5]

# Generate user IDs
user_ids = [f'user{n}' for n in range(1, num_users + 1)]

# Generate item IDs
item_ids = [f'item{n}' for n in range(1, num_items + 1)]

# Generate interactions
interactions_data = {
    'user_id': [random.choice(user_ids) for _ in range(num_interactions)],
    'item_id': [random.choice(item_ids) for _ in range(num_interactions)],
    'rating': [random.choice(ratings) for _ in range(num_interactions)]
}

interactions = pd.DataFrame(interactions_data)

# Generate item features
genres = ['Fantasy', 'Mystery', 'Science Fiction', 'Romance', 'Horror', 'Thriller']
authors = [fake.name() for _ in range(20)]
keywords = ['magic, adventure', 'detective, suspense', 'space, aliens', 'love, drama', 'ghosts, fear', 'crime, excitement']

item_features_data = {
    'item_id': item_ids,
    'genre': [random.choice(genres) for _ in range(num_items)],
    'author': [random.choice(authors) for _ in range(num_items)],
    'keywords': [random.choice(keywords) for _ in range(num_items)]
}

item_features = pd.DataFrame(item_features_data)

# Save the DataFrames to CSV files
interactions.to_csv('interactions.csv', index=False)
item_features.to_csv('item_features.csv', index=False)

print("CSV files have been generated.")

