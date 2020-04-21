# Parallelized Password Cracker [in-progress :hammer: :hammer: :hammer:]
In this project attempt to leverage the mutithreading capabiltiies of the Java SDK to accrelate the execution of this password cracker. In essence, this application cracks passwords that encrypted with SHA-256 hash function.

## You may want to test the strength of your password against this program.

## SHA-256?
This is a one-way hash function that takes a char string as input, and outputs a single 256-bit or 32-Byte fixed size char string.

Most websites store their users’ passwords in the form of signatures obtained from cryptographic hash functions such as the SHA-256. Thus, if their database were to be hacked, their users’ passwords would not be compromised immediately.

Hackers would still have to try to obtain each entry that matches the cryptographic signatures obtained. Thereby,(often) cost of bute-forcing the password out weights the cost of cracking it. 

## Algorithmic Strategy
Brute-force i.e. O(n). Thus, to decrease execution by some constant value, one has to refine the dictionary.


