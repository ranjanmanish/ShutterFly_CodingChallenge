# ShutterFly_CodingChallenge

## Some of the assumptions made
1. All the data comes in one list. As there was no clarity on return type of API request for data.
2. for ingesting data I have created the infrastructure however because of time limitation I have not implemented the individual class checks . Based on what values are marked as star, the factory class will invoke respective class and it will be class responsbility to reject or add a value of its type to data set. 
3. I have used the same data provided as simple.txt and extened it for 3 more input varying only fields which matter and I wanted to test my code against.i.e ### amount spent. Have kept the visits same as first customer
4. The CustomerVisist class implements comparator  so it can sort based on value as the map created is Map<id, no_of_visit>
5. Jar External Used:

<!-- https://mvnrepository.com/artifact/org.json/json -->
<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20140107</version>
</dependency>
