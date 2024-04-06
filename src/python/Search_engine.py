import csv
from math import *
import regex as re
import pandas as pd
import time
import numpy as np
import string
import json

class BM25:

    def __init__(self, corpus) -> None:
        self.corpus_size = 0    # here is the number of documents in corpus
        self.avgdl = 0          # here is the avg length of all documents
        self.tf = []
        self.idf = {}
        self.doc_len = []

        df = self._initialize(corpus)
        self._calc_idf(df)

    def _initialize(self, corpus):
        df = {}  # word : number of documents with word
        len_docs = 0

        for document in corpus:
            self.doc_len.append(len(document))
            len_docs += len(document)

            frequencies = {}
            for word in document:
                if word not in frequencies:
                    frequencies[word] = 1
                else:
                    frequencies[word] += 1

            self.tf.append(frequencies)

            for word, freq in frequencies.items():
                if word in df:
                    df[word] += 1
                else:
                    df[word] = 1

            self.corpus_size += 1

        self.avgdl = len_docs / self.corpus_size

        return df

    def _calc_idf(self, df):
        raise NotImplementedError()

    def get_scores(self, query):
        raise NotImplementedError()

    def get_top_n(self, query, documents, n = 1):

        assert self.corpus_size == len(documents), "The documents given don't match the index corpus!"

        scores = self.get_scores(query)
        top_n = np.argsort(scores)
        top_n = list(top_n)[::-1][:n]

        return [documents[i] for i in top_n]

class BM25Okapi(BM25):
    def __init__(self, corpus, k1 = 1.5, b = 0.75, epsilon = 0.25) -> None:
        self.k1 = k1
        self.b = b
        self.epsilon = epsilon
        super().__init__(corpus)

    def _calc_idf(self, df):
        idf_sum = 0
        negative_idfs = []
        for word, freq in df.items():
            idf = log(self.corpus_size - freq + 0.5) - log(freq + 0.5)
            self.idf[word] = idf
            idf_sum += idf
            if idf < 0:
                negative_idfs.append(word)
        self.average_idf = idf_sum / len(self.idf)

        eps = self.epsilon * self.average_idf
        for word in negative_idfs:
            self.idf[word] = eps

    def get_scores(self, query):
        scores = np.zeros(self.corpus_size)
        doc_len = np.array(self.doc_len)

        for q in query:
            q_freq = np.array([doc[q] if q in doc else 0 for doc in self.tf])
            if q in self.idf:
                scores += self.idf[q] * (q_freq * (self.k1 + 1) /
                                         (q_freq + self.k1 *(1 - self.b + self.b * doc_len / self.avgdl)))

        return scores

def remove_puncts(input_string, string):
    return input_string.translate(str.maketrans('', '', string.punctuation)).lower()

def make_json(csv_file, json_file):

    data = {}

    with open(csv_file, encoding = 'utf-8') as csvf:
        csvReader = csv.DictReader(csvf)

        key = 0
        for rows in csvReader:
            data[key] = rows
            key += 1

    with open(json_file, 'w', encoding='utf-8') as jsonf:
        jsonf.write(json.dumps(data, indent = 4))

if __name__ == "__main__":

    start_time = time.time()
    query = input()
    tokenized_query = remove_puncts(query, string).split()

    make_json('../resources/game.csv', '../resources/jsonfile.json')

    corpus = []

    with open('../resources/jsonfile.json', 'r') as jsonf:
        data = json.load(jsonf)

    for i in range(len(data)):
        corpus.append(' '.join(data[str(i)].values()))


    tokenized_corpus = [remove_puncts(doc, string).split() for doc in corpus]

    bm25 = BM25Okapi(tokenized_corpus)

    scores = bm25.get_scores(tokenized_query)

    cnt = 0
    for score in scores:
        if score != 0.0:
            cnt +=1

    lines1 = bm25.get_top_n(tokenized_query, corpus, n = cnt)

    with open('../resources/answer.txt', 'w') as f:
        for line in lines1:
            f.write(line + "\n")

    end_time = time.time()

    print(end_time - start_time)
