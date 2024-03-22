from flask import Flask, jsonify

app = Flask(__name__)


@app.route('/generate_json')
def generate_json_endpoint():
    """Generates a JSON object and returns it as a JSON response."""

    data = {
        "name": "Alice",
        "age": 30,
        "city": "New York",
        "skills": ["Python", "Java", "JavaScript"],
    }

    return jsonify(data)  # Return the data as a JSON response


if __name__ == '__main__':
    app.run(debug=True)  # Start the Flask development server
