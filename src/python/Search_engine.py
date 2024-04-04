import time
from math import *

import pandas as pd
import regex as re


class Normalize:
    global df
    df = pd.read_csv('../resources/game.csv')

    # with open("output.txt", 'w') as f:
    # f.write(df.to_string)

    global storage
    storage = []
    with open("../resources/output.txt", 'r') as f:
        for i in range(1, len(df) + 2):
            line = f.readline()
            storage.append(line.lower())

    global keys
    keys = [line for line in df]

    global d
    d = dict()

    for line in storage:
        d[line] = dict()
        for word in line.split():
            if word not in d[line]:
                d[line][word] = 1
            else:
                d[line][word] += 1
    global D
    D = 0
    for key in keys:
        doc = list(df[key])
        D += len(doc)

    def __init__(self) -> None:
        self.keys = keys
        self.df = df
        self.N = len(df)
        self.d = d
        self.D = D


class BM25Score(Normalize):

    def __init__(self, pattern) -> None:
        super().__init__()
        self.k = 1.2
        self.b = 0.75
        self.pattern = pattern

    def numTerms(self, word):

        # this method find the number of terms in query 
        new_pattern = re.sub(r'[\W_]', ' ', word)

        words = new_pattern.split()

        return (words, len(words))

    def TF(self, word, document):

        # This method count the number of times pattern appears in the document
        if word not in self.d[document]:
            return 0
        return self.d[document][word]

    def DF(self, word):

        # This method count the number of documents that contain the pattern
        cnt = 0
        for key in self.keys:
            doc = ' '.join(str(i) for i in list(self.df[key]))
            doc = doc.lower()
            cnt += doc.count(pattern)

        return cnt

    def IDF(self, word):

        # This method find the inverse document frequency of word
        numerator = self.N - self.DF(word) + 0.5
        denominator = self.DF(word) + 0.5

        return log(numerator / denominator + 1)

    def avgLength(self):

        # This method find the ave length of all documents.
        return len(self.keys)

    def score(self, document):

        # This method find the score of the document when searching pattern
        total = 0
        for word in self.numTerms(self.pattern)[0]:
            total += self.IDF(word) * (self.TF(word, document) * (self.k + 1)) / (
                    self.TF(word, document) + self.k * (1 - self.b + self.b * self.D / self.avgLength()))

        return total


if __name__ == "__main__":
    start_time = time.time()
    pattern = input().lower()

    rank = BM25Score(pattern)

    # storage = []
    # with open("output.txt", 'r') as f:
    #     for i in range(1, len(df) + 2):
    #         line = f.readline()
    #         storage.append(line.lower())

    # print(storage[1])
    # print(score.TF(pattern, storage[1]))
    # print(score.DF(pattern))
    # print(score.IDF(pattern))
    # print(score.score(storage[1]))

    # d = dict()
    # for line in storage:
    #     d[line] = dict()
    #     for word in line.split():
    #         if word not in d[line]:
    #             d[line][word] = 1
    #         else:
    #             d[line][word] += 1

    score_dict = {}
    for line in storage:
        point = rank.score(line)
        if point != 0.0:
            score_dict[line] = point

    ans = sorted(list(score_dict.keys()), key=lambda x: -score_dict[x])
    print(*ans)
    print(time.time() - start_time)
