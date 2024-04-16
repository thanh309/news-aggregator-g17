from search_engine import *
from file_handling import *
import string
import csv

def search_from_file(csv_file):

    query = input('Enter the query here: ')
    tokenized_query = remove_puncts(query, string).split()

    corpus = []
    data = {}

    with open(csv_file, 'r', encoding='utf-8') as csvf:
        csvReader = csv.DictReader(csvf)

        key = 0
        for rows in csvReader:
            data[key] = rows
            key += 1

    for i in range(len(data)):
        corpus.append(' '.join(data[i].values()))

    tokenized_corpus = [remove_puncts(doc, string).split() for doc in corpus]

    bm25 = BM25Okapi(tokenized_corpus)

    scores = bm25.get_scores(tokenized_query)

    cnt = 0
    for score in scores:
        if score != 0.0:
            cnt += 1

    lines1 = bm25.get_top_n(tokenized_query, corpus, n=cnt)

    with open('../resource/answer.txt', 'w') as f:
        for line in lines1:
            f.write(line + "\n")