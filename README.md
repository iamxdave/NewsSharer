# NewsSharer
Swing application that let you send news to your clients.

__________________________________________________________________________________________________________________

<p align="center">
  <img src=https://user-images.githubusercontent.com/74014874/166149786-f415e370-72d8-4418-bd97-ce9a750ba58c.png
   >
</p>

<p align="center">
  <img src=https://user-images.githubusercontent.com/74014874/166149828-b7504074-6293-427c-be71-e307e4c550c2.png
   >
</p>


# General information about the application
Each client may sign up for the service provider by specifying topics (etc. policy, sport, cooking) as well as resign from his existing topics. The application enables simultaneous service of many clients. Admininstrator is a program that sends news to a main server from various fields and he sends them out to interested customers. 
<br />
<br />

## How it works

  1. Run admin, server and any number of clients.
  2. Choose a topic put an information (optional) and click add button in the administartor window.
  3. Go to client window and write a topic you want to get news about. Click subscribe.
  4. If additional news is provided click the update button in the admin frame
<p align="center">
  <img src=https://user-images.githubusercontent.com/74014874/166154860-8357312e-e5a4-409d-8031-052e0ffc6700.png
   >
</p>
     and then refresh button to refresh content for the client.
<br />
<p align="center">
  <img src=https://user-images.githubusercontent.com/74014874/166154892-ad6c42a6-3f14-4abb-af6a-ac3b84918d82.png
   >
</p>

  5. To remove a topic from the list, pick topic and apply remove button.
  6. To unsubscribe a topic click on an unsubscribe button.

 ## API meets the following requirements:
  1. Checks if the client is a subscribent of the current topic before refreshing news or for unsubscribing field.
  2. Checks if the chosen topic is in the database and sends appropriate message.
  3. Checks if the server is closed or the topic is removed from the list and connected clients with the information.
