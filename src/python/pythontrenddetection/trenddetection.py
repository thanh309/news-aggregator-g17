"""Import necessary libraries"""

import yfinance as yf
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn import metrics
import numpy as np
import datetime


def download_historical_data(coin_code):
    today = datetime.date.today()
    today = today.strftime('%Y-%m-%d')

    """Download the historical data"""

    bitcoin = yf.download(coin_code, start='2014-09-17', end=today)
    return bitcoin


def trend_detection(coin):
    """Prepare data for model"""
    # Create a variable for predicting 'n' days out into the future
    future_days = 30

    # Create new columns called prediction
    coin['Prediction'] = coin[['Adj Close']].shift(-future_days)

    # Create the independent data set (x)
    x = np.array(coin[['Open', 'High', 'Low', 'Close', 'Adj Close', 'Volume']])
    x = x[:-future_days]

    # Create the dependent data set (y)
    y = coin['Prediction'].values
    y = y[:-future_days]

    # Split the data into 80% training data and 20% testing data
    x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.2)
    """Modeling"""

    # Create and train the model
    model = LinearRegression()
    # Train the model
    model.fit(x_train, y_train)

    """Prediction"""
    # Create a variable called x_projection and set it equal to the last 30 rows of data from the original data set
    x_projection = np.array(coin[['Open', 'High', 'Low', 'Close', 'Adj Close', 'Volume']])[-future_days:]
    model_prediction = model.predict(x_projection)
    return [list(coin['Adj Close'])[-1], model_prediction[0], model_prediction[6], model_prediction[29]]


def generate_trending_dict(coin_code_list):
    trending_dict = {coin_code: [] for coin_code in coin_code_list}
    for coin_code in coin_code_list:
        coin = download_historical_data(coin_code)
        trending_dict[coin_code] = trend_detection(coin)

    return trending_dict
