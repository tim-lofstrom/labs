'''
Created on 15 okt 2013

@author: Kurt
'''

import person

class phonebook:
    
    def __init__(self): #constructor of phonebook, make empty list that will contain instances of persons
        self.personList = []
    
    def addPerson(self, name, number, alias = []): #function to add person
        res = self.lookupPerson(name, number) # check res
        if not res: # if a result
            p = person.person(name, number, alias)
            self.personList.append(p)
            return True
        else:
            print "A person with this name and number already exists"
            return
            
    def lookupPerson(self, name, number = None): #function for looking up persons in list
        result = []
        for p in self.personList: #check all person in personlist
            if(p.getName() == name): #search can be done for both name and alias
                result.append(p) #adding multiple persons to a resultlist
            for a in p.getAlias(): #check all the alias that belongs to the person
                if(a == name): #if alias equals the searched name
                    result.append(p) #append it to the resultlist
        if result and number == None: #if result not empty and number is not given as parameter
            return result
        elif number != None: #if number is given as parameter and matching a person, the final result is found
            filtered = [] #make a list that will contain a filtered list of result
            for r in result: #check all results ant compare numbers
                if(r.getNumber() == number):
                    filtered.append(r)
            if filtered:
                for p in filtered: #all persons in the filtered list
                    if p.getName() == name:
                        return [p] #must be returned as list
        return None
    
    def aliasPerson(self, name, alias, number = None): #function to set alias to person
        result = self.lookupPerson(name, number) #use lookup person to find the specific one to add alias to
        if len(result) == 1: #it should just be one result, if more raise error
            result[0].setAlias(alias) #append the new alias
            print name + " now has alias " + alias
            return True
        else:
            print "Multiple persons found, try add number as parameter" 
            return False
    
    def changePerson(self, name, newnumber, number = None): #function to change a persons number
        result = self.lookupPerson(name, number) #use lookup person to find the specific one to change
        exists = self.lookupPerson(name, newnumber) #use lookup person to find the specific one to change
        if exists:
            print "A person with this name and number already exists"
            return False
        if result:
            if len(result) == 1: #it should just be one result
                result[0].setNumber(newnumber)  #set the new number
                print name + " changed number to " + newnumber
                return True
            else:
                print "Multiple persons found, try add number as parameter" 
                return False
        else:
            print "No results found, cannot change"
            return False
            
    def removePerson(self, name, number = None): #function for removing person
        res = self.lookupPerson(name, number) #make a search for the person
        index = 0 #index will keep track of which person to delete
        if len(res) == 1: #if lookup only gives one result
            for p in self.personList: #check all persons in list and remove the one that matches
                if number == None:
                    if p.getName() == name:
                        self.personList.pop(index)
                        print p.getName() + " is removed"
                        return True
                elif number != None:
                    if p.getNumber() == number and p.getName() == name:
                        self.personList.pop(index)
                        print p.getName() + " is removed"
                        return True
                index += 1
        else:
            if len(res) > 1:
                print "Multiple persons found, try add number as parameter"
                return False
            elif len(res) == 0:
                print "No results found, cannot delete"
                return False
        return
        
    def saveBook(self, filename): #functions for saving the phonebook to a file, by filename as parameter
        
        pattern = list(";:,.!%&/(()=?<>~^|*") #makes a patternlist for illegal characters
        for p in pattern:
            if p in filename: #if filename contains one of the illegal characters, raise error and the file will not be created
                raise Exception, "Filename contains invalid characters"
        
        if len(self.personList) < 1: #if there are no persons in the list, do not make any file, raise error
            raise Exception, "Cannot save empty phonebook"
        f = open("books/"+filename+".txt", "w") #open file by filename in the books-folder as WRITE
        for p in self.personList: #loop all persons in the list
            aliases = ""
            for a in p.getAlias():
                aliases += a + ";"
            f.write(p.getNumber() + ";" + p.getName() + ";" + aliases + "\n") #write every persons number, name, alias to file separated by ;
        f.close() #close the file
        return True
        
    def loadBook(self, filename): #function to load telbook from file
        f = open("books/"+filename+".txt", "r") #open file by filename in the books-folder as READ
        pers = [] # make empty list for temporary person storage
        while True: #read all lines in file, break when empty line
            i = f.readline()
            if i == "": #if line is empty
                break
            pers.append(i.strip("\n")) #append every line in file to the pers-list. Strip away linebreaks
        f.close() #done with file, close it
        
        if(len(pers) > 0): #check if any persons in list, else file must have been empty
            self.personList = [] #delete everything in the current personlist
            for p in pers: #loop through loaded persons so they can be added
                prop = p.split(";") #properties are separeted by ; so split by that
                if len(prop) < 2: #if the length of prop is not 4, something is wrong with file, empty the phonebook and raise error
                    self.personList = []
                    raise Exception, "file corrupted"
                number = prop.pop(0) #first element contains number
                name = prop.pop(0) #secont element contains name
                aliases = []
                for al in prop: #append all aliases to a list
                    aliases.append(al)
                if aliases:
                    aliases.pop(len(aliases) - 1)
                self.addPerson(name, number, aliases)
        else: #length of person less then 1, file must be empty, raise error
            return False
        return True

    def traverse(self):
        result = self.personList
        if result:
            return result
        return False







