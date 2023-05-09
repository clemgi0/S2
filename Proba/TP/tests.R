Histo <- function(vn, mt, randu, sm)
{
  par(mfrow=c(2,2))
  hist(vn[,1],xlab='',main='Von Neumann')
  hist(mt[,1],xlab='',main='Mersenne Twister')
  hist(RANDU[,1],xlab='',main='RANDU')
  hist(sm[,1],xlab='',main='Standard Minimal')
}

Plot <- function(vn, mt, randu, sm)
{
  par(mfrow=c(2,2))
  plot(vn[1:(Nsimu-1),1],vn[2:Nsimu,1],xlab='VN(i)', ylab='VN(i+1)', main='Von Neumann')
  plot(mt[1:(Nsimu-1),1],mt[2:Nsimu,1],xlab='MT(i)', ylab='MT(i+1)', main='Mersenne Twister')
  plot(RANDU[1:(Nsimu-1),1],RANDU[2:Nsimu,1],xlab='RANDU(i)', ylab='RANDU(i+1)', main='RANDU')
  plot(sm[1:(Nsimu-1),1],sm[2:Nsimu,1],xlab='SM(i)', ylab='SM(i+1)', main='Standard Minimal')
}

Frequency <- function(x, nb)
{
  s <- 0
  n <- length(x)*nb
  for (i in 1:length(x))
  {
    bits <- binary(x[i])
    for (j in 1:nb)
    {
      s = s + 2*bits[j] - 1
    }
  }
  sobs <- abs(s)/sqrt(n)
  P <- 2*(1-pnorm(sobs))
  return(P)
}

Runs <- function(x, nb) 
{
  pi <- 0
  V <- 0
  n <- length(x)*nb
  epsilon <- rep(0, n)
  for (i in 1:length(x))
  {
    bits <- binary(x[i])
    for (j in 1:nb)
    {
      epsilon[(i-1)*nb + j] = bits[j]
    }
  }
  
  for (i in 1:n)
  {
    pi = pi + epsilon[i]
    if (i == n || epsilon[i] != epsilon[i+1]) {
      V = V + 1
    }
  }
  
  pi = pi/n
  to = 2/sqrt(n)
  
  if (abs(pi - 0.5) >= to) {
    return(0.)
  }
  
  P <- 2 * (1 - pnorm(abs(V-2*n*pi*(1-pi))/(2*sqrt(n)*pi*(1-pi))))
  return(P)
  
}

Ordre <- function(x)
{
  P = order.test(x, d=4, echo=FALSE)
  return(P[["p.value"]])
}