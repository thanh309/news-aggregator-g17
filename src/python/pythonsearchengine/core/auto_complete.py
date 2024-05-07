# we will Trie data structure to make the auto_complete feature

class TrieNode():
    def __init__(self):
        self.children = {}
        self.is_word = False

class Trie():
    def __init__(self, words_dict) -> None:
        self.root = TrieNode()
        self.words_dict = words_dict

    def createTrie(self):

        for word in self.words_dict:
            self.insert(word)

    def insert(self, word):

        node = self.root

        for char in word:
            if char not in node.children:
                node.children[char] = TrieNode()

            node = node.children[char]

        node.is_word = True

class AutoComplete(Trie):
    def __init__(self, words_dict) -> None:
        super().__init__(words_dict)

    def autocomplete(self, node, word):

        results = []
        if node.is_word:
            results.append(word)

        for char, childnode in node.children.items():
            results.extend(self.autocomplete(childnode, word + char))

        return results

    def calc_score(self, word):
        return self.words_dict[word]

    def get_suggestion(self, word):

        ans = []

        node = self.root

        for char in word:

            if char not in node.children:
                return (ans, 0)

            node = node.children[char]


        if not node.children:
            return (ans, -1)

        # we need at most 10-highest-score suggestions
        ans = sorted(self.autocomplete(node, word), key = self.calc_score)[:10]

        return (ans, 1)

