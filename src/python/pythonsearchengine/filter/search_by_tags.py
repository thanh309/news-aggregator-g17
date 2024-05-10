from src.python.pythonsearchengine.core.search_engine import *
from src.python.pythonsearchengine.core.file_handling import *
import csv
import string
import pandas as pd

def search_by_tags(csv_file):

    query = input("Enter the tags here: ")
    tokenized_query = remove_puncts(query, string).split()

    corpus_tags = []
    corpus_index = []

    df = pd.read_csv(csv_file, encoding='utf-8')

    for line in df['INDEX']:
        corpus_index.append(line)

    for tags in df['TAGS']:
        if not pd.isna(tags):
            corpus_tags.append(tags)
        else:
            corpus_tags.append("NaN")
    tokenized_corpus = [doc.lower().split('|') for doc in corpus_tags]

    bm25 = BM25Okapi(tokenized_corpus)

    scores = bm25.get_scores(tokenized_query)

    cnt = 0
    for score in scores:
        if score != 0.0:
            cnt += 1

    lines1 = bm25.get_top_n(tokenized_query, corpus_index, n=cnt)

    return lines1
