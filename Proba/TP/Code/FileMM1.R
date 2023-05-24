FileMM1 <- function(lambda, mu, D) {
  arrivee <- c()
  depart <- c()
  attente_service <- c()
  
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
        temps_attente_service <- 0;
        temps_depart <- temps_arrivee + duree_attente
      }
      else 
      {
        temps_attente_service <- temps_depart - temps_arrivee
        temps_depart <- temps_depart + duree_attente
      }
      depart <- c(depart, temps_depart)
      attente_service <- c(attente_service, temps_attente_service)
    }
  }
  
  return(list(arrivee = arrivee, depart = depart, attente_service = attente_service))
}