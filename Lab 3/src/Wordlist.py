'''
Created on 28 sep 2013

@author: Kurtan
'''

def list_string(): # funktionen for stringlistan
    word = [] #definera tom lista for ord
    desc = [] #definera tom lista for beskrivningar
    printMenu() #kor funktionen som skriver ut menu
    alt = getInput() #kor funktion for att hamta input, funktinen innehaller felkontroll
    while (alt != 5): #5 an avslutar programmet
        if(alt == 1):
            exist = False 
            print "Ord att lagga till ->"
            w = raw_input()
            for i in range(0, len(word)): #loopar igenom listan med ord
                if(word[i] == w):#kollar om inskrivet ord existerar
                    exist = True #om det existerar, satt exist = true
                    print "Ordet du vill lagga till existerar redan i listan"
                    break #ordet existerade, bryt loopen
            if(exist == False): #om ordet inte existerade
                word.append(w) #lagg till w, alltsa det inskrivna ordet i listan
                print "Beskrivning for ordet ->"
                desc.append(raw_input()) #skriv in beskrivning for ordet
        elif(alt == 2):
            print "Sok efter ord i listan ->"
            search = raw_input() #hamta input for sok
            for i in range(0, len(word)): #loopar igenom listan med ord
                if(word[i] == search): #om det ar en traff
                    print "Ord:", word[i]
                    print "Beskrivning: ", desc[i]
                    break
        elif(alt == 3):
            print "Ord att ta bort ->"
            delete = raw_input() #hamta input for delete
            for i in range(0, len(word)): #lopa listan med ord
                if(word[i] == delete): #om det ar en traff
                    word.pop(i) #delete
                    print "Ord borttaget"
        elif(alt == 4): #traversera listan
            for i in range(0, len(word)):
                print i, ". Ord:", word[i], "Beskrivning:", desc[i]
                    
        printMenu() #skriv ut menyn igen
        alt = getInput() #hamta en ny input for menuvalet
    
def list_tupler(): #funktion for tupler-listan
    theList = () #skapa en tom tuple
    printMenu() #skriv ut menyn
    alt = getInput() #hamta input for menyval
    while(alt != 5): # 5 an avslutar programmet
        if(alt == 1):
            exist = False #skapa en variebel som kollar om ordet existerar
            print "Ord att lagga till ->"
            w = raw_input() #hamta input for nytt ord
            for i in range(0, len(theList)): #loopa igenom tuple
                if(theList[i] == w): #om det skrivna ordet redan finns i tuple
                    exist = True #satt exist till true
                    print "Ordet du vill lagga till existerar redan i listan"
                    break #bryt loopen
            if(exist == False): #om ordet inte fanns i tuple
                theList += w, # lagg till w till tuple
                print "Beskrivning for order ->"
                theList += raw_input(), #hamta input for beskrivningen av ordet
                
        elif(alt == 2): #alternativ 2, sok
            print "Sok efter ord i listan ->"
            search = raw_input() #hamtar input for sok
            result = None #satter result till none
            for i in range(0, len(theList)): #loopar igenom tuplen
                if(theList[i] == search): #om ordet finns i listan
                    result = str(i) + ". Ord:" + str(theList[i]) + " Beskrivning:" + str(theList[i+1]) #satt in resutatet i variebeln
                    break
            if(result ==None): #om inget resultat hittades, skriv felmeddelande
                print "Inget ord i listan matchade din sokning"
            else:
                print result
        elif(alt == 3): #alternativ 3, ta bort
            print "Ord att ta bort ->"
            delete = raw_input() #hamta input for vilket ord som sak tas bort
            for i in range(0, len(theList)): #loopar igenom tuplen
                if(theList[i] == delete): #om den hittar vad som ska tas bort
                    tempList = list(theList) #gor om till en lista
                    tempList.pop(i) #ta bort
                    tempList.pop(i) #ta bort
                    theList = tuple(tempList) # gor om den modifierade listan till en tuple
                    print "Ord borttaget"
                    break
        elif(alt == 4): # alternativ 4, traversera
            i = 0
            while(i < len(theList)): #loopar igenom listan
                print str(i/2) + ". Ord:" + str(theList[i]) + " Beskrivning:" + str(theList[i+1]) #skriver ut ord och tillhorande beskrivning
                i = i + 2
        
        printMenu() #skriv ut menyn
        alt = getInput() #hamta nytt input for nasta val

def list_dictionary(): #funktion for dictionary-listan
    theList = {} #skapa en tom dictionary
    printMenu() #skriv ut menyn
    alt = getInput() #hamta input som bestammer menyvalet, felhantering sker i funktionen
    while (alt != 5): #alternativ 5 avslutar programmet
        if(alt == 1): #alternativ 1, lagg till
            exist = False #skapa variabel for att kolla om ordet redan existerar
            print "Ord att lagga till ->"
            w = raw_input() # hamtar input for ordet som ska laggas till
            if(w in theList): #kollar om ordet finns i listan som keyword
                exist = True #satter exist till true
                print "Ordet du vill lagga till existerar redan i listan"
            if(exist == False):#om ordet inte existerade i listan
                print "Beskrivning for order ->"
                theList[w] = raw_input() #skapar ett nytt keyword med ordet, hamtar input for beskrivning
        elif(alt == 2):
            print "Sok efter ord i listan ->"
            search = raw_input() #hamtar input for sokningen
            result = None #satter variabeln result till none
            i = 0
            for k, v in theList.iteritems(): #loopar igenom alla items i dictionaryt
                if(k == search): #om keyword matchar sokningen
                    result = str(i) + ". Ord:" + str(k) + " Beskrivning:" + str(v) #satt resultvariabeln till det hittade ordet
                    i += 1
                    break
            if(result ==None): #om inget ord hittats
                print "Inget ord i listan matchade din sokning" #felmeddelande
            else:
                print result #allt gick som det skulle, skriv ut resultatet
        elif(alt == 3): #alternativ 3, ta bort ord
            print "Ord att ta bort ->"
            delete = raw_input() #hamtar input for delete
            for k, v in theList.iteritems(): #loopar igenom alla items i dictionaryt
                if(k == delete): #om keyword matchar sokningen
                    del theList[delete] #ta bort det inkriva ordet fran listan
                    print "Ord borttaget"
        elif(alt == 4): #alternativ 4, traverserar dictionaryt
            i = 0
            for k, v in theList.iteritems():
                print str(i) + ". Ord:" + str(k) + " Beskrivning:" + str(v)
                i += 1
            
        printMenu() #skriv ut menyn
        alt = getInput() #hamtar nytt input for menyval
    

def checkIfInt(i): #kollar om i verkligen ar en int
    try:
        int(i)
        return True #om i ar en int, returnera true
    except ValueError:
        return False

def printMenu(): #funktion for skriva ut menyn
    print "----------------------"
    print "Huvudmeny:"
    print ""
    print "1. Insert"
    print "2. Lookup"
    print "3. Delete"
    print "4. Traverse"
    print "5. Exit program"
    print "->"
    
    
def getInput():
    while True:
        alt = raw_input() #hamtar ett input fran konsol, input for vilket alternativ anvandaren vill ha
        #kolla sa det inskrivna vardet ar en int, och ar inom intervallet for menyvalen
        while((checkIfInt(alt) == False) or ((int(alt) < 1) or (int(alt) > 5)) == True):
            print "Valj nagot av de giltiga alternativen."
            printMenu() #skriv ut menyn
            alt = raw_input()
        return int(alt)

        
#valj vilken losning som ska koras

#list_string()
#list_tupler()
list_dictionary()






