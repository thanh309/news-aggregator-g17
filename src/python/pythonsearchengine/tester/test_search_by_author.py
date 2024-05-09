from src.python.pythonsearchengine.filter.search_by_author import search_by_author

def main():

    csv_file = input()

    list_titles = search_by_author(csv_file)
    # print(list_titles)

    with open('answer.txt', 'w', encoding='utf-8') as f:
        for line in list_titles:
            f.write(line + "\n")
if __name__ == "__main__":
    main()