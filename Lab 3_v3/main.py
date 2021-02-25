'''
Created on 1 okt 2013

@author: Kurtan
'''
import dictionaryList
import stringList
import tupleList

class main:
    
    def printMenu(self):
        print "----------------------"
        print "Main menu:"
        print ""
        print "1. Insert"
        print "2. Lookup"
        print "3. Delete"
        print "4. Traverse"
        print "5. Exit program"  
        
    def checkIfInt(self, i): #Check if really an int and handle value error
        try:
            int(i)
            return True
        except ValueError:
            return False

    def getInput(self): #get input from user to navigate menu, input must be valid
        while True:
            alt = raw_input("->")
            while((self.checkIfInt(alt) == False) or ((int(alt) < 1) or (int(alt) > 5)) == True): #while the input is not valid
                self.printError(3) #print error code 3, invalid menu input
                self.printMenu()
                alt = raw_input("->")
            return int(alt) #when an valid input is written, return

    def printError(self, errorCode): #general function for displaying error messages by given code
        if(errorCode == 1):
            print "Word already exists"
        elif(errorCode == 2):
            print "Word can not be found"
        elif(errorCode == 3):
            print "Invalid menu input"
        elif(errorCode == 4):
            print "Invalid input"

    def getValidWord(self, d):
        w = raw_input(d)
        temp = w.strip(" ")
        if(temp != ""): 
            return w
        else:
            return False

    def displayResult(self, res): #function for displaying a result, the word and its description
        print "Word: " + str(res[0]) + " Description: " + str(res[1])


    def main_funct(self, theList):
        self.printMenu()
        alt = self.getInput()
        if(alt == 1):
            word = self.getValidWord("Enter word ->")
            if(word == False):
                self.printError(4)
            elif(theList.lookup(word) != None):
                self.printError(1)
            else:
                d = self.getValidWord("Enter description ->")
                if(d != False):
                    theList.add(word, d)
                    return True
                    print "Word inserted"
                else:
                    self.printError(4)
        elif(alt == 2):
            search = self.getValidWord("Search for ->")
            if(search == False):
                self.printError(4)
            else:
                result = theList.lookup(search)
                if(result == None):
                    self.printError(2)
                else:
                    self.displayResult(result)
            return True
        elif(alt == 3):
            delete = self.getValidWord("Delete word ->")
            if(delete == False):
                self.printError(4)
            elif(theList.lookup(delete) == None):
                self.printError(2)
            else:
                theList.delete(delete)
                print "Word deleted"
            return True
        elif(alt == 4):
            print theList.traverse()                
            return True
        elif(alt == 5):
            return False
        
        return True
 
    def __init__(self):
        pass
        
        

m = main()

s = stringList.stringList()
t = tupleList.tupleList()
d = dictionaryList.dictionaryList()

while m.main_funct(t):
    pass

















