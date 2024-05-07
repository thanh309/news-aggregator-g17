from src.python.pythonsearchengine.filter.search_by_tags import search_by_tags

def main():

    csv_file = input()

    list_titles = search_by_tags(csv_file)
    print(list_titles)

if __name__ == "__main__":
    main()