from src.python.pythonsearchengine.core.auto_complete import *
from src.python.pythonsearchengine.core.file_handling import remove_puncts
import string
import re
import pandas as pd

def create_words_dict(csv_file):

    df = pd.read_csv(csv_file)

    # first we need to create the word dicts from csv file and then create dict of each column
    with open(csv_file, 'r') as csvf:
        # header = csvf.readline().split(',')
        words_list = re.split(r'[,| \n]', csvf.read())

    words_dict = create_a_words_dict([remove_puncts(word, string) for word in words_list])


    # header = [re.sub(r'[\"\'\n]', '', word) for word in header]


    # create dictionary of each column, useful when searching by each column
    # "AUTHOR","CATEGORY","CONTENT","CREATIONDATE","LINK","SUMMARY","TAGS","TITLE","TYPE","WEBSITESOURCE"

    authors_dict = create_a_words_dict(list(df['AUTHOR']))
    categories_dict = create_a_words_dict(list(df['CATEGORY']))
    contents_dict = create_a_words_dict(list(df["CONTENT"]))
    creation_date_dict = create_a_words_dict(list(df["CREATIONDATE"]))
    links_dict = create_a_words_dict(list(df["LINK"]))
    summary_dict = create_a_words_dict(list(df["SUMMARY"]))
    tags_dict = create_a_words_dict(list(df["TAGS"]))
    titles_dict = create_a_words_dict(list(df["TITLE"]))
    types_dict = create_a_words_dict(list(df["TYPE"]))
    websitesources_dict = create_a_words_dict(list(df["WEBSITESOURCE"]))

    return (words_dict, authors_dict, categories_dict, contents_dict, creation_date_dict, links_dict, summary_dict, tags_dict, titles_dict, types_dict, websitesources_dict)

def create_a_words_dict(words_list):

    words_dict = {}
    for word in words_list:
        if word not in words_dict:
            words_dict[word] = 1
        words_dict[word] += 1

    return words_dict



def main():
    csv_file = input()
    lst = create_words_dict(csv_file)

    authors_dict = lst[1]

    query = input()

    t = AutoComplete(authors_dict)

    t.createTrie()

    comp = t.get_suggestion(query)

    if comp[1] == -1:
        print("No other strings found with this prefix")

    elif comp[1] == 0:
        print("No string found with this prefix")

    print(comp[0])


if __name__ == "__main__":
    main()