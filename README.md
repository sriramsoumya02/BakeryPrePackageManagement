# BakeryPrePackageManagement
######BakeryPrePackageManagement
A bakeryused tobasethepriceof theirproduceonan individual itemcost.So if a customerordered 10crossbunsthentheywould becharged10xthecost ofsinglebun. Thebakeryhas decidedtostart selling their produceprepackagedinbunchesand charging thecustomeron aper packbasis. Soif the shopsold vegemitescroll in packsof3 and5 andacustomer ordered 8theywould get apackof 3and a packof5. The bakerycurrentlysells thefollowing products: Name Code Packs VegemiteScroll VS5 3 @$6.99 5 @$8.99
BlueberryMuffin MB11 2 @$9.95 5 @$16.95 8 @$24.95
Croissant CF 3 @$5.95 5 @$9.95 9 @$16.99
Task: Given a customer order you are required todetermine thecost and pack breakdown for each product. Tosave onshipping spaceeachorder shouldcontain theminimal numberof packs.
Input: Eachorder hasa seriesof lineswith eachline containing thenumber of itemsfollowed by the productcode.
######input:
Please place your order in the following format Quantity,Productcode eg:10,VS5 :
10,VS5
14,MB11
13,CF
######Output:
10 VS5 $17.98
	2 x 5 $8.99
14 MB11 $54.8
	1 x 8 $24.95
	3 x 2 $9.95
13 CF $25.85
	2 x 5 $9.95
	1 x 3 $5.95
######Build and run:
mvn clean package
java -jar target\BakeryPrePackageManagement-1.0-SNAPSHOT.jar


