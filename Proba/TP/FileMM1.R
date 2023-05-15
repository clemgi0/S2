FileMM1 <- function(lambda, mu, D) {
  arrivee <- c()
  depart <- c()
  
  temps_arrivee <- 0
  temps_depart <- 0
  
  while (temps_arrivee < D) 
  {
    intervalle_arrivee <- rexp(1, lambda)
    temps_arrivee <- temps_arrivee + intervalle_arrivee
    
    if (temps_arrivee < D) {
      arrivee <- c(arrivee, temps_arrivee)
      duree_attente <- rexp(1, mu)
      if (temps_arrivee > temps_depart)
      {
        temps_depart <- temps_arrivee + duree_attente
      }
      else 
      {
        temps_depart <- temps_depart + duree_attente
      }
      depart <- c(depart, temps_depart)
    }
  }
  
  return(list(arrivee = arrivee, depart = depart))
}