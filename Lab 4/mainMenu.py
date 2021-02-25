'''
Created on 15 okt 2013

@author: Kurt
'''

import phonebook

class mainMenu:
    
    def __init__(self): #constructor of mainMenu, assign book as instance of phonebook() in file phonebook
        self.book = phonebook.phonebook()
        print("Welcome to phonebook, type ? for help")
    
    def main_funct(self): #function that gets input and checks what alternative to execute
        self.inp = self.secureInput()
        self.alt = self.inp[0]
        self.alt = self.alt.lower() #makes lower or upper letter mean the same
        
        if(self.alt == "add"): #add new person by name and number
            self.book.addPerson(self.inp[1], self.inp[2]) #parameters name, number
            print self.inp[1] + " with number " + self.inp[2] + " added to phonebook"
            return
        
        if(self.alt == "remove"): #remove person
            if(len(self.inp) == 2):
                self.book.removePerson(self.inp[1]) #parameter name
            elif(len(self.inp) == 3):
                self.book.removePerson(self.inp[1], self.inp[2]) #parameters name, number
            print self.inp[1] + " is removed"
            return
        
        if(self.alt == "lookup"): #lookup a person by serching for hims
            res = None
            if(len(self.inp) == 2):
                res = self.book.lookupPerson(self.inp[1]) #parameter name
            elif(len(self.inp) == 3):
                res = self.book.lookupPerson(self.inp[1], self.inp[2]) #parameter name, number
            for p in res:
                print "Name: " + p.getName() + " \tAlias: " + p.getAlias() + " \tNumber: " + p.getNumber() #result found, give it a nice printout
            return
        
        if(self.alt == "alias"): #set alias to a person
            if(len(self.inp) == 3):
                self.book.aliasPerson(self.inp[1], self.inp[2]) #parameters name, alias
            elif(len(self.inp) == 4):
                self.book.aliasPerson(self.inp[1], self.inp[2], self.inp[3]) #parameters name, alias, number
            print self.inp[1] + " now has alias " + self.inp[2]
            return
        
        if(self.alt == "change"): #change a persons number
            if(len(self.inp) == 3):
                self.book.changePerson(self.inp[1], self.inp[2]) #parameters name, newname
            elif(len(self.inp) == 4):
                self.book.changePerson(self.inp[1], self.inp[2], self.inp[3]) #parameters name, newname, number
            print self.inp[1] + " changed to " + self.inp[2]
            return
        
        if(self.alt == "save"): #save the phonebook into a file
            self.book.saveBook(self.inp[1])
            print "file '" + self.inp[1] + "' successfully saved"
            return
        
        if(self.alt == "load"): #load a file into the phonebook
            self.book.loadBook(self.inp[1])
            print "file '" + self.inp[1] + "' successfully loaded"
            return
        
        if(self.alt == "traverse"): #traverse and print all persons in phonebook
            res = self.book.traverse()
            for p in res:
                print "Name: " + p.getName() + " \tAlias: " + p.getAlias() + " \tNumber: " + p.getNumber() #result found, give it a nice printout
            return
        
        if(self.alt == "?"): #prints avaliable options
            self.printOptions()
            return
        
        if(self.alt == "exit"): #exit the program by returning false
            return False
        
        raise Exception, self.inp[0] + " is not a valid command, type ? for help"
        
    def secureInput(self): #function that returns a list of the inputs that is separated with space
        inp = raw_input("telbook>").split()
        return inp
    
    def printOptions(self): #help function that prints all avaliable options
        print("-------------------------")
        print("Avaliable commands:")
        print("")
        print("Command:\tParameter:")
        print("add \t\tname number")
        print("remove \t\tname number")
        print("lookup \t\tname number")
        print("alias \t\tname newname")
        print("change \t\tname number")
        print("save \t\tfilename")
        print("load \t\tfilename")
        print("traverse")
        print("exit")
        print("")        

        
def main():
    m = mainMenu() #assign m the instance of mainMenu
    while True:
        try:
            if(m.main_funct() == False): #if main_funct returns false, the program will exit
                break
        except IndexError: #index error means not enough parameters was entereds
            print "Error: Bad parameters"
        except IOError, e: #input otuput error means filehandling gone wrong
            print "File could not be found"
        except LookupError, e: #person not found
            print e
        except Exception, e: # e contains error message
            print e
        
    
if  __name__ =="__main__":main() #start main




