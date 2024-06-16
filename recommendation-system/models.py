class UserInfo:
    def __init__(self, id, username, roles, storybooks, chapters, storybook_permissions, ratings):
        self.id = id
        self.username = username
        self.roles = roles
        self.storybooks = storybooks
        self.chapters = chapters
        self.storybook_permissions = storybook_permissions
        self.ratings = ratings

class UserInteraction:
    def __init__(self, id, user, storybook, interaction_time, interaction_type):
        self.id = id
        self.user = user
        self.storybook = storybook
        self.interaction_time = interaction_time
        self.interaction_type = interaction_type

class Storybook:
    def __init__(self, id, name, plot, max_chapter_length, min_chapter_length, chapters, user, privacy, permissions, genres, ratings):
        self.id = id
        self.name = name
        self.plot = plot
        self.max_chapter_length = max_chapter_length
        self.min_chapter_length = min_chapter_length
        self.chapters = chapters
        self.user = user
        self.privacy = privacy
        self.permissions = permissions
        self.genres = genres
        self.ratings = ratings

class UserPreference:
    def __init__(self, id, user, genre):
        self.id = id
        self.user = user
        self.genre = genre

class StorybookRating:
    def __init__(self, id, user, storybook, rating):
        self.id = id
        self.user = user
        self.storybook = storybook
        self.rating = rating

class StorybookUserPermission:
    def __init__(self, id, storybook, user, role):
        self.id = id
        self.storybook = storybook
        self.user = user
        self.role = role

class Genre:
    def __init__(self, id, name):
        self.id = id
        self.name = name

class Chapter:
    def __init__(self, id, title, upVotes, downVotes, chapterNumber, chapterContent, storybook, user):
        self.id = id
        self.title = title
        self.upVotes = upVotes
        self.downVotes = downVotes
        self.chapterNumber = chapterNumber
        self.chapterContent = chapterContent
        self.storybook = storybook
        self.user = user

