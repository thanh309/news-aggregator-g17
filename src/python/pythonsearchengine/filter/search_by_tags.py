from src.python.pythonsearchengine.core.search_from_file import search_from_file

def search_by_tags(csv_file):

    query = input('Enter the tags here: ')
    title_list = search_from_file(csv_file, query)

    return title_list[:20]
    