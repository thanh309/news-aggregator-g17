import regex as re

if __name__ == "__main__":
    string = input()
    pattern = r'bitcoin'

    print(re.findall(pattern, string))
