from math import *
import numpy as np

class BM25:

    def __init__(self, corpus) -> None:
        self.corpus_size = 0  # here is the number of documents in corpus
        self.avgdl = 0  # here is the avg length of all documents
        self.tf = []
        self.idf = {}
        self.doc_len = []

        df = self._initialize(corpus)
        self._calc_idf(df)

    def _initialize(self, corpus):
        df = {}  # dictionary {word : number of documents with word}
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
        pass

    def get_scores(self, query):
        pass

    def get_top_n(self, query, documents, n=1):

        assert self.corpus_size == len(documents), ":) never occur"

        scores = self.get_scores(query)
        top_n = np.argsort(scores)
        top_n = list(top_n)[::-1][:n]

        return [documents[i] for i in top_n]


class BM25Okapi(BM25):
    def __init__(self, corpus, k1=1.5, b=0.75, epsilon=0.25) -> None:
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
                                         (q_freq + self.k1 * (1 - self.b + self.b * doc_len / self.avgdl)))

        return scores



