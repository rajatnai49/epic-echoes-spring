import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from scipy.sparse import csr_matrix
from sklearn.neighbors import NearestNeighbors

interactions = pd.read_csv('temp/interactions.csv')
preferences = pd.read_csv('temp/preferences.csv')

# Encode categorical variables
label_encoder_user = LabelEncoder()
label_encoder_storybook = LabelEncoder()

interactions['user_id'] = label_encoder_user.fit_transform(interactions['user_id'])
interactions['storybook_id'] = label_encoder_storybook.fit_transform(interactions['storybook_id'])

# Create a pivot table for collaborative filtering
interaction_matrix = interactions.pivot_table(index='user_id', columns='storybook_id', values='interaction_type', aggfunc='count').fillna(0)

# Convert pivot table to sparse matrix
interaction_sparse_matrix = csr_matrix(interaction_matrix.values)

train_data, test_data = train_test_split(interaction_matrix, test_size=0.2, random_state=42)

model = NearestNeighbors(metric='cosine', algorithm='brute')
model.fit(train_data)

def recommend_storybooks(user_id, n_recommendations=5):
    if user_id not in interactions['user_id'].values:
        return []

    user_index = label_encoder_user.transform([user_id])[0]
    distances, indices = model.kneighbors([interaction_matrix.iloc[user_index].values], n_neighbors=n_recommendations + 1)

    print(f"User ID: {user_id}, User Index: {user_index}")
    print(f"Distances: {distances}")
    print(f"Indices: {indices}")

    recommendations = []
    for i in range(1, len(distances.flatten())):
        recommendations.append(interaction_matrix.columns[indices.flatten()[i]])

    recommended_storybooks = list(label_encoder_storybook.inverse_transform(recommendations))
    print(f"Recommended Storybooks: {recommended_storybooks}")

    return recommended_storybooks

def evaluate_model(test_data):
    test_users = test_data.index.tolist()
    all_true_positives = 0
    all_false_positives = 0
    all_false_negatives = 0

    for user_index in test_users:
        try:
            actual_storybooks = set(test_data.columns[test_data.loc[user_index] > 0].tolist())
        except IndexError as e:
            print(f'Error accessing test_data for user_index {user_index}: {e}')
            continue

        user_id = label_encoder_user.inverse_transform([user_index])[0]
        recommended_storybooks = set(recommend_storybooks(user_id, n_recommendations=5))

        print(f'User ID: {user_id}')
        print(f'Actual Storybooks: {actual_storybooks}')
        print(f'Recommended Storybooks: {recommended_storybooks}')

        true_positives = len(actual_storybooks.intersection(recommended_storybooks))
        false_positives = len(recommended_storybooks.difference(actual_storybooks))
        false_negatives = len(actual_storybooks.difference(recommended_storybooks))

        print(f'True Positives: {true_positives}, False Positives: {false_positives}, False Negatives: {false_negatives}')

        all_true_positives += true_positives
        all_false_positives += false_positives
        all_false_negatives += false_negatives

    precision = all_true_positives / (all_true_positives + all_false_positives) if (all_true_positives + all_false_positives) > 0 else 0
    recall = all_true_positives / (all_true_positives + all_false_negatives) if (all_true_positives + all_false_negatives) > 0 else 0
    f1 = 2 * (precision * recall) / (precision + recall) if (precision + recall) > 0 else 0

    print(f'Precision: {precision:.2f}')
    print(f'Recall: {recall:.2f}')
    print(f'F1 Score: {f1:.2f}')

example_user_id = label_encoder_user.inverse_transform([0])[0]
recommended_storybooks = recommend_storybooks(example_user_id, n_recommendations=5)
print(f'Recommended storybooks for user {example_user_id}: {recommended_storybooks}')

evaluate_model(test_data)

