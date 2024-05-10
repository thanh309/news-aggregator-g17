from src.python.pythonsearchengine.core.auto_complete import AutoComplete

def main():
    file = input('Enter the file here: ')
    query = input("Enter the query here: ")
    with open(file, 'r') as f:
        string = f.read()
    t = AutoComplete(eval(string))

    t.createTrie()

    comp = t.get_suggestion(query)

    if comp[1] == -1:
        print("No other strings found with this prefix")

    elif comp[1] == 0:
        print("No string found with this prefix")

    print(comp[0])


if __name__ == "__main__":
    main()