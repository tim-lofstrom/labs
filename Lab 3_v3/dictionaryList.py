'''
Created on 1 okt 2013

@author: Kurtan
'''
class dictionaryList:
    
    def __init__(self):
        self.itemDic = {}

    def add(self, w, d):
        self.itemDic[w] = d
        
    def lookup(self, s):
        for w, d in self.itemDic.iteritems():
            if(w == s):
                return w, d
        return None
            
    def delete(self, d):
        del self.itemDic[d] #result found, delete it and return true

    def traverse(self):
        return self.itemDic