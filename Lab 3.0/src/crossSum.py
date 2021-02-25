'''
Created on 20 sep 2013

@author: Kurt
'''


def crossSumRek(n): #rekursiv funktion for tvarsumma
    if(n < 10): #om n ar mindre an 10 har sista siffran hittat, den ska bara returneras
        return n
    result = n % 10 #hamtar sista siffran i n
    result += (crossSumRek(n / 10)) #adderar result, samt kor sig sjalv, rekursivt
    return result #returnerar resultatet

def crossSumIte(n):#iterativ funktion for tvarsumma
    result = 0
    while(n > 10):
        result += n % 10 #hamtar sista siffran i n
        n = n / 10 #plockar bort sista siffran i n
    return result + n % 10 #returnerar resultatet, adderar i sista hand pa den sista siffran
        
    
def checkIfInt(i): #kollar om i verkligen ar en int, dvs, ett heltal
    try:
        int(i)
        return True #returnera true
    except ValueError:
        return False #returnera false
    
def menu():
    
    #skriver ut en enkel meny
    print "-----------------------"
    print "Valj alternativ->"
    print "1. Tvarsumma, rekursivt(n)"
    print "2. Tvarsumma, iterativt(n)"
    
    
    alt = raw_input() #hamtar ett input fran konsol, input for vilket alternativ anvandaren vill ha
    while(True):
        if(checkIfInt(alt) == False): #kollar om alternativet ar en int
            print "Antalet maste vara ett heltal, forsok igen ->"
        elif((int(alt) == 1) or (int(alt) == 2)): #kollar sa alternativer ar giltigt
            break
        else:
            print "Maste valja nagot av de giltiga alternativen."
            print "1. Tvarsumma, rekursivt(n)"
            print "2. Tvarsumma, iterativt(n)"
        alt = raw_input()
    
    print "Skriv in parameter n for vald funktion->"
    
    n = raw_input() #hamtar ett input fran konsol, input for n
    while(True):
        if(checkIfInt(n) == False): #kollar om siffran ar en int
            print "Antalet maste vara ett positivt heltal, forsok igen ->"
        elif(int(n) < 0): #kollar sa siffran ar giltig
            print "Antalet maste vara ett positivt heltal, forsok igen ->"
        else:
            break
        n = raw_input()
    
    if(int(alt) == 1): #om anvandaren valt alternativ 1
        print "Tvarsumman for "+ str(n) + " ar: " + str(crossSumRek(int(n)))
    elif(int(alt) == 2): #om anvandaren valt alternativ 2
        print "Tvarsumman for "+ str(n) + " ar: " + str(crossSumIte(int(n)))
    print ""






