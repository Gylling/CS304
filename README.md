# CS304
Car rental

Our car rental DB has three branches: 
    1) Location: A City: Abbotsford
    2) Location: C City: Chilliwack
    3) Location: V City: Vancouver

Within the rentals relation we have rolled in the details of a return, which are defined as NULL until
the car rental is returned where the following fields associated with a return will be updated, that being:
return date, odometer, value and the likes.

As a clerk you are also allowed to edit, insert and delete branches along with the required transactions.
As a customer you are also allowed to edit, delete and show a reservation along with the required making 
a reservation.

Run following line in terminal in intelliJ
ssh -l egylling -L localhost:1522:dbhost.students.cs.ubc.ca:1522 lulu.students.cs.ubc.ca