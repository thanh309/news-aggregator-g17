from flask import Flask, jsonify
import yfinance as yf
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import PolynomialFeatures
import numpy as np
import datetime

app = Flask(__name__)


def download_historical_data(coin_code):
    today = datetime.date.today()
    today = today.strftime('%Y-%m-%d')

    """Download the historical data"""

    coin = yf.download(coin_code, start='2018-05-19', end=today)
    return coin


def price_prediction(coin):
    """Prepare data for model"""
    # Create a variable for predicting 'n' days out into the future
    future_days = 30

    # Create new columns called prediction
    coin['Prediction'] = coin[['Adj Close']].shift(-future_days)

    # Create the independent data set (x)
    x = np.array(coin[['Open', 'High', 'Low', 'Close', 'Adj Close', 'Volume']])
    poly = PolynomialFeatures(degree=2)
    x = poly.fit_transform(x)
    x_projection = np.array(x[-future_days:])
    x = x[:-future_days]

    # Create the dependent data set (y)
    y = coin['Prediction'].values
    y = y[:-future_days]

    # Split the data into 80% training data and 20% testing data
    x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.2, random_state=42)
    """Modeling"""

    # Create and train the model
    model = LinearRegression()
    # Train the model
    model.fit(x_train, y_train)

    """Prediction"""
    # Create a variable called x_projection and set it equal to the last 30 rows of data from the original data set
    model_prediction = model.predict(x_projection)
    return [list(coin['Adj Close'])[-1], model_prediction[0], model_prediction[6], model_prediction[29]]


def generate_prices_dict(coin_code_list, coin_name):
    prices_dict = {coin_name[coin_code]: [] for coin_code in coin_code_list}
    for coin_code in coin_code_list:
        coin = download_historical_data(coin_code)
        prices_dict[coin_name[coin_code]] = price_prediction(coin)

    return prices_dict


@app.route('/price_prediction')
def generate_price_prediction_json():
    """Generates a JSON object and returns it as a JSON response."""

    coin_code_list = ['BTC-USD', 'ETH-USD', '^DJI', 'BNB-USD', 'THOREUM17410-USD']
    coin_name = {'BTC-USD': 'Bitcoin USD', 'ETH-USD': 'Ethereum USD', '^DJI': 'Dow 30', 'BNB-USD': 'BNB-USD',
                 'THOREUM17410-USD': 'Thoreum V3 USD'}
    data = generate_prices_dict(coin_code_list, coin_name)

    return jsonify(data)  # Return the data as a JSON response


if __name__ == '__main__':
    app.run()  # Start the Flask development server
