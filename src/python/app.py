from flask import Flask, jsonify

from src.python.pythontrenddetection.trenddetection import generate_trending_dict

app = Flask(__name__)


@app.route('/trend_detection')
def generate_trending_json():
    """Generates a JSON object and returns it as a JSON response."""

    coin_code_list = ['BTC-USD', 'ETH-USD', 'WBTC-USD', 'BNB-USD', 'THOREUM17410-USD']
    data = generate_trending_dict(coin_code_list)

    return jsonify(data)  # Return the data as a JSON response


if __name__ == '__main__':
    app.run(debug=True)  # Start the Flask development server
