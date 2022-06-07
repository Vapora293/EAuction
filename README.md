**Auction logic**<br />
In order to log in to the auction, the user needs to have created an account with a username and password. Once logged in, they have the option to register new object into the warehouse (its image, name, category, starting price and estimated price) , increase money balance, create new auction or join one of the auctions available. When the auction is in progress, the player competes with other players in placing bids on the auction item. In case of English Auction, if a player has placed the highest bid and the others are unwilling to raise the bid, the item is credited to his account. In case of Reverse auction, the player accepting current price wins it. In both cases, auction is deleted from the Auction list, the object from the list of the user and if the player won the object, it will be credited to his account and balance will be lowered.

**UML Diagrams**<br />
Uml diagrams are included in the folder uml-diagrams, one with associations while other file without them, just inheritance with interface implementations

**Javadoc**<br />
Javadoc is generated into javadoc folder with index.html as a main file.

**Main criteria**<br />
Inheritance is applied in two cases, for patterns of controllers to use the logic of upper navbar. Second one is for the class Starter, all the classes used for serialization (AuctionedObject, Auction, User) and BasicUser + Bot extends User, ReverseAuction and EnglishAuction extend Auction.<br />
Interface is used as AuctionObserver, when new auction is formed, all the bots get invited to join. Then AuctionStatusListener - methods defined in the controller of Auction and it gets called in the concrete auction types. <br />
Agreggation is used e.g. in Auction, it uses AuctionedObject, former owner, actual owner and Users have Wallet with credit.<br />
Encapsulation is used throughout whole code - classes with inheritance use protected parameters, other classes private access and for Auctions package-protected.<br />

**Side criteria**<br />
**Design Patterns**<br />
                  **Observer**<br />
                  implemented in the Auction. When new auction is created, bots waiting in Singleton have the chance to join the auction, implemented via their own Interface.<br />
                  **Memento**<br />
                  implemented in the Auctioned Object. When setting up new auction, you have the possibility to change data in the object - and revert changes to the first Memento of the object saved.<br />
                  **Factory Method**<br />
                  implemented for making new auctions. <br />
**Own exception**<br />
In the object making there can be no letter in expected price and starting price - IncorrectEntryException is being thrown. <br />
**Manually created popup**<br />
Custom Popup - when you want to raise credit in the wallet and the name of donator doesn't match the username, this popup shows as a warning, if it's really the person who wants to raise the money.<br />
**Multithreading**<br />
Just the use of thread in the Auction Timer with the implementation of AuctionTask.<br />
**Generic class**<br />
GenericList contains Observable List with function to find in the list - implements compare function, which is kind of the same as .equals, but doesn't compare instances, just the data in it - used with serialization to get the same objects.<br />
**RTTI use**<br />
in Serialize class, it gets the instance of the first object in it and based on the Class it serializes to different .txt files while implementing different serialization pattern.<br />
**Nested classes**<br />
Two of them, first one Memento - used exclusively by AuctionedObject and Auction Task used exclusively in the Auction Controller.<br />
**Default method implementation**<br />
AuctionStatusListener has a quote executed in the thread in order to start the auction.<br />
**Serialization**<br />
Implemented in the Serialize class, has two methods, each with two patterns - <br />
WriteObject - writes the GenericList into the .txt file based on RTTI. If needed to serialize into the .txt file of certain user, another method with the same name is available.<br />
ReadObject - reads from .txt file desired data and returns GenericList of the data based on a String argument of the function. Second method with additional parameter for specific user implemented as in the WriteObject.<br />
