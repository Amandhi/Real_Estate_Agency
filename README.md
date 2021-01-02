## Mobile Development - Final Project : Real Estate Agency App named "Estates"

#### The above project uses a MVVM pattern architecture and contains the following java actvities in View :
* AgentListActivity : page which is displayed when the user first opens the app or when the user later logs out.
* PropertyListActivity : home page of the app, displayed when the user logs in. From this page, the user has view on the properties saved in the app's database. 
The user can also from here, add a property and view properties on the map through the expandable floating button menu, make a research, or log out.
* AddPropertyActivity : enables the user to add a property by filling a form.
* PropertyDetailActivity : enables the user to view a property's details when he/she clicks on the property from the home page. 
This page has also an acces to functionnalities such as the updation or deletion of a property, price conversion and loan simulation. 
* UpdatePropertyActivity : enables the user to update one or multiple info about a registered property, by changing a prefilled form.
* SearchPropertyActivity : enables the user to make a research according to several criterias, by filling a form.
* MapsActivity : enables the user to view all the properties saved on the app, on Google Maps. Sold properties will appear with a red pin, not sold ones, with a blue pin.
* LoanSimulatorActivity : enables the user to simulate a real estate loan : by filling a form, the app indicates the monthly and total payments to a specific loan process.

All according activities to the above ones, implementing methods are containes in Viewmodel.

and finally the project contains the following xml layouts :
* activity_agent_list.xml
* activity_property_list.xml
* activity_add_property.xml
* activity_property_detail.xml
* activity_update_property.xml
* activity_search_property.xml
* activity_maps.xml
* activity_loan_simulator.xml
* viewholder_agent.xml
* viewholder_property.xml

#### This project also uses the following objects :
* Adapter
* Database
* Dao
* Retrofit instance 
* JSON Parser 
* Preferences
* Repositories

The app also has a customized icon and name. 

#### Basically, what you can do with this app :
* Log in choosing the profile of your choice (and therefore log out)
* Add a property (notification system)
* View that property's details (type, price, surface, number of rooms etc.)
* View the displayed price in USD instead of EUR
* Do a real estate loan simulation
* Make a research
* Delete an existing property (with a warning pop up message customized for each property : deletion can only be done by the agent in charge) (notification system)
* Update an existing property (notification system)
* View all existing properties on Google Maps (categorized into blue (not sold) and red (sold) pins)

