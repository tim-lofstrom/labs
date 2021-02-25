'''
Created on 1 okt 2013

@author: Kurtan
'''
class stringList:
    
    def __init__(self):
        self.word = []
        self.desc = []
    
    def add(self, w, d):
        self.word.append(w)
        self.desc.append(d)
        
    def lookup(self, s):
        index = 0
        for w in self.word:
            if w == s:
                return w, self.desc[index]
            index += 1
        return None
    def delete(self, d):
        index = 0
        for w in self.word:
            if w == d:
                self.word.pop(index)
                self.desc.pop(index)
                break
            index += 1
            
    def traverse(self):
        return (self.word, self.desc)