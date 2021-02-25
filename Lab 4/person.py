'''
Created on 15 okt 2013

@author: Kurt
'''
class person:
    def __init__(self, name, number, alias = ""): #constructor for person, assigning name, number and alias
        try: #check if number is only numbers
            int(number)
        except ValueError: #if number contains more than just number, raise error and the person will not be added
            raise Exception, "Number not valid"
        pattern = list(";:,!%&/(()=?<>~^|*") #makes a patternlist for illegal characters
        for p in pattern:
            if p in name: #if name contains one of the illegal characters, raise error and the person will not be added
                raise Exception, "Name contains invalid characters"
            
        #all inputs are fine, add the person
        self.name = name
        self.number = number
        self.alias = alias
    
    #functions for getting and setting the properties of person    
    def getName(self):
        return self.name
    
    def getAlias(self):
        return self.alias
    
    def getNumber(self):
        return self.number

    def setName(self, newname):
        self.name = newname
    
    def setAlias(self, alias):
        self.alias = alias
    
    def setNumber(self, number):
        self.number = number
        
