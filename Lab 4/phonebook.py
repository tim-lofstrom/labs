'''
Created on 15 okt 2013

@author: Kurt
'''

import person

class phonebook:
    
    def __init__(self): #constructor of phonebook, make empty list that will contain instances of persons
        self.personList = []
    
    def addPerson(self, name, number, alias = ""): #function to add person
        try:
            self.lookupPerson(name, number) # if lookup goes fine, the persons with that number already exists
            raise Exception, "A person with this number already exists"
        except LookupError: #if lookup gives error, the person do not exists, add it
            p = person.person(name, number, alias)
            self.personList.append(p)
            
    def lookupPerson(self, name, number = None): #function for looking up persons in list
        result = []
        for p in self.personList: #check all person in personlist
            if(p.getName() == name or p.getAlias() == name): #search can be done for both name and alias
                if(p.getNumber() == number): #if number is given as parameter and matching a person, the final result is found
                    return [p] #return the result as a list
                result.append(p) #adding multiple persons to a resultlist
        if result and number == None: #if result not empty and number is not given as parameter
            return result
        raise LookupError, "No results found" #if no persons found, raise error
    
    def aliasPerson(self, name, alias, number = None): #function to set alias to person
        result = self.lookupPerson(name, number) #use lookup person to find the specific one to add alias to
        if len(result) == 1: #it should just be one result, if more raise error
            result[0].setAlias(alias)
        else:
            raise LookupError, "Multiple persons found, try add number as parameter" 
        return
    
    def changePerson(self, name, newnumber, number = None): #function to change a persons number
        result = self.lookupPerson(name, number) #use lookup person to find the specific one to change
        if len(result) == 1: #it should just be one result, if more raise error
            result[0].setNumber(newnumber)
        else:
            raise LookupError, "Multiple persons found, try add number as parameter" 
        return
    
    def removePerson(self, name, number = None): #function for removing a person
        index = 0
        result = []
        for p in self.personList:
            if(p.getName() == name or p.getAlias() == name): #adding all matching names index to resultlist
                if(p.getNumber() == number): #if number is given as parameter and matches, result found, delete it
                    self.personList.pop(index)
                    return
                result.append(index)
            index += 1
        if len(result) == 1: #if only one person with the name was found, delete it
            self.personList.pop(result[0])
            return
        if len(result) > 1: #if multiple results, number is needed as parameter
            raise LookupError, "Multiple persons found, try add number as parameter"
        raise LookupError, "No results found, cannot delete"

    def saveBook(self, filename): #functions for saving the phonebook to a file, by filename as parameter
        
        pattern = list(";:,.!%&/(()=?<>~^|*") #makes a patternlist for illegal characters
        for p in pattern:
            if p in filename: #if filename contains one of the illegal characters, raise error and the file will not be created
                raise Exception, "Filename contains invalid characters"
        
        if len(self.personList) < 1: #if there are no persons in the list, do not make any file, raise error
            raise Exception, "Cannot save empty phonebook"
        f = open("books/"+filename+".txt", "w") #open file by filename in the books-folder as WRITE
        for p in self.personList: #loop all persons in the list
            f.write(p.getNumber() + ";" + p.getName() + ";" + p.getAlias() + ";\n") #write every persons number, name, alias to file separated by ;
        f.close() #close the file
        return
        
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
                if len(prop) != 4: #if the length of prop is not 4, something is wrong with file, empty the phonebook and raise error
                    self.personList = []
                    raise Exception, "file corrupted"
                self.addPerson(prop[1], prop[0], prop[2])
        else: #length of person less then 1, file must be empty, raise error
            raise Exception, "File is empty"
        return

    def traverse(self):
        result = self.personList
        if result:
            return result
        raise LookupError, "No results found" 







