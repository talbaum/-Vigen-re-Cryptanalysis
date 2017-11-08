# -Vigenre-Cryptanalysis

This project is dealing with Vigenre cipher.

**What is Vigenere cipher?**

Each letter in the ABC get a value. A key word is decided and we can encrypt our plain text with the key to get the ciphered text.
For example:

Each letter get the value according to this method:
A=0, B=1 , C=2, D=3, E=4,,F=5,G=6,H=7,I=8 ... Z=25

let's take for example this plain text : DEFENDTHEEASTWALLOFTHECASTLE                                                      
with this repeating key:                 FORTIFYFORTIFYFORTIFYFORTIFY                                                       
we will get this ciphered text:          ISWXVIRMSVTAYUFZCHNYFJQRLBQC

The first coloumn is D+F=I, which is 5+3=8 according to the table.

**In this Project**

1)cipher function - gets a plain text and a key , and adjust the text to cipher text.

2)decipher function- gets a ciphered text and a key , and adjust the text to plain text. 

3)numOfLetters- function that return an int array which represents how many times each letter appeared in a string.

4)findLengthOfKey- gets a ciphered text and return the length of the secret key.
this function uses some sub functions: icForAverage , icForOneElement and findMin.
We calc the key using the Ic for each suspected letter.
for more details about IC :
[https://en.wikipedia.org/wiki/Index_of_coincidence]

5)findTheKey- gets a ciphered text and find it's secret key.
This function uses 4) in order to get the key length , and then find the actual chars of the key while using
a letter frequnecy formula.

[https://en.wikipedia.org/wiki/Letter_frequency#Relative_frequencies_of_letters_in_the_English_language]

After we have found the key , we can use decipher function with the ciphered text and the key as parameters , and we will get the secret messege in response!

