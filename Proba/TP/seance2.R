source("fileMM1.R")

lambda <- 30
mu <- 20
D <- 12

result <- FileMM1(lambda, mu, D)

temps <- seq(1, D, 1)
nb_clients <- sapply(temps, function(t) sum(result$arrivee <= t & result$depart > t))

plot(temps, nb_clients, type = "s", xlab = "Temps", ylab = "Nombre de clients", main = "Évolution du nombre de clients dans la file d'attente")
