# BakeryPrePackageManagement
BakeryPrePackageManagement
Background: A bakeryused tobasethepriceof theirproduceonan individual itemcost.So if a customerordered 10crossbunsthentheywould becharged10xthecost ofsinglebun. Thebakeryhas decidedtostart selling their produceprepackagedinbunchesand charging thecustomeron aper packbasis. Soif the shopsold vegemitescroll in packsof3 and5 andacustomer ordered 8theywould get apackof 3and a packof5. The bakerycurrentlysells thefollowing products: Name Code Packs VegemiteScroll VS5 3 @$6.99 5 @$8.99
BlueberryMuffin MB11 2 @$9.95 5 @$16.95 8 @$24.95
Croissant CF 3 @$5.95 5 @$9.95 9 @$16.99
Task: Given a customer order you are required todetermine thecost and pack breakdown for each product. Tosave onshipping spaceeachorder shouldcontain theminimal numberof packs.
Input: Eachorder hasa seriesof lineswith eachline containing thenumber of itemsfollowed by the productcode. 
An example input: 
10,VS5 
14,MB11 
13CF
Output: A successfully passing test(s) that demonstrates the following 
output: 
10VS5 $17.98 
  2 x5$8.99 
14MB11 $54.8
  1 x8$24.95 
  3 x2$9.95 
13CF $25.85 
  2 x5$9.95 
  1 x3$5.95
  
  

