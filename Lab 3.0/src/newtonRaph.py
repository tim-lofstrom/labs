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

def checkIfInt(i): #kollar om i verkligen ar en int
    try:
        int(i)
        return True #om i ar en int, returnera true
    except ValueError:
        return False


def menu():
    #skriver ut en enkel meny
    print "-----------------------"
    print "Valj alternativ->"
    print "1. f(x) = 2^x-1"
    print "2. f(x) = x^2-1"
    print "3. f(x) = x-e^-x"
    
    
    alt = raw_input() #hamtar ett input fran konsol, input for vilket alternativ anvandaren vill ha
    while(True):
        if(checkIfInt(alt) == False): #kollar om alternativet ar en int
            print "Antalet maste vara ett heltal, forsok igen ->"
        elif((int(alt) == 1) or (int(alt) == 2) or (int(alt) == 3)): #kollar sa alternativer ar giltigt
            break
        else:
            print "Maste valja nagot av de giltiga alternativen."
            print "1. f(x) = 2^x-1"
            print "2. f(x) = x^2-1"
            print "3. f(x) = x-e^-x"
        alt = raw_input()#om alternativet inte ar giltigt, hamta ett nytt input
    
    print "Skriv in parameter x0 for vald funktion->"
    
    n = raw_input() #hamtar ett input fran konsol, input for n
    while(True):
        if(checkIfInt(n) == False):
            print "Antalet maste vara ett heltal, forsok igen ->"
        else:
            break
        n = raw_input()
    
    if(int(alt) == 1): #om anvandaren valt alternativ 1
        print newtonRaph(f, int(n), 0.000001)
    elif(int(alt) == 2): #om anvandaren valt alternativ 2
        print newtonRaph(f2, int(n), 0.000001)
    elif(int(alt) == 3):
        print newtonRaph(f3, int(n), 0.000001)
    print ""











