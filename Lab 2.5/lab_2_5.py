'''
Created on 20 sep 2013

@author: Kurt
'''
import math


def deriv(f, x, h):
    return (1.0 / (2.0*h)) * (f(x+h) - f(x-h))


def f(x):
    return 2**x-1
    #return x - math.e**(-x)
    
def f2(x):
    return x**2-1

def f3(x):
    return x-math.e**(-x)

def newtonRaph(f, n, h):
    temp = n - f(n)/deriv(f, n, h)
    if(abs(n - temp) < h):
        return n
    else:
        return newtonRaph(f, temp, h)



print newtonRaph(f, -1, 0.00000001)
print newtonRaph(f2, -1, 0.00000001)
print newtonRaph(f3, -5, 0.00000001)

















