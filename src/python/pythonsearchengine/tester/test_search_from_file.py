from src.python.pythonsearchengine.core.search_from_file import *

def main():

    csv_file = input()
    query = input('Enter the query here: ')

    list_titles = search_from_file(csv_file, query)
    # print(list_titles)

    with open('answer.txt', 'w', encoding='utf-8') as f:
        for line in list_titles:
            f.write(line + "\n")
if __name__ == "__main__":
    main()