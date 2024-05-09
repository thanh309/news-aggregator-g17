import csv
import json

def remove_puncts(input_string, string):
    return input_string.translate(str.maketrans('', '', string.punctuation)).lower()


def make_json(csv_file, json_file): # NOT USE
    data = {}

    with open(csv_file, encoding='utf-8') as csvf:
        csvReader = csv.DictReader(csvf)

        key = 0
        for rows in csvReader:
            data[key] = rows
            key += 1

    with open(json_file, 'w', encoding='utf-8') as jsonf:
        jsonf.write(json.dumps(data, indent=4))


