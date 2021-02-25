'''
Created on 14 sep 2013

@author: Kurtan
'''
#    3 st agg
#    3 dl strosocker
#    2 tsk vaniljsocker
#    2 tsk bakpulver
#    75 g smor
#    1 dl vatten
#    3 dl vetemjol

class ingredient:
    def __init__(self, name, amount, ending):
        self.name = name
        self.amount = amount
        self.ending = ending
        
class sugarCake:
    def __init__(self, ingredients):
        self.ingredients = ingredients
    
    def bakeCake(self, n):
        print "For att baka en sockerkaka som ska racka till " + str(n) + "st personer, anvand foljande proportioner:"
        for i in self.ingredients:
            if(i.name == "Agg"):
                print str(i.name) + " " + str(int(i.amount * n))+ " " + str(i.ending)
            else:
                print str(i.name) + " " + str(i.amount * n)+ " " + str(i.ending)


def checkIfInt(i):
    try:
        int(i)
        return True
    except ValueError:
        return False

ing = [ingredient("Agg", float(3)/4, "st")]
ing.append(ingredient("Socker", float(3)/4, "dl"))
ing.append(ingredient("Vaniljsocker", float(2)/4, "tsk"))
ing.append(ingredient("Bakpulver", float(2)/4, "tsk"))
ing.append(ingredient("Smor", float(75)/4, "g"))
ing.append(ingredient("Vatten", float(1)/4, "dl"))
ing.append(ingredient("Mjol", float(3)/4, "dl"))

#skapar en instance av sockerkaka med en array av ingredienser som parameter
cake1 = sugarCake(ing)

print "Sockerkaksprogrammet"
print ""
print "Skriv in antalet personer som vill ha sockerkaka->"
n = raw_input()
while(True):
    if(checkIfInt(n) == False):
        print "Antalet maste vara ett positivt heltal, forsok igen ->"
    elif(int(n) < 1):
        print "Antalet maste vara ett positivt heltal, forsok igen ->"
    else:
        break
    n = raw_input()

cake1.bakeCake(int(n))

# startar "script" som skriver ut recept for 4 och 7 personer
print ""
cake1.bakeCake(4)
print ""
cake1.bakeCake(7)


