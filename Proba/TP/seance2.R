source("fileMM1.R")

lambda <- 10
mu <- 20
D <- 1000

result <- FileMM1(lambda, mu, D)

temps <- seq(1, D, 1)
nb_clients <- sapply(temps, function(t) sum(result$arrivee <= t & result$depart > t))
nb_clients_attente <- sapply(temps, function(t) max(0, sum(result$arrivee <= t & result$depart > t)-1))

plot(temps, nb_clients, type = "h", xlab = "Temps", ylab = "Nombre de clients", main = "Évolution du nombre de clients dans la file d'attente")


alpha <- lambda / mu
E_N_th <- alpha / (1 - alpha)
E_W_th <- E_N / lambda
E_Wa_th <- E_W_th - 1/mu
E_Na_th <- lambda * E_A

temps_attente <- result$depart - result$arrivee

E_N = mean(nb_clients)
E_W = mean(temps_attente)
E_Wa = mean(result$attente_service)
E_Na = mean(nb_clients_attente)

E_N_th
E_N

E_W_th
E_W

E_Wa_th
E_Wa

E_Na_th
E_Na
