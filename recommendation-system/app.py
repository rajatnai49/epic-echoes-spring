import uuid
from db import fetch_data
from models import UserInfo, Storybook, StorybookRating, Genre, UserPreference
import queries

users_data = fetch_data(queries.user_query)
storybooks_data = fetch_data(queries.storybook_query)
ratings_data = fetch_data(queries.rating_query)
genres_data = fetch_data(queries.genre_query)
preferences_data = fetch_data(queries.preference_query)

print(users_data)
print(storybooks_data)
print(ratings_data)
print(genres_data)
print(preferences_data)

users = [UserInfo(uuid.UUID(user[0]), user[1], set(), [], [], [], []) for user in users_data]
storybooks = [Storybook(uuid.UUID(sb[0]), sb[1], sb[2], sb[3], sb[4], [], None, None, [], [], []) for sb in storybooks_data]
ratings = [StorybookRating(uuid.UUID(rt[0]), next(user for user in users if user.id == rt[1]), next(sb for sb in storybooks if sb.id == rt[2]), rt[3]) for rt in ratings_data]
genres = [Genre(uuid.UUID(gr[0]), gr[1]) for gr in genres_data]
preferences = [UserPreference(uuid.UUID(pr[0]), next(user for user in users if user.id == pr[1]), next(gr for gr in genres if gr.id == pr[2])) for pr in preferences_data]

for rating in ratings:
    rating.user.ratings.append(rating)
    rating.storybook.ratings.append(rating)

print(users)
print(storybooks)
print(ratings)
print(genres)
print(preferences)

