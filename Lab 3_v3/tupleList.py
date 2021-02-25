'''
Created on 1 okt 2013

@author: Kurtan
'''
class tupleList:
    
    def __init__(self):
        self.items = []
        
    def add(self, w, d):
        i = (w, d)
        self.items.append(i)
        
    def lookup(self, s):
        for i in self.items:
            if i[0] == s:
                return i[0], i[1]
        return None
            
    def delete(self, d):
        index = 0
        for i in self.items:
            if i[0] == d:
                self.items.pop(index)
                break
            index += 1
            
    def traverse(self):
        return self.items