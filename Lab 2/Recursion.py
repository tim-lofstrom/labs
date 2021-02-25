'''
Created on 16 sep 2013

@author: Kurt
'''
def bounce(n): #funktion for rekursiv bounce
    if(n >= 0): #kollar om n ar positivt, satt om den ska bounca upp eller ner
        dir = -1
    else:
        dir = 1
    if(n != 0):
        print n,
        bounce(n + dir) #dir bestammer om bouncen ar negativ eller positiv
        print n,
    else:
        print 0,

def bounce2(n):#funktion for iterativ bounce
    if(n >= 0): #kollar om n ar positivt, satt om den ska bounca upp eller ner
        dir = 1
    else:
        dir = -1
    for i in range(0, abs(n)): #loopar fran n till 0
        print n - (i * dir), #skriver ut n, minus i ganger vilkett hall
    for i in range(0, abs(n)+1): #loopar fran 0 tillbaka till startvardet
        print i * dir, #skriver ut i, ganger dir, vilket bestammer om bouncen ar negativ eller positiv
        
def checkIfInt(i): #kollar om i verkligen ar en int
    try:
        int(i)
        return True #om i ar en int, returnera true
    except ValueError:
        return False 
    
while (True): #kor menuprogrammet flera ganger
    
    #skriver ut en enkel meny
    print "-----------------------"
    print "Valj alternativ->"
    print "1. Bounce(n)"
    print "2. Bounce2(n)"
    
    
    alt = raw_input() #hamtar ett input fran konsol, input for vilket alternativ anvandaren vill ha
    while(True):
        if(checkIfInt(alt) == False): #kollar om alternativet ar en int
            print "Antalet maste vara ett heltal, forsok igen ->"
        elif((int(alt) == 1) or (int(alt) == 2)): #kollar sa alternativer ar giltigt
            break
        else:
            print "Maste valja nagot av de giltiga alternativen."
            print "1. Bounce(n)"
            print "2. Bounce2(n)"
        alt = raw_input()#om alternativet inte ar giltigt, hamta ett nytt input
    
    print "Skriv in parameter n for vald funktion->"
    
    n = raw_input() #hamtar ett input fran konsol, input for n
    while(True):
        if(checkIfInt(n) == False):
            print "Antalet maste vara ett heltal, forsok igen ->"
        else:
            break
        n = raw_input()
    
    if(int(alt) == 1): #om anvandaren valt alternativ 1
        bounce(int(n))
    elif(int(alt) == 2): #om anvandaren valt alternativ 2
        bounce2(int(n))
    print ""









