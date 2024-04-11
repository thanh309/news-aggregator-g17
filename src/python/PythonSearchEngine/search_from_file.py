from src.python.PythonSearchEngine.file_handling import *
from src.python.PythonSearchEngine.search_engine import *
import string
import json 

def search_from_file(file_name_csv, file_name_json):

    query = input()
    tokenized_query = remove_puncts(query, string).split()

    make_json(file_name_csv, file_name_json)

    corpus = []

    with open(file_name_json, 'r') as jsonf:
        data = json.load(jsonf)

    for i in range(len(data)):
        corpus.append(' '.join(data[str(i)].values()))

    tokenized_corpus = [remove_puncts(doc, string).split() for doc in corpus]

    bm25 = BM25Okapi(tokenized_corpus)

    scores = bm25.get_scores(tokenized_query)

    cnt = 0
    for score in scores:
        if score != 0.0:
            cnt += 1

    lines1 = bm25.get_top_n(tokenized_query, corpus, n=cnt)

    with open('../resources/answer.txt', 'w') as f:
        for line in lines1:
            f.write(line + "\n")


