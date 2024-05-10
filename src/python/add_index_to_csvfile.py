import pandas as pd

def add_index_to_csvfile(input_file, output_file):
    df = pd.read_csv(input_file)

    df.insert(0, 'INDEX', range(df.shape[0]))
    df.to_csv(output_file, index=False)

if __name__ == "__main__":
    input_file = input()
    output_file = input()
    add_index_to_csvfile(input_file, output_file)