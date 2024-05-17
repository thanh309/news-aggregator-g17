'''Import necessary libraries'''

import yfinance as yf
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn import metrics
import numpy as np
import datetime

today = datetime.date.today()
today = today.strftime('%Y-%m-%d')

'''Download the historical data'''

bitcoin = yf.download('BTC-USD', start='2014-09-17', end=today)

'''Prepare data for model'''
# Create a variable for predicting 'n' days out into the future
future_days = 7

# Create new columns called prediction
bitcoin['Prediction'] = bitcoin[['Adj Close']].shift(-future_days)

# Create the independent data set (X)
X = np.array(bitcoin[['Open', 'High', 'Low', 'Close', 'Adj Close', 'Volume']])
X = X[:-future_days]

# Create the dependent data set (y)
y = bitcoin['Prediction'].values
y = y[:-future_days]

# Split the data into 85% training and 15% testing data sets

# to_row = int(len(bitcoin) * 0.85)
# X_train = X[0:to_row]
# X_test = X[to_row:]
# y_train = y[0:to_row]
# y_test = y[to_row:]

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)
'''Modeling'''

# Create and train the model
model = LinearRegression()
# Train the model
model.fit(X_train, y_train)

# Test the model using score
model_confidence = model.score(X_test, y_test)
print("Linear regression confidence: ", model_confidence)

'''Prediction'''
# Create a variable called x_projection and set it equal to the last 30 rows of data from the original data set
x_projection = np.array(bitcoin[['Open', 'High', 'Low', 'Close', 'Adj Close', 'Volume']])[-future_days:]
model_prediction = model.predict(x_projection)
print("The prices of bitcoin in the next 7 days: \n", model_prediction)