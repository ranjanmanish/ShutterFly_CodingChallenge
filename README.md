# ShutterFly_CodingChallenge

## Some of teh assumptions made
1. All the data comes in one list. As there was no clarity on return type of API request for data
2. for ingestinging data I have created the infrastructure however because of time limitation I have not implemented the individual class checks 
3. I have used the same data provided as simple.txt and extened it for 3 more input varying only fields which matter i.e ### amount spent . Have kept the visists same as first customer
4. The CustomerVisist class implements comparator  so it can sort based on value as the map created is Map<id, no_of_visit>
