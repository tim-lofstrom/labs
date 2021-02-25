'''
Created on 29 sep 2013

@author: Kurtan
'''

import bounce
import crossSum
import newtonRaph

def checkIfInt(i): #kollar om i verkligen ar en int
    try:
        int(i)
        return True #om i ar en int, returnera true
    except ValueError:
        return False

def printMenu():#funktion for skriva ut menyn
    print "----------------------"
    print "Huvudmeny:"
    print ""
    print "1. Bounce"
    print "2. Cross Sum"
    print "3. Newton Raph"
    print "4. Exit program"

while True:
    printMenu() #skriv ut meny
    alt = raw_input() #hamtar ett input fran konsol, input for vilket alternativ anvandaren vill ha
    
    while((checkIfInt(alt) == False) or ((int(alt) < 1) or (int(alt) > 4)) == True):        
        print "Maste valja nagot av de giltiga alternativen."
        printMenu() #skriv ut menyn
        alt = raw_input()
        
        
    if(int(alt) == 1):
        bounce.menu()
    elif(int(alt) == 2):
        crossSum.menu()
    elif(int(alt) == 3):
        newtonRaph.menu()
    elif(int(alt) == 4):
        print "programmet avslutas..."
        break
        
        
        
        
        
        
        
