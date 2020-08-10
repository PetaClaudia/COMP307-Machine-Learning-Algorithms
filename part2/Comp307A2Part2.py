
from sklearn.neural_network import MLPClassifier
from sklearn.linear_model import Perceptron
from sklearn.preprocessing import StandardScaler

wine_training = open("wine_training", "r")
wine_test = open("wine_test", "r")
train_features = []
test_features = []
train_labels = []
test_labels = []

with wine_training:
    next(wine_training)
    for line in wine_training:
        l = line[:-1]
        instance = l.split(' ')
        train_labels.append(instance[-1])
        train_features.append([float(i) for i in instance[:-1]])

with wine_test:
    next(wine_test)
    for line in wine_test:
        l = line[:-1]
        instance = l.split(' ')
        test_labels.append(instance[-1])
        test_features.append([float(i) for i in instance[:-1]])

wine_training.close()
wine_test.close()

clf = MLPClassifier(solver='sgd',max_iter=2000,hidden_layer_sizes=(100),
                    learning_rate='adaptive', learning_rate_init=0.001,
                    momentum=0.9, alpha=0.0001)

scaler = StandardScaler()
scaler.fit(train_features)
train_features = scaler.transform(train_features)
test_features = scaler.transform(test_features)

clf.fit(train_features, train_labels)

print("Predictions: ",clf.predict(test_features))
print("Accuracy: ",clf.score(test_features, test_labels)*100)
