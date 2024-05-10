from src.python.pythonsearchengine.core.search_engine import *
from src.python.pythonsearchengine.core.file_handling import *
import csv
import string
import pandas as pd

def search_from_file(csv_file, query):

    tokenized_query = remove_puncts(query, string).split()

    corpus = []
    corpus_index = []

    df = pd.read_csv(csv_file, encoding='utf-8')

    for line in df['INDEX']:
        corpus_index.append(line)

    with open(csv_file, 'r', encoding='utf-8') as csvf:
        reader = csv.reader(csvf)
        data_list = list(reader)

    corpus = [' '.join(lst) for lst in data_list[1:]]

    tokenized_corpus = [remove_puncts(doc, string).split() for doc in corpus]

    bm25 = BM25Okapi(tokenized_corpus)

    scores = bm25.get_scores(tokenized_query)

    cnt = 0
    for score in scores:
        if score != 0.0:
            cnt += 1

    lines1 = bm25.get_top_n(tokenized_query, corpus_index, n=cnt)

    return lines1