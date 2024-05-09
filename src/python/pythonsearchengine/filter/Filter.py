from src.python.pythonsearchengine.filter.search_by_author import search_by_author
from src.python.pythonsearchengine.filter.search_by_tags import search_by_tags
from src.python.pythonsearchengine.filter.search_by_title import search_by_title


def filter(check_list, csv_file):
    # in the check_list, we have 3 keys: AUTHOR, TITLE, TAGS
    # check that whether AUTHOR filter is tick or not.
    filter_author_id = []
    if check_list['author']:
        filter_author_id = search_by_author(csv_file)

    # check that whether TITLE filter is tick or not.
    filter_title_id = []
    if check_list['title']:
        filter_title_id = search_by_title(csv_file)

    # check that whether TAGS filter is tick or not.
    filter_tags_id = []
    if check_list['tags']:
        filter_tags_id = search_by_tags(csv_file)

    return (filter_author_id, filter_title_id, filter_tags_id)

if __name__ == "__main__":
    csv_file = input()
    print(*filter({'author' : True, 'title' : False, 'tags': True}, csv_file), sep="\n")
