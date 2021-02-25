'''
Created on 19 sep 2013

@author: Kurtan
'''


def calc(n):
  if(n < 10):
    return n
  result = n % 10
  result += calc(n / 10) 
  return result

print calc(114)