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

For DailyReports, make sure sql script is run same day as daily reports is run to check for rentals/returns for the current day
Rentals are added with current date so that there are rentals to show for current day. If sql script is run not on
same day report will show no rentals as no rentals were made on that day of the report. Report generation is for current
day, or date of when report is asked and compares that date to see if rentals or returns occured that same day.


Run following line in terminal in intelliJ
ssh -l egylling -L localhost:1522:dbhost.students.cs.ubc.ca:1522 lulu.students.cs.ubc.ca