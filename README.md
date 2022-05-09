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

<br />

# General information about the application
<p> 
Each client may sign up for the service provider by specifying topics (etc. policy, sport, cooking) as well as resign from his existing topics. </br>
The application enables simultaneous service of many clients. Admininstrator is a program that sends news to a main server from various fields and he sends them out to interested customers. 
</p>

<br />
<br />
<hr>
<br />

## How it works

  1. Run admin, server and any number of clients.
  2. Choose a topic put an information (optional) and click add button in the administartor window.
<p align="center">
  <img src=https://user-images.githubusercontent.com/74014874/166155180-6a8d06dd-547f-455e-8e55-a60dd90e4fca.png
   >
</p>
  3. Go to client window and write a topic you want to get news about. Click subscribe.
<p align="center">
<br />
  <img src=https://user-images.githubusercontent.com/74014874/166155215-552c2f93-7596-47f3-9937-b5b40e4fb80f.png
   >
</p>
  4. If additional news are provided click the update button in the admin frame and then refresh button to refresh content for the client.
<p align="center"> 
<br />
  <img src=https://user-images.githubusercontent.com/74014874/166154892-ad6c42a6-3f14-4abb-af6a-ac3b84918d82.png
   >
</p>

  5. To remove a topic from the list, pick topic and apply remove button.
  6. To unsubscribe a topic click on an unsubscribe button.

__________________________________________________________________________________________________________________
 ## API meets the following requirements:
  1. Checks if the client is a subscribent of the current topic before refreshing news or for unsubscribing field.
  2. Checks if the chosen topic is in the database and sends appropriate message.
  3. Checks if the server is closed or the topic is removed from the list and connected clients with the information.
