Name: Andrew Whitney (ajwhitney)
Student ID: 525692403

Section I.

- open terminal/powershell/cmd in the project directory
- type to compile program: javac Main.java Frame.java BufferPool.java
- to run program: java Main [num of frames]
    num of frames: any integer
- now program is running, available commands: 'GET [record]' 'SET [record] [content]' 'PIN [file]' 'UNPIN [file]'
    as specified in project pdf
- type EXIT to exit program

Section II.

The program is able to successful perform all Cases specified in the program pdf.

Testing Code Result:

C:\..\..\..\DatabasesII\Project1>javac Main.java BufferPool.java Frame.java
C:\..\..\..\DatabasesII\Project1>java Main 3
The program is ready for the next command
SET 430 "F05-Rec450, Jane Do, 10 Hill Rd, age020."
Write was successful; Brought File 5 from disk; Placed in Frame 1
GET 430
F05-Rec450, Jane Do, 10 Hill Rd, age020.; File 5 already in memory; Located in Frame 1
GET 20
F01-Rec020, Name020, address020, age020.; Brought File 1 from disk; Placed in Frame 2
SET 430 "F05-Rec450, John Do, 23 Lake Ln, age056."
Write was successful; File 5 already in memory; Located in Frame 1
PIN 5
File 5 pinned in Frame 1; Frame 1 was not already pinned
UNPIN 3
The corresponding block 3 cannot be unpinned because it is not in memory.
GET 430
F05-Rec450, John Do, 23 Lake Ln, age056.; File 5 already in memory; Located in Frame 1
PIN 5
File 5 pinned in Frame 1; Frame 1 was already pinned
GET 646
F07-Rec646, Name646, address646, age646.; Brought File 7 from disk; Placed in Frame 3
PIN 3
File 3 pinned in Frame 2; Frame 2 was not already pinned; Evicted File 1 from Frame 2
SET 10 "F01-Rec010, Tim Boe, 09 Deer Dr, age009."
Write was successful; Brought File 1 from disk; Placed in Frame 3; Evicted File 7 from Frame 3
UNPIN 1
File 1 in Frame 3 is unpinned; Frame 3 was already unpinned
GET 355
F04-Rec355, Name355, address355, age355.; Brought File 4 from disk; Placed in Frame 3; Evicted File 1 from Frame 3
PIN 2
File 2 pinned in Frame 3; Frame 3 was not already pinned; Evicted File 4 from Frame 3
GET 156
F02-Rec156, Name156, address156, age156.; File 2 already in memory; Located in Frame 3
SET 10 "F01-Rec010, No Work, 31 Hill St, age100."
The corresponding block 1 cannot be accessed from disk because the memory buffers are full; Write was unsuccessful
PIN 7
The corresponding block 7 cannot be pinned because the memory buffers are full
GET 10
The corresponding block 1 cannot be accessed from disk because the memory buffers are full; Write was unsuccessful
UNPIN 3
File 3 in Frame 2 is unpinned; Frame 2 was not already unpinned
UNPIN 2
File 2 in Frame 3 is unpinned; Frame 3 was not already unpinned
GET 10
F01-Rec010, Tim Boe, 09 Deer Dr, age009.; Brought File 1 from disk; Placed in Frame 2; Evicted File 3 from Frame 2
PIN 6
File 6 pinned in Frame 3; Frame 3 was not already pinned; Evicted File 2 from Frame 3
EXIT

Section III.

I added a lastUsed array for each frame which took in a varaible commandNum which worked at "time" for my program instead
of working with time directly. I added variables to keep track if a file was evicted and if it was brought to buffer to help
shape the outputs. I utilized a hashmap to keep trash of which frame each file was.  The hashmap would return null if the file
was not in the buffer.