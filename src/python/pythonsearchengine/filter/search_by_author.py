from src.python.pythonsearchengine.core.search_engine import *
from src.python.pythonsearchengine.core.file_handling import *
import csv
import string
import pandas as pd

def search_by_author(csv_file):

    query = input("Enter the author's name here: ")
    tokenized_query = remove_puncts(query, string).split()

    corpus_author = []
    corpus_index = []

    df = pd.read_csv(csv_file, encoding='utf-8')

    for line in df['INDEX']:
        corpus_index.append(line)

    for author in df['AUTHOR']:
        corpus_author.append(author)

    tokenized_corpus = [remove_puncts(doc, string).split() for doc in corpus_author]

    bm25 = BM25Okapi(tokenized_corpus)

    scores = bm25.get_scores(tokenized_query)

    cnt = 0
    for score in scores:
        if score != 0.0:
            cnt += 1

    lines1 = bm25.get_top_n(tokenized_query, corpus_index, n=cnt)

    return lines1